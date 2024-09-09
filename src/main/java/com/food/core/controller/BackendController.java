package com.food.core.controller;

import com.food.core.data.FoodReqVO;
import com.food.core.model.Food;
import com.food.core.service.FoodService;
import com.food.core.utils.Result;
import com.food.core.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class BackendController {

    private final FoodService foodService;

    @Autowired
    public BackendController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping(value = "/findFoodFacility")
    public Result<PageInfo<Food>> findFoodFacility(@RequestBody FoodReqVO foodReqVO) {

        return ResultUtil.success(new PageInfo<>(foodService.findFoodFacility(foodReqVO)));
    }

}
