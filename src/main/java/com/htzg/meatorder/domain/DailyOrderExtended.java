package com.htzg.meatorder.domain;

import com.htzg.meatorder.util.OrderUtils;

public class DailyOrderExtended extends DailyOrder {

    /**
     * 已经被计算了折扣的数量
     */
    private int alreadyDiscountedNumber;

    public int getRemainDiscountedNumber(){
        return this.getAmount() - alreadyDiscountedNumber;
    }

    public String getDisplayStatus(){
        OrderStatus status = getStatus();
        if(status != null){
            return status.getMessage();
        }else{
            return null;
        }
    }

    public String getDisplayOrderTime(){
        return OrderUtils.getOrderTimeStr(getUpdateTime());
    }

    public int getAlreadyDiscountedNumber() {
        return alreadyDiscountedNumber;
    }

    public void setAlreadyDiscountedNumber(int alreadyDiscountedNumber) {
        this.alreadyDiscountedNumber = alreadyDiscountedNumber;
    }
}
