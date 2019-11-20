package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.menu.RsMenus;

import java.util.List;

/**
 * Created by jby on 2019/1/1.
 */
public interface MenuService {


    public RsMenus queryMenus(String shop, String flavor);

    public RsMenus queryMenus(String meatName, String flavor, String shop, boolean strict);

    public Boolean deleteMenu(String meatName);

    public Boolean addMenu(RsMenus rsMenus);

    public Boolean modifyMenu(RsMenus rsMenus);

    public List<String> validateOrder(List<DailyOrder> dailyOrders, String shop);

}
