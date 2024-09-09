package com.food.core.service;

import com.food.core.data.FoodReqVO;
import com.food.core.data.Interval;
import com.food.core.enums.FacilityTypeEnum;
import com.food.core.enums.PermitStatus;
import com.food.core.enums.WeekEnum;
import com.food.core.mapper.DayHoursMapper;
import com.food.core.mapper.FoodMapper;
import com.food.core.model.DayHours;
import com.food.core.model.Food;
import com.github.pagehelper.PageHelper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FoodService {
    private final FoodMapper foodMapper;

    private final DayHoursMapper dayHoursMapper;

    private final ResourceLoader resourceLoader;

    @Autowired
    public FoodService(FoodMapper foodMapper, DayHoursMapper dayHoursMapper, ResourceLoader resourceLoader) {
        this.foodMapper = foodMapper;
        this.dayHoursMapper = dayHoursMapper;
        this.resourceLoader = resourceLoader;
    }

    private int parseHour(String str) {
        int ret = 0;
        if (str.endsWith("AM")) {
            if (str.equals("12AM")) {
                ret = 0;
            } else {
                ret = Integer.parseInt(str.replace("AM", ""));
            }
        } else if (str.endsWith("PM")) {
            if (str.equals("12PM")) {
                ret = 12;
            } else {
                ret = Integer.parseInt(str.replace("PM", "")) + 12;
            }
        }
        return ret;
    }

    private List<Interval> intervalMerge(List<Interval> intervals) {
        if (intervals.isEmpty()) return intervals;
        intervals.sort((o1, o2) -> {
            if (o1.getStart() - o2.getStart() == 0) {
                return o1.getEnd() - o2.getEnd();
            }
            return o1.getStart() - o2.getStart();
        });
        List<Interval> ret = new ArrayList<>();
        ret.add(intervals.get(0));

        for (int i = 1; i < intervals.size(); i++) {
            int ansLen = ret.size() - 1;
            if (ret.get(ansLen).getEnd() >= intervals.get(i).getStart()) {
                ret.get(ansLen).setEnd(Math.max(ret.get(ansLen).getEnd(), intervals.get(i).getEnd()));
            } else {

                ret.add(intervals.get(i));
            }
        }
        return ret;
    }

    private List<DayHours> createDayHour(String[] arr) {
        if(arr[17].isEmpty())return null;
        List<DayHours> rets = new ArrayList<>();
        Long locationId = Long.valueOf(arr[0]);
        ArrayList<Interval>[] intervals = new ArrayList[8];
        for (int i = 0; i < 8; i++) {
            intervals[i] = new ArrayList<>();
        }
        //第18列是dayhour数据
        //分号分割了多个排班数据
        String[] dayHoursArr = arr[17].split(";");
        for (String dayHoursStr : dayHoursArr) {
            //每个排班数据单元分为前后两部分，前面是周，后面是时间区间，以冒号分隔
            String[] dhArr = dayHoursStr.split(":");
            if (dhArr.length != 2) {
                log.error("wrong data:{}", dayHoursStr);
                continue;
            }
            String dayStr = dhArr[0];
            String hourStr = dhArr[1];

            //解析起始时间区间，可能有多个
            String[] hourStrs = hourStr.split("/");
            List<Interval> intervalList = new ArrayList<>();
            for (String hstr : hourStrs) {
                String[] hours = hstr.split("-");
                if (hours.length != 2) {
                    log.error("wrong hour data:{}", hstr);
                    continue;
                }
                Interval interval = new Interval();
                interval.setStart(parseHour(hours[0]));
                interval.setEnd(parseHour(hours[1]));
                intervalList.add(interval);
            }
            //解析周数据，并将时间区间放入天
            String[] dayStrs = dayStr.split("/");
            if (dayStrs.length == 1) {
                //可能是单独一天，也可能是区间
                if (dayStrs[0].length() == 2) {
                    int day = WeekEnum.getKeyByValue(dayStrs[0]);
                    intervals[day].addAll(new ArrayList<>(intervalList));
                } else if (dayStrs[0].length() > 2) {
                    String[] dayInterval = dayStrs[0].split("-");
                    if (dayInterval.length != 2) {
                        log.error("wrong day interval:{}", dayStrs[0]);
                    } else {
                        int sday = WeekEnum.getKeyByValue(dayInterval[0]);
                        int eday = WeekEnum.getKeyByValue(dayInterval[1]);
                        for (int i = sday; i <= eday; i++) {
                            intervals[i].addAll(new ArrayList<>(intervalList));
                        }
                    }
                }
            } else {
                //以/分隔的多天
                //如果区间只有一个，说明每一天都是这个区间
                if (intervalList.size() == 1) {
                    for (String dStr : dayStrs) {
                        int day = WeekEnum.getKeyByValue(dStr);
                        intervals[day].addAll(new ArrayList<>(intervalList));
                    }
                } else {
                    //多对多的情况
                    int min = Math.min(dayStrs.length, intervalList.size());
                    for (int i = 0; i < min; i++) {
                        int day = WeekEnum.getKeyByValue(dayStrs[i]);
                        intervals[day].add(intervalList.get(i));
                    }
                }
            }
        }
        //对每一天执行区间合并
        for (int i = 1; i <= 7; ++i) {
            List<Interval> inters = intervalMerge(intervals[i]);
            for (Interval inter : inters) {
                DayHours dayHours = new DayHours();
                dayHours.setLocationId(locationId);
                dayHours.setDay(i);
                dayHours.setStartHour(inter.getStart());
                dayHours.setEndHour(inter.getEnd());
                rets.add(dayHours);
            }
        }
        return rets;
    }

    private Food createFood(String[] arr) {
        Food food = new Food();
        food.setLocationId(Long.parseLong(arr[0]));
        food.setApplicant(arr[1]);
        food.setFacilityType(FacilityTypeEnum.getKeyByValue(arr[2]));
        food.setLocationDescription(arr[4]);
        food.setAddress(arr[5]);
        food.setBlock(arr[7]);
        food.setLot(arr[8]);
        food.setPermit(arr[9]);
        food.setStatus(PermitStatus.getKeyByValue(arr[10]));
        food.setFoodItems(arr[11]);
        food.setDayHours(arr[17]);
        try {
            food.setCnn(Long.parseLong(arr[3]));
            food.setLatitude(Double.parseDouble(arr[14]));
            food.setLongitude(Double.parseDouble(arr[15]));
            food.setPriorPermit(Integer.parseInt(arr[21]));
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                food.setApproved(arr[19].isEmpty()?null:sdf.parse(arr[19]));
                food.setExpiration(arr[22].isEmpty()?null:sdf.parse(arr[22]));
                sdf = new SimpleDateFormat("yyyyMMdd");
                food.setReceived(arr[20].isEmpty()?null:sdf.parse(arr[20]));
            } catch (ParseException e) {
                log.error(e.getMessage());
                return null;
            }
            food.setFirePreventionDistricts(arr[24].isEmpty()?0:Integer.parseInt(arr[24]));
            food.setPoliceDistricts(arr[25].isEmpty()?0:Integer.parseInt(arr[25]));
            food.setSupervisorDistricts(arr[26].isEmpty()?0:Integer.parseInt(arr[26]));
            food.setZipCodes(arr[27].isEmpty()?0:Integer.parseInt(arr[27]));
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return food;
    }

    /**
     * 从csv文件读取数据，保存入库
     */
    public void readCSVtoSaveFood(String csvPath) {
        Resource resource = resourceLoader.getResource("classpath:" + csvPath);
        List<Food> dbFoods = foodMapper.selectAll();
        Set<Long> locationIdSet = new HashSet<>();
        for(Food food : dbFoods) {locationIdSet.add(food.getLocationId());}
        List<Long> existIds = new ArrayList<>();
        List<Food> foodList = new ArrayList<>();
        List<DayHours> dayHoursList = new ArrayList<>();
        try(CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))){
            List<String[]> lines = csvReader.readAll();
            //跳过标题
            for (int i=1 ; i<lines.size(); i++) {
                String[] arr = lines.get(i);
                Food food = createFood(arr);
                if (food != null) {
                    if(locationIdSet.contains(food.getLocationId())) {
                        existIds.add(food.getLocationId());
                    }
                    foodList.add(food);
                }
                List data = createDayHour(arr);
                if(data != null && data.size() > 0) {
                    dayHoursList.addAll(data);
                }

            }

        }  catch (IOException e) {
            log.error(e.getMessage());
        } catch (CsvException e) {
            log.error(e.getMessage());
        }

        if(!existIds.isEmpty()){
            //先删除已存在数据，防止重复
            Example example = new Example(Food.class);
            example.createCriteria().andIn("locationId", existIds);
            foodMapper.deleteByExample(example);
            dayHoursMapper.deleteByExample(example);
        }

        //批量插入
        if(!foodList.isEmpty())foodMapper.insertList(foodList);
        if(!dayHoursList.isEmpty())dayHoursMapper.insertList(dayHoursList);

    }

    public List<Food> findFoodFacility(FoodReqVO foodReqVO) {
        if (foodReqVO.getDay() != null && foodReqVO.getHourStart() != null && foodReqVO.getHourEnd() != null) {
            DayHours dayHours = new DayHours();
            dayHours.setDay(foodReqVO.getDay());
            dayHours.setStartHour(foodReqVO.getHourStart());
            dayHours.setEndHour(foodReqVO.getHourEnd());
            foodReqVO.setLocationIds(dayHoursMapper.selectLocationIdsByInterval(dayHours));
        }
        List<Food> rets;

        try {
            PageHelper.startPage(foodReqVO.getPage(), foodReqVO.getPageSize());
            rets = foodMapper.selectByCondition(foodReqVO);
        } finally {
            PageHelper.clearPage();
        }
        return rets;
    }

}
