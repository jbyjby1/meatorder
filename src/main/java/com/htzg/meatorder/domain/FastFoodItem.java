package com.htzg.meatorder.domain;

import com.htzg.meatorder.domain.menu.FastFoodType;

import java.time.LocalDateTime;

public class FastFoodItem {
    private Integer id;

    private FastFoodType fastFoodType;

    private String fastFoodName;

    private LocalDateTime createTime;

    private boolean available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FastFoodType getFastFoodType() {
        return fastFoodType;
    }

    public void setFastFoodType(FastFoodType fastFoodType) {
        this.fastFoodType = fastFoodType;
    }

    public String getFastFoodName() {
        return fastFoodName;
    }

    public void setFastFoodName(String fastFoodName) {
        this.fastFoodName = fastFoodName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}