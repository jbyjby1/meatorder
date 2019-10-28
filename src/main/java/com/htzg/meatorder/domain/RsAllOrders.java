package com.htzg.meatorder.domain;

import com.htzg.meatorder.domain.modifier.OrderModifiers;

import java.util.List;
import java.util.Map;

/**
 * Created by jby on 2018/12/31.
 */
public class RsAllOrders {

    private List<MeatOrder> meatOrders;

    private List<PersonOrder> personOrders;

    private List<Menu> allMenus;

    private List<OrderModifiers> allOrderModifiers;

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

    public List<Menu> getAllMenus() {
        return allMenus;
    }

    public void setAllMenus(List<Menu> allMenus) {
        this.allMenus = allMenus;
    }

    public List<OrderModifiers> getAllOrderModifiers() {
        return allOrderModifiers;
    }

    public void setAllOrderModifiers(List<OrderModifiers> allOrderModifiers) {
        this.allOrderModifiers = allOrderModifiers;
    }
}
