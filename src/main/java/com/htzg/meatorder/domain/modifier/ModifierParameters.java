package com.htzg.meatorder.domain.modifier;

import com.htzg.meatorder.domain.menu.MeatType;

import java.util.List;
import java.util.Map;

public class ModifierParameters {

    /**
     *  key： 菜品分组
     *  value：对于菜品类型的条件，如果所有类型都可以就在list中写入所有的类型
     */
    private Map<String, List<MeatType>> meatTypeConditions;

    public Map<String, List<MeatType>> getMeatTypeConditions() {
        return meatTypeConditions;
    }

    public void setMeatTypeConditions(Map<String, List<MeatType>> meatTypeConditions) {
        this.meatTypeConditions = meatTypeConditions;
    }
}
