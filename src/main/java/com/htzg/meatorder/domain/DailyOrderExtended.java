package com.htzg.meatorder.domain;

public class DailyOrderExtended extends DailyOrder {

    public String getDisplayStatus(){
        OrderStatus status = getStatus();
        if(status != null){
            return status.getMessage();
        }else{
            return null;
        }
    }
}
