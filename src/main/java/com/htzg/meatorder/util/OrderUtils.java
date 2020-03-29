package com.htzg.meatorder.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.DailyOrderExtended;
import com.htzg.meatorder.domain.FastFoodItem;
import com.htzg.meatorder.domain.FastFoodItemExtended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.htzg.meatorder.util.CommonConstant.DAILY_SUPPER_TIME;

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

    /**
     * 根据订单的时间，获取到应该展示在excel中的时间
     * @param allOrderTimes 所有的订单时间
     * @return 应该在excel时间字段的字符串
     */
    public static String getOrdersTimeStr(List<LocalDateTime> allOrderTimes){
        if(CollectionUtils.isEmpty(allOrderTimes)){
            //空list直接返回空字符串
            return "";
        }
        //找到所有时间中最早和最晚的时间
        LocalDateTime earliestTime = LocalDateTime.MAX;
        LocalDateTime latestTime = LocalDateTime.MIN;
        for (LocalDateTime currentTime : allOrderTimes){
            if(currentTime.isBefore(earliestTime)){
                earliestTime = currentTime;
            }
            if(currentTime.isAfter(latestTime)){
                latestTime = currentTime;
            }
        }
        //根据最早和最晚时间写范围
        if(earliestTime.truncatedTo(ChronoUnit.DAYS).equals(latestTime.truncatedTo(ChronoUnit.DAYS))){
            //如果两者在同一天，那么只展示当天，并判断时间，有14:00之后的订单认为是晚上
            if(latestTime.isAfter(latestTime.truncatedTo(ChronoUnit.DAYS).plus(DAILY_SUPPER_TIME, ChronoUnit.HOURS))){
                return latestTime.toLocalDate().toString() + "晚";
            }else{
                //最晚订单不在14:00之后，认为是中午订单
                return latestTime.toLocalDate().toString() + "中午";
            }
        }else{
            //如果两者不在同一天，那么展示从开始那天到结束那天的范围
            return earliestTime.toLocalDate().toString() + "到"
                    + latestTime.toLocalDate().toString();
        }
    }

    public static String getOrderTimeStr(LocalDateTime orderTime) {
        if(orderTime == null){
            //空直接返回空字符串
            return "";
        }
        //14:00之后的订单认为是晚上
        if(orderTime.isAfter(orderTime.truncatedTo(ChronoUnit.DAYS).plus(DAILY_SUPPER_TIME, ChronoUnit.HOURS))){
            return orderTime.toLocalDate().toString() + "晚";
        }else{
            //最晚订单不在14:00之后，认为是中午订单
            return orderTime.toLocalDate().toString() + "中午";
        }
    }

    public static LocalDateTime getOrdersEarliestTime(List<DailyOrderExtended> dailyOrders){
        if(CollectionUtils.isEmpty(dailyOrders)){
            logger.error("Get daily order earliest time error. Order empty.");
            return LocalDateTime.MIN;
        }
        List<LocalDateTime> allOrderTimes = dailyOrders.stream()
                .map(dailyOrder -> {
                    if(dailyOrder.getUpdateTime() == null){
                        return LocalDateTime.now();
                    }else{
                        return dailyOrder.getUpdateTime();
                    }
                }).collect(Collectors.toList());
        LocalDateTime earliestTime = LocalDateTime.MAX;
        for (LocalDateTime currentTime : allOrderTimes){
            if(currentTime.isBefore(earliestTime)){
                earliestTime = currentTime;
            }
        }
        return earliestTime;
    }

    public static LocalDateTime getOrdersLatestTime(List<DailyOrderExtended> dailyOrders){
        if(CollectionUtils.isEmpty(dailyOrders)){
            logger.error("Get daily order latest time error. Order empty.");
            return LocalDateTime.MAX;
        }
        List<LocalDateTime> allOrderTimes = dailyOrders.stream()
                .map(dailyOrder -> {
                    if(dailyOrder.getUpdateTime() == null){
                        return LocalDateTime.now();
                    }else{
                        return dailyOrder.getUpdateTime();
                    }
                }).collect(Collectors.toList());
        LocalDateTime latestTime = LocalDateTime.MIN;
        for (LocalDateTime currentTime : allOrderTimes){
            if(currentTime.isAfter(latestTime)){
                latestTime = currentTime;
            }
        }
        return latestTime;
    }

    public static List<FastFoodItemExtended> fastFoodsToExtended(List<FastFoodItem> fastFoodItems){
        try{
            List<FastFoodItemExtended> fastFoods =
                    JsonUtils.fromJson(JsonUtils.toJson(fastFoodItems), new TypeReference<List<FastFoodItemExtended>>() {
                    });
            return fastFoods;
        }catch (Exception e){
            logger.error("convert FastFoodItem to extended error.", e);
            return new ArrayList<>();
        }
    }
}
