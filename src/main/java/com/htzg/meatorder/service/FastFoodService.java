package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.FastFoodItem;
import com.htzg.meatorder.domain.FastFoodItemExtended;

import java.util.List;

public interface FastFoodService {

    public List<FastFoodItemExtended> getAvailableFastFoodMenus();

    public void updateAllFastFoods(List<FastFoodItem> fastFoodItems) throws Exception;

}
