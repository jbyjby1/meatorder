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

                //标志位，当每一轮meattype都找到了满足的订单时，继续循环，直到有一轮不满足为止
                boolean allConditionsRight = true;
                int allConditionsRightNumber = 0;
                while(allConditionsRight){
                    //本轮计算满足条件的订单，key是折扣策略，value是折扣订单
                    List<DailyOrderExtended> thisModifierOrders = new ArrayList<>();
                    for (Map.Entry<String, List<MeatType>> entry : meatTypeGroupMap.entrySet()){
                        List<MeatType> meatTypes = entry.getValue();
                        for (DailyOrderExtended dailyOrderExtended : dailyOrderExtendeds){
                            if(dailyOrderExtended.getRemainDiscountedNumber() == 0){
                                //已经被计算了折扣，直接跳过
                                continue;
                            }
                            logger.info("Daily meat: {}", dailyOrderExtended.getMeat());
                            if(meatTypes.contains(meatAndTypeMap.get(dailyOrderExtended.getMeat()))){
                                //本订单满足条件，记录一下
                                dailyOrderExtended.setAlreadyDiscountedNumber(dailyOrderExtended.getAlreadyDiscountedNumber() + 1);
                                thisModifierOrders.add(dailyOrderExtended);
                                break;
                            }
                        }
                    }
                    if(thisModifierOrders.size() != 0 && thisModifierOrders.size() == meatTypeGroupMap.size()){
                        //本轮找到了所有满足条件的订单，记录折扣数量+1，直接开启下一轮查找
                        allConditionsRightNumber++;
                    }else{
                        //本轮没有找到所有满足条件的订单，将已经记录的订单退回
                        allConditionsRight = false;
                        for (DailyOrderExtended alreadyRecordOrder : thisModifierOrders){
                            alreadyRecordOrder.setAlreadyDiscountedNumber(alreadyRecordOrder.getAlreadyDiscountedNumber() - 1);
                        }
                    }
                }
                //根据满减数量增加modifier
                if(allConditionsRightNumber != 0){
                    for (int i = 0; i < allConditionsRightNumber; i++){
                        result.add(currentModifier);
                    }
                }
            }
        } catch (Exception e){
            logger.error("Deal with modifier error.", e);
        }
        return result;
    }





}
