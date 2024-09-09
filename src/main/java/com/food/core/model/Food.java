package com.food.core.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Food {
    @Id
    @GeneratedValue(
            generator = "JDBC"
    )
    private Long id;

    private Long locationId;

    private String applicant;

    private Integer facilityType;

    private Long cnn;

    private String locationDescription;

    private String address;

    private String block;

    private String lot;

    private String permit;

    private Integer status;

    private String foodItems;

    private Double latitude;

    private Double longitude;

    private String dayHours;

    private Date approved;

    private Date received;

    private Date expiration;

    private Integer priorPermit;

    private Integer firePreventionDistricts;

    private Integer policeDistricts;

    private Integer supervisorDistricts;

    private Integer zipCodes;

}