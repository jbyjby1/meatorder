package com.htzg.meatorder.domain;

import java.util.List;

/**
 * Created by jby on 2018/12/31.
 */
public class RsAllOrders {

    private List<MeatOrder> meatOrders;

    private List<PersonOrder> personOrders;

    public List<MeatOrder> getMeatOrders() {
        return meatOrders;
    }

    public void setMeatOrders(List<MeatOrder> meatOrders) {
        this.meatOrders = meatOrders;
    }

    public List<PersonOrder> getPersonOrders() {
        return personOrders;
    }

    public void setPersonOrders(List<PersonOrder> personOrders) {
        this.personOrders = personOrders;
    }
}
