package com.htzg.meatorder.domain.menu;

import com.htzg.meatorder.domain.FastFoodItem;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RsUpdateFastFood {

    private String pungent;

    private String vegetable;

    public List<FastFoodItem> toItems(){
        List<FastFoodItem> result = new ArrayList<>();
        if(StringUtils.isNotBlank(pungent)){
            for (String currentName : pungent.split(" ")){
                if(StringUtils.isNotBlank(currentName)){
                    FastFoodItem fastFoodItem = new FastFoodItem();
                    fastFoodItem.setFastFoodName(currentName.trim());
                    fastFoodItem.setFastFoodType(FastFoodType.PUNGENT);
                    result.add(fastFoodItem);
                }
            }
        }
        if(StringUtils.isNotBlank(vegetable)){
            for (String currentName : vegetable.split(" ")){
                if(StringUtils.isNotBlank(currentName)){
                    FastFoodItem fastFoodItem = new FastFoodItem();
                    fastFoodItem.setFastFoodName(currentName.trim());
                    fastFoodItem.setFastFoodType(FastFoodType.VEGETABLE);
                    result.add(fastFoodItem);
                }
            }
        }
        return result;
    }

    public String getPungent() {
        return pungent;
    }

    public void setPungent(String pungent) {
        this.pungent = pungent;
    }

    public String getVegetable() {
        return vegetable;
    }

    public void setVegetable(String vegetable) {
        this.vegetable = vegetable;
    }
}
