package com.htzg.meatorder.domain;

import java.time.Instant;
import java.util.List;

/**
 * Created by jby on 2018/12/31.
 */
public class PersonOrder {

    private String username;

    private Float inputPriceSum;

    private List<DailyOrderExtended> orders;

    private boolean lucky;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isLucky() {
        return lucky;
    }

    public void setLucky(boolean lucky) {
        this.lucky = lucky;
    }
}
