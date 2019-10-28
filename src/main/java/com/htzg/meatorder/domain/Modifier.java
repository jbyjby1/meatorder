package com.htzg.meatorder.domain;

import com.htzg.meatorder.domain.modifier.DiscountType;
import com.htzg.meatorder.domain.modifier.ModifierType;

import java.time.LocalDateTime;

public class Modifier {
    private Integer id;

    private ModifierType modifierType;

    private String shop;

    private DiscountType discountType;

    private Integer priority;

    private LocalDateTime createTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String displayName;

    private Float modifierValue;

    private String modifierParameters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ModifierType getModifierType() {
        return modifierType;
    }

    public void setModifierType(ModifierType modifierType) {
        this.modifierType = modifierType;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Float getModifierValue() {
        return modifierValue;
    }

    public void setModifierValue(Float modifierValue) {
        this.modifierValue = modifierValue;
    }

    public String getModifierParameters() {
        return modifierParameters;
    }

    public void setModifierParameters(String modifierParameters) {
        this.modifierParameters = modifierParameters;
    }
}