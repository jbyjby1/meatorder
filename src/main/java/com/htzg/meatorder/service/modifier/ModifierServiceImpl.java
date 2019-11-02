package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.dao.ModifierMapper;
import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.domain.modifier.ModifierExtended;
import com.htzg.meatorder.domain.modifier.ModifierType;
import com.htzg.meatorder.domain.modifier.OrderModifiers;
import com.htzg.meatorder.domain.modifier.RsCountedModifier;
import com.htzg.meatorder.service.tools.DateService;
import com.htzg.meatorder.util.JsonUtils;
import com.htzg.meatorder.util.ModifierUtils;
import com.htzg.meatorder.util.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModifierServiceImpl implements ModifierService {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ModifierMapper modifierMapper;

    //TODO:本类的方法异常处理要做一下
    @Override
    public List<Modifier> queryAllModifiers() {
        List<Modifier> modifiers = modifierMapper.selectByExample(null);
        return modifiers;
    }

    @Override
    public boolean addModifier(Modifier modifier) {
        modifier.setCreateTime(LocalDateTime.now());
        modifier.setStartTime(modifier.getCreateTime());
        modifier.setEndTime(modifier.getCreateTime().plus(10, ChronoUnit.YEARS));
        modifierMapper.insert(modifier);
        return false;
    }

    public RsAllOrders countByModifier(RsAllOrders rsAllOrders){
        //没有订单直接返回
        if(CollectionUtils.isEmpty(rsAllOrders.getMeatOrders())){
            return rsAllOrders;
        }

        //所有的折扣
        List<OrderModifiers> orderModifiersList = new ArrayList<>();

        //第一步，按照时间归类所有订单
        List<DailyOrderExtended> allDailyOrders = new ArrayList<>();
        for (MeatOrder meatOrder : rsAllOrders.getMeatOrders()){
            allDailyOrders.addAll(meatOrder.getOrders());
        }
        //获取到最早和最晚时间用于生成折扣
        //找到所有时间中最早和最晚的时间
        List<LocalDateTime> allOrderTimes = allDailyOrders.stream()
                .map(DailyOrderExtended::getUpdateTime).collect(Collectors.toList());
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
        //获取到所有的饭店
        List<String> allShops = allDailyOrders.stream()
                .map(DailyOrderExtended::getShop).collect(Collectors.toList());
        //获取到所有的折扣信息
        List<Modifier> discounts = queryContitionDiscounts(earliestTime, latestTime, allShops);

        //根据时间生成key用于计算折扣
        Map<String, List<DailyOrderExtended>> dailyOrderExtendedMap = allDailyOrders.stream()
                .collect(Collectors.groupingBy(DailyOrderExtended::getDisplayOrderTime));
        for (Map.Entry<String, List<DailyOrderExtended>> entry : dailyOrderExtendedMap.entrySet()){
            String key = entry.getKey();
            List<DailyOrderExtended> currentOrders = entry.getValue();
            List<Modifier> applyModifiers = discountService
                    .countByDiscount(currentOrders, discounts, rsAllOrders.getAllMenus());
            OrderModifiers orderModifiers = new OrderModifiers();
            orderModifiers.setDisplayTime(key);
            orderModifiers.setAllModifiers(applyModifiers);
            orderModifiersList.add(orderModifiers);
        }

        //放入按时间生成订单统计的最终结果
        rsAllOrders.setAllOrderModifiers(orderModifiersList);

        //计算本次查询所有的折扣统计结果
        Map<ModifierExtended, Long> allModifiersCountMap = new HashMap<>();
        for (OrderModifiers orderModifiers : rsAllOrders.getAllOrderModifiers()){
            //每天的折扣
            Map<ModifierExtended, Long> countedModifiers = orderModifiers.getCountedModifiersMap();
            for (Map.Entry<ModifierExtended, Long> entry : countedModifiers.entrySet()){
                if(allModifiersCountMap.containsKey(entry.getKey())){
                    //如果结果中已经有这个key，则数量相加
                    Long originCount = allModifiersCountMap.get(entry.getKey());
                    allModifiersCountMap.put(entry.getKey(), originCount + entry.getValue());
                }else{
                    //如果结果中还没有这个key，则新建key
                    allModifiersCountMap.put(entry.getKey(), entry.getValue());
                }
            }
        }

        OrderModifiers orderModifiers = new OrderModifiers();
        String displayTime = OrderUtils.getOrdersTimeStr(allOrderTimes);
        orderModifiers.setDisplayTime(displayTime);
        orderModifiers.setCountedModifiersMap(allModifiersCountMap);
        rsAllOrders.setAllModifiersCount(orderModifiers);
        return rsAllOrders;
    }

    @Override
    public OrderModifiers countByModifier(List<DailyOrder> dailyOrders, List<Menu> allMenus) {
        //第一步，转换所有订单
        List<DailyOrderExtended> allDailyOrders = dailyOrders.stream()
                .map(OrderUtils::dailyOrderToExtended).collect(Collectors.toList());
        LocalDateTime earliestTime = OrderUtils.getOrdersEarliestTime(allDailyOrders);
        LocalDateTime latestTime = OrderUtils.getOrdersLatestTime(allDailyOrders);
        //获取到所有的饭店
        List<String> allShops = allDailyOrders.stream()
                .map(DailyOrderExtended::getShop).collect(Collectors.toList());
        //获取到所有的折扣信息
        List<Modifier> discounts = queryContitionDiscounts(earliestTime, latestTime, allShops);
        //应用所有的折扣
        List<Modifier> applyModifiers = discountService
                .countByDiscount(allDailyOrders, discounts, allMenus);
        OrderModifiers orderModifiers = new OrderModifiers();
        orderModifiers.setAllModifiers(applyModifiers);
        return orderModifiers;
    }

    /**
     * 根据条件获取到生效的的折扣信息
     * @param earliestTime
     * @param latestTime
     * @param allShops
     * @return
     */
    private List<Modifier> queryContitionDiscounts(LocalDateTime earliestTime, LocalDateTime latestTime,
                                                   List<String> allShops){
        ModifierExample modifierExample = new ModifierExample();
        ModifierExample.Criteria criteria = modifierExample.createCriteria();
        criteria.andStartTimeLessThanOrEqualTo(earliestTime);
        criteria.andEndTimeGreaterThanOrEqualTo(latestTime);
        criteria.andShopIn(allShops);
        List<Modifier> modifiers = modifierMapper.selectByExample(modifierExample);
        //从modifier中获取到折扣列表
        List<Modifier> discounts = modifiers.stream().filter(modifier ->
                ModifierType.DISCOUNT.equals(modifier.getModifierType())).collect(Collectors.toList());
        return discounts;
    }
}
