package com.food.core.enums;

public enum WeekEnum {
    MONDAY(1, "Mo"),
    TUESDAY(2, "Tu"),
    WEDNESDAY(3, "We"),
    THURSDAY(4,"Th"),
    FRIDAY(5, "Fr"),
    SATURDAY(6, "Sa"),
    SUNDAY(7, "Su");
    private WeekEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

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

    private Integer key;
    private String value;

    public static String getValueByKey(Integer key) {
        String value = "";
        WeekEnum[] values = WeekEnum.values();
        for (WeekEnum status : values) {
            if (status.key == key) {
                value = status.getValue();
            }
        }
        return value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = 0;
        WeekEnum[] values = WeekEnum.values();
        for (WeekEnum status : values) {
            if (status.value.equals(value)) {
                key = status.getKey();
            }
        }
        return key;
    }

}
