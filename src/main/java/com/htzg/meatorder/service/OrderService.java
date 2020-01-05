package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.domain.modifier.OrderModifiers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jby on 2018/12/28.
 */
public interface OrderService {

    public RsDailyOrder getDailyOrder(LocalDateTime day, String shopName, String username, boolean splitSupper);

    public Boolean addOrModifyDailyOrder(List<DailyOrder> dailyOrders, boolean splitSupper);

    public boolean modifyDailyOrderStatus(int dailyOrderId, OrderStatus status);

    public RsAllOrders queryAllOrders(LocalDateTime startDate,
                                      LocalDateTime endDate, String shopName, List<SupportOrderStatus> statusList,
                                      boolean onlySupper);

    public List<String> queryDailyOrderPersons();

    /**
     * 点餐时根据当日订单获取到所有的修正信息
     * @param dailyOrders 当日订单
     * @return 修正信息
     */
    public OrderModifiers getDailyOrderModifiers(List<DailyOrder> dailyOrders);

}
