package com.htzg.meatorder.domain.menu;

public enum FastFoodType {

    PUNGENT("荤菜"), VEGETABLE("素菜");

    private String displayType;

    FastFoodType(String displayType){
        this.displayType = displayType;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
