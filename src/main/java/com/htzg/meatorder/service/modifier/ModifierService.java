package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.Menu;
import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.modifier.ModifierExtended;
import com.htzg.meatorder.domain.modifier.OrderModifiers;

import java.util.List;

public interface ModifierService {

    public List<Modifier> queryAllModifiers();

    public boolean addModifier(Modifier modifier);

    /**
     * 查询当日所有订单获取修正信息
     * @param rsAllOrders
     * @return
     */
    public RsAllOrders countByModifier(RsAllOrders rsAllOrders);

    /**
     * 订餐时查询当日订单获取折扣信息
     * @param dailyOrders
     * @param allMenus
     * @return
     */
    public OrderModifiers countByModifier(List<DailyOrder> dailyOrders, List<Menu> allMenus);

}
