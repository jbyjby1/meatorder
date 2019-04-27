package com.htzg.meatorder.domain;

public class SupportOrderStatus {

    private OrderStatus status;

    public SupportOrderStatus(){

    }

    public SupportOrderStatus(OrderStatus status) {
        this.status = status;
    }



    public String getDisplayStatus(){
        OrderStatus status = getOrderStatus();
        if(status != null){
            return status.getMessage();
        }else{
            return null;
        }
    }

    //这两个函数为了能够反序列化
    public void setActive(Boolean active){

    }

    public void setDisplayStatus(String displayStatus){

    }

    public OrderStatus getOrderStatus() {
        return status;
    }

    public void setOrderStatus(OrderStatus status) {
        this.status = status;
    }
}
