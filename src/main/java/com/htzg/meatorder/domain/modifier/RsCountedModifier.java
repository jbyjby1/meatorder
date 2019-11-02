package com.htzg.meatorder.domain.modifier;

/**
 * 用于返回前端展示的modifier统计情况
 */
public class RsCountedModifier {

    private Integer id;

    private String displayName;

    private Long count;

    private Float modifierValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Float getModifierValue() {
        return modifierValue;
    }

    public void setModifierValue(Float modifierValue) {
        this.modifierValue = modifierValue;
    }
}
