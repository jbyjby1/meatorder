package com.htzg.meatorder.domain.menu;

import com.htzg.meatorder.domain.FastFoodItem;
import com.htzg.meatorder.domain.FastFoodItemExtended;

import java.util.List;

public class RsFastFoods {

    private List<FastFoodItemExtended> fastFoodItems;

    public List<FastFoodItemExtended> getFastFoodItems() {
        return fastFoodItems;
    }

    public void setFastFoodItems(List<FastFoodItemExtended> fastFoodItems) {
        this.fastFoodItems = fastFoodItems;
    }
}
