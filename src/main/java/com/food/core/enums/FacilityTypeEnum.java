package com.food.core.enums;

import java.util.Objects;

/**
 * 属性值级别枚举类
 */
public enum FacilityTypeEnum {
    NONE(0,"None"),
    PUSHCART(1,"Push Cart"),
    TRUCK(2,"Truck");

    private FacilityTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    private Integer key;
    private String value;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByKey(Integer key) {
        String value = "";
        FacilityTypeEnum[] values = FacilityTypeEnum.values();
        for (FacilityTypeEnum status : values) {
            if (Objects.equals(status.key, key)) {
                value = status.getValue();
            }
        }
        return value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = 0;
        FacilityTypeEnum[] values = FacilityTypeEnum.values();
        for (FacilityTypeEnum status : values) {
            if (status.value.equals(value)) {
                key = status.getKey();
            }
        }
        return key;
    }
}
