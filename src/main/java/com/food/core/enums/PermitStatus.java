package com.food.core.enums;

public enum PermitStatus {
    REQUESTED(0, "REQUESTED"),
    APPROVED(1, "APPROVED"),
    EXPIRED(2, "EXPIRED"),
    SUSPEND(3,"SUSPEND"),
    ISSUED(4, "ISSUED");
    private PermitStatus(Integer key, String value) {
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
        PermitStatus[] values = PermitStatus.values();
        for (PermitStatus status : values) {
            if (status.key == key) {
                value = status.getValue();
            }
        }
        return value;
    }

    public static Integer getKeyByValue(String value) {
        Integer key = 0;
        PermitStatus[] values = PermitStatus.values();
        for (PermitStatus status : values) {
            if (status.value.equals(value)) {
                key = status.getKey();
            }
        }
        return key;
    }

}
