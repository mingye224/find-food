package com.food.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitConfig implements ApplicationListener<ApplicationStartedEvent> {

    private final FoodService foodService;

    @Autowired
    public InitConfig(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * 项目启动时，初始化参数
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        foodService.readCSVtoSaveFood("Mobile_Food_Facility_Permit.csv");
    }
}
