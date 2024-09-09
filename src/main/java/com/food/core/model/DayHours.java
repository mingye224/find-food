package com.food.core.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class DayHours {
    @Id
    @GeneratedValue(
            generator = "JDBC"
    )
    private Long id;

    private Long locationId;

    private Integer day;

    private Integer startHour;

    private Integer endHour;
}
