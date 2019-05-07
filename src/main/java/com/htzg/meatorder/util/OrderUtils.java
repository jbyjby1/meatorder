package com.htzg.meatorder.util;

import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.DailyOrderExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于处理订单相关的工具类
 */
public class OrderUtils {

    public static Logger logger = LoggerFactory.getLogger(OrderUtils.class);

    public static String getOrderNameWithShop(DailyOrder dailyOrder){
        return dailyOrder.getMeat() + " - " + dailyOrder.getFlavor() + " - " + dailyOrder.getShop();
    }

    public static DailyOrderExtended dailyOrderToExtended(DailyOrder dailyOrder){
        try{
            DailyOrderExtended dailyOrderExtended =
                    JsonUtils.fromJson(JsonUtils.toJson(dailyOrder), DailyOrderExtended.class);
            return dailyOrderExtended;
        }catch (Exception e){
            logger.error("convert daily order to extended error.", e);
            return null;
        }
    }
}
