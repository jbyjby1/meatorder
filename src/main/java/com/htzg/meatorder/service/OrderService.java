package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.RsDailyOrder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jby on 2018/12/28.
 */
public interface OrderService {

    public RsDailyOrder getDailyOrder(LocalDateTime day, String username);

    public Boolean addOrModifyDailyOrder(List<DailyOrder> dailyOrders);

    public RsAllOrders queryAllOrders(LocalDateTime startDate, LocalDateTime endDate);

}
