package com.htzg.meatorder.util;

import com.htzg.meatorder.domain.DailyOrder;

/**
 * 用于处理订单相关的工具类
 */
public class OrderUtils {

    public static String getOrderNameWithShop(DailyOrder dailyOrder){
        return dailyOrder.getMeat() + " - " + dailyOrder.getShop();
    }
}
