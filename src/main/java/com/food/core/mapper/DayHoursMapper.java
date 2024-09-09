package com.food.core.mapper;

import com.food.core.model.DayHours;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface DayHoursMapper extends Mapper<DayHours>, MySqlMapper<DayHours> {

    List<Long> selectLocationIdsByInterval(DayHours dayHours);
}