package com.htzg.meatorder.domain;

import java.time.LocalDateTime;

public class DailyChicken {
    private Integer id;

    private Integer chickenNumber;

    private ChickenType chickenType;

    private String chickenName;

    private String chickenId;

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChickenNumber() {
        return chickenNumber;
    }

    public void setChickenNumber(Integer chickenNumber) {
        this.chickenNumber = chickenNumber;
    }

    public ChickenType getChickenType() {
        return chickenType;
    }

    public void setChickenType(ChickenType chickenType) {
        this.chickenType = chickenType;
    }

    public String getChickenName() {
        return chickenName;
    }

    public void setChickenName(String chickenName) {
        this.chickenName = chickenName;
    }

    public String getChickenId() {
        return chickenId;
    }

    public void setChickenId(String chickenId) {
        this.chickenId = chickenId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}