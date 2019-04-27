package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jby on 2018/12/28.
 */
public interface OrderService {

    public RsDailyOrder getDailyOrder(LocalDateTime day, String shopName, String username);

    public Boolean addOrModifyDailyOrder(List<DailyOrder> dailyOrders);

    public boolean modifyDailyOrderStatus(int dailyOrderId, OrderStatus status);

    public RsAllOrders queryAllOrders(LocalDateTime startDate,
                                      LocalDateTime endDate, String shopName, List<SupportOrderStatus> statusList);

    public List<String> queryDailyOrderPersons();

}
