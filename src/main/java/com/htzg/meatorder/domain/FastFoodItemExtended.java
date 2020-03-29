package com.htzg.meatorder.domain;

import com.htzg.meatorder.util.OrderUtils;

public class FastFoodItemExtended extends FastFoodItem {

    public String getDisplayType(){
        return this.getFastFoodType().getDisplayType();
    }

}
