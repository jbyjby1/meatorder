package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.dao.ModifierMapper;
import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.domain.modifier.ModifierExtended;
import com.htzg.meatorder.domain.modifier.ModifierType;
import com.htzg.meatorder.domain.modifier.OrderModifiers;
import com.htzg.meatorder.service.tools.DateService;
import com.htzg.meatorder.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
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

        ModifierExample modifierExample = new ModifierExample();
        ModifierExample.Criteria criteria = modifierExample.createCriteria();
        criteria.andStartTimeLessThanOrEqualTo(earliestTime);
        criteria.andEndTimeGreaterThanOrEqualTo(latestTime);
        criteria.andShopIn(allShops);
        List<Modifier> modifiers = modifierMapper.selectByExample(modifierExample);
        //从modifier中获取到折扣列表
        List<Modifier> discounts = modifiers.stream().filter(modifier ->
                ModifierType.DISCOUNT.equals(modifier.getModifierType())).collect(Collectors.toList());

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

        //放入最终结果
        rsAllOrders.setAllOrderModifiers(orderModifiersList);
        return rsAllOrders;
    }
}
