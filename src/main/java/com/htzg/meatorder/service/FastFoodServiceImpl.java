package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.FastFoodItemMapper;
import com.htzg.meatorder.domain.FastFoodItem;
import com.htzg.meatorder.domain.FastFoodItemExample;
import com.htzg.meatorder.domain.FastFoodItemExtended;
import com.htzg.meatorder.util.JsonUtils;
import com.htzg.meatorder.util.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FastFoodServiceImpl implements FastFoodService {

    public static Logger logger = LoggerFactory.getLogger(FastFoodServiceImpl.class);

    @Autowired
    private FastFoodItemMapper fastFoodItemMapper;

    @Override
    public List<FastFoodItemExtended> getAvailableFastFoodMenus() {
        FastFoodItemExample fastFoodItemExample = new FastFoodItemExample();
        FastFoodItemExample.Criteria criteria = fastFoodItemExample.createCriteria();
        criteria.andAvailableEqualTo(true);
        LocalDateTime nextDay = LocalDateTime.now().plus(Duration.ofDays(1));
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = nextDay.truncatedTo(ChronoUnit.DAYS);
        criteria.andCreateTimeBetween(start, end);
        List<FastFoodItem> fastFoodItems = fastFoodItemMapper.selectByExample(fastFoodItemExample);
        return OrderUtils.fastFoodsToExtended(fastFoodItems);
    }

    /**
     * 更新全量的快餐数据（之前的数据逻辑删除）
     * @throws Exception
     */
    @Override
    public void updateAllFastFoods(List<FastFoodItem> fastFoodItems) throws Exception {
        //逻辑删除
        FastFoodItemExample fastFoodItemExample = new FastFoodItemExample();
        FastFoodItemExample.Criteria criteria = fastFoodItemExample.createCriteria();
        criteria.andAvailableEqualTo(true);
        FastFoodItem fastFoodItem = new FastFoodItem();
        fastFoodItem.setAvailable(false);
        fastFoodItemMapper.updateByExampleSelective(fastFoodItem, fastFoodItemExample);

        //增加新数据
        for (FastFoodItem currentItem : fastFoodItems){
            currentItem.setAvailable(true);
            currentItem.setCreateTime(LocalDateTime.now());
            fastFoodItemMapper.insert(currentItem);
        }
    }

}
