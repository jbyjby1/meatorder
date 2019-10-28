package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.domain.DailyOrderExtended;
import com.htzg.meatorder.domain.Menu;
import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.domain.RsAllOrders;

import java.util.List;
import java.util.Map;

public interface DiscountService {

    /**
     * 根据不同的折扣返回
     * @param dailyOrderExtendeds 所有的订单
     * @param modifier 所有折扣列表
     * @return 本部分订单叠加应用的多个折扣
     */
    public List<Modifier> countByDiscount(List<DailyOrderExtended> dailyOrderExtendeds, List<Modifier> modifier, List<Menu> allMenus);

}
