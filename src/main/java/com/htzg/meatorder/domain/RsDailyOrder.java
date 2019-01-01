package com.htzg.meatorder.domain;

import java.util.List;

/**
 * Created by jby on 2018/12/28.
 */
public class RsDailyOrder {

    private List<DailyOrder> orders;

    public RsDailyOrder(){}

    public RsDailyOrder(List<DailyOrder> orders) {
        this.orders = orders;
    }

    public List<DailyOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<DailyOrder> orders) {
        this.orders = orders;
    }
}
