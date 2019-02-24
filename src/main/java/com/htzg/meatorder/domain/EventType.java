package com.htzg.meatorder.domain;

public enum EventType {

    DAILY_LOCK_ORDERS("dailyLockOrders", "锁定订单");

    EventType(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public static EventType valueFrom(String value){
        for (EventType eventType : EventType.values()){
            if(eventType.getValue().equals(value)){
                return eventType;
            }
        }
        return null;
    }

    private String value;

    private String desc;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
