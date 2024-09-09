package com.food.core.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class FoodReqVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3391925059769227078L;

    private List<Long> locationIds;

    private String applicant;

    private Integer facilityType;

    private Long cnn;

    private String locationDescription;

    private String address;

    private String blockLot;

    private String permit;

    private Integer status;

    private String foodItems;

    private Double latitude;

    private Double longitude;

    private Date approvedStart;

    private Date approvedEnd;

    //半径，单位 米
    private Integer radius;

    private Integer priorPermit;

    private Integer firePreventionDistricts;

    private Integer policeDistricts;

    private Integer supervisorDistricts;

    private Integer zipCodes;

    //周一到周日
    private Integer day;

    //24小时制
    private Integer hourStart;

    //24小时制
    private Integer hourEnd;

    private Integer page = 1;

    private Integer pageSize = 10;

}
