package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.domain.DailyOrderExtended;
import com.htzg.meatorder.domain.Menu;
import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.menu.MeatType;
import com.htzg.meatorder.domain.modifier.DiscountType;
import com.htzg.meatorder.domain.modifier.ModifierExtended;
import com.htzg.meatorder.domain.modifier.ModifierType;
import com.htzg.meatorder.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    public static Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    @Override
    public List<Modifier> countByDiscount(List<DailyOrderExtended> dailyOrderExtendeds, List<Modifier> modifier, List<Menu> allMenus) {
        //根据modifier优先级排序
        modifier.sort((a, b) -> a.getPriority() - b.getPriority());
        //根据菜单生成相应的菜品 -> 菜品类型的map
        Map<String, MeatType> meatAndTypeMap = new HashMap<>();
        for (Menu menu : allMenus){
            meatAndTypeMap.put(menu.getMeat(), menu.getMeatType());
        }
        //最终结果
        List<Modifier> result = new ArrayList<>();
        //挨个获取modifier计算
        try{

            for (Modifier currentModifier : modifier){
                ModifierExtended modifierExtended = JsonUtils.fromJson(JsonUtils.toJson(currentModifier), ModifierExtended.class);
                logger.info("start to deal modifier: {}", JsonUtils.toJson(modifierExtended));
                Map<String, List<MeatType>> meatTypeGroupMap = modifierExtended.getRealModifierParameters().getMeatTypeConditions();

                //本轮计算满足条件的订单，key是折扣策略，value是折扣订单
                Map<String, List<DailyOrderExtended>> thisModifierOrders = new HashMap<>();
                for (Map.Entry<String, List<MeatType>> entry : meatTypeGroupMap.entrySet()){
                    List<MeatType> meatTypes = entry.getValue();
                    List<DailyOrderExtended> currentModifierOrders = dailyOrderExtendeds.stream().filter(dailyOrderExtended -> {
                        if(meatTypes.contains(meatAndTypeMap.get(dailyOrderExtended.getMeat()))){
                            return true;
                        }else{
                            return false;
                        }
                    }).collect(Collectors.toList());
//
//                    for (DailyOrderExtended dailyOrderExtended : dailyOrderExtendeds){
//                        if(dailyOrderExtended.getAmount() - dailyOrderExtended.getAlreadyDiscountedNumber() == 0){
//                            //已经被计算了折扣，直接跳过
//                            continue;
//                        }
//                        logger.info("Daily meat: {}", dailyOrderExtended.getMeat());
//                        if(meatTypes.contains(meatAndTypeMap.get(dailyOrderExtended.getMeat()))){
//                            //本订单满足条件，记录一下
//                            dailyOrderExtended.setAlreadyDiscounted();
//                            currentModifierOrders.add(dailyOrderExtended);
//                        }
//                    }
                    thisModifierOrders.put(entry.getKey(), currentModifierOrders);
                }
                //查看满足多个条件的订单数量，取一个最小值，每个部分将最小值部分记录折扣，其余改回false
                int discountNumber = Integer.MAX_VALUE;
                for(List<DailyOrderExtended> currentCondition : thisModifierOrders.values()){
                    int amount = currentCondition.stream().map(DailyOrderExtended::getRemainDiscountedNumber)
                            .reduce((a, b) -> a + b).orElseGet(() -> 0);
                    if(amount < discountNumber){
                        discountNumber = amount;
                    }
                }
                for(List<DailyOrderExtended> currentCondition : thisModifierOrders.values()){
                    int amount = currentCondition.stream().map(DailyOrderExtended::getRemainDiscountedNumber)
                            .reduce((a, b) -> a + b).orElseGet(() -> 0);
                    if(discountNumber == 0 || CollectionUtils.isEmpty(currentCondition)){
                        continue;
                    }
                    //TODO:遍历所有的订单，把discountNumber数量的订单变成已优惠
                }
                for (int i = 0; i < discountNumber; i++){
                    result.add(currentModifier);
                }
            }
        } catch (Exception e){
            logger.error("Deal with modifier error.", e);
        }
        return result;
    }





}
