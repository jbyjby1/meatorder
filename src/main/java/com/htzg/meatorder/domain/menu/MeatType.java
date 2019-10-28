package com.htzg.meatorder.domain.menu;

public enum MeatType {

    PILAFF("盖浇饭"), FRY("炒菜"), NOODLE("面食"),
    RICE_NOODLE("米粉"), PICKLE("小菜"), DRINK("饮料"),
    CHINESE_HAMBURGER("肉夹馍"), PORRIDGE("汤粥类"), RICE("米饭");

    private String displayType;

    MeatType(String displayType){
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
