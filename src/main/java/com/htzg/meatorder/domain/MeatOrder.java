package com.htzg.meatorder.domain;

import java.util.List;

/**
 * Created by jby on 2018/12/31.
 */
public class MeatOrder {

    private String meat;

    private Integer amount;

    private Float inputPriceSum;

    private List<DailyOrderExtended> orders;

    public String getMeat() {
        return meat;
    }

    public void setMeat(String meat) {
        this.meat = meat;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getInputPriceSum() {
        return inputPriceSum;
    }

    public void setInputPriceSum(Float inputPriceSum) {
        this.inputPriceSum = inputPriceSum;
    }

    public List<DailyOrderExtended> getOrders() {
        return orders;
    }

    public void setOrders(List<DailyOrderExtended> orders) {
        this.orders = orders;
    }
}
