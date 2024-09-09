package com.food.core.mapper;

import com.food.core.data.FoodReqVO;
import com.food.core.model.Food;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface FoodMapper extends Mapper<Food>, MySqlMapper<Food> {
    List<Food> selectByCondition(FoodReqVO foodReqVO);
}