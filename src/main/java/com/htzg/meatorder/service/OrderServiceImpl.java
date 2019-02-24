package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.DailyOrderMapper;
import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.util.OrderUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jby on 2018/12/28.
 */
@Service
public class OrderServiceImpl implements OrderService{

    public static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private DailyOrderMapper dailyOrderMapper;

    @Autowired
    private MenuService menuService;

    @Override
    public RsDailyOrder getDailyOrder(LocalDateTime day, String username) {
        LocalDateTime nextDay = day.plus(Duration.ofDays(1));
        LocalDateTime start = day.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = nextDay.truncatedTo(ChronoUnit.DAYS);
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        if(StringUtils.isNotBlank(username)){
            criteria.andUsernameEqualTo(username);
        }else{
            List<DailyOrder> dailyOrders = new ArrayList<>();
            return new RsDailyOrder(dailyOrders);
        }
        List<DailyOrder> dailyOrders = dailyOrderMapper.selectByExample(dailyOrderExample);
        RsDailyOrder rsDailyOrder = new RsDailyOrder(dailyOrders);
        return rsDailyOrder;
    }

    @Override
    public Boolean addOrModifyDailyOrder(List<DailyOrder> dailyOrders) {
        List<String> usernames = dailyOrders.stream().map(DailyOrder::getUsername).distinct().collect(Collectors.toList());
        //删除原来的订单
        this.deleteTodayOrdersForUsers(usernames);
        long successNum = dailyOrders.stream().filter(dailyOrder ->
                dailyOrder.getAmount() != null && dailyOrder.getAmount() > 0).map(dailyOrder -> {
            dailyOrder.setId(null);
            dailyOrder.setCreateTime(LocalDateTime.now());
            dailyOrder.setUpdateTime(LocalDateTime.now());
            dailyOrder.setUnit("份");
            return dailyOrder;
        }).map(dailyOrder -> {
            //顺便增加新菜单
            RsMenus rsMenus = menuService.queryMenus(dailyOrder.getMeat(), dailyOrder.getShop(), true);
            if(CollectionUtils.isEmpty(rsMenus.getMenus())){
                if(dailyOrder.getInputPrice() == null){
                    dailyOrder.setInputPrice(new Float(0));
                }
                Menu menu = new Menu();
                menu.setMeat(dailyOrder.getMeat());
                menu.setShop(dailyOrder.getShop());
                RsMenus rsMenusToAdd = new RsMenus();
                rsMenusToAdd.setMenu(menu);
                menu.setPrice(dailyOrder.getInputPrice());
                menuService.addMenu(rsMenusToAdd);
                dailyOrder.setPrice(dailyOrder.getInputPrice());
            }else{
                Menu menu = rsMenus.getMenus().stream().findAny().get();
                dailyOrder.setPrice(menu.getPrice());
                if(dailyOrder.getInputPrice() == null){
                    dailyOrder.setInputPrice(new Float(0));
                }
            }
            return dailyOrder;
        }).map(dailyOrder -> {
            int resultNum = dailyOrderMapper.insert(dailyOrder);
            return resultNum;
        }).count();

        if(successNum == dailyOrders.size()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public RsAllOrders queryAllOrders(LocalDateTime startDate, LocalDateTime endDate) {
        //获取所有的订单信息
        LocalDateTime start = startDate.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = endDate.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        List<DailyOrder> dailyOrders = dailyOrderMapper.selectByExample(dailyOrderExample);
        //数据规整为以菜品为中心和以用户为中心
        //首先规整为以用户为中心
        Map<String, List<DailyOrder>> userOrdersMap = dailyOrders.stream().collect(Collectors.groupingBy(DailyOrder::getUsername));
        Iterator<Map.Entry<String, List<DailyOrder>>> userOrdersIter = userOrdersMap.entrySet().iterator();
        List<PersonOrder> personOrders = new ArrayList<>();
        while(userOrdersIter.hasNext()){
            PersonOrder personOrder = new PersonOrder();
            Map.Entry<String, List<DailyOrder>> userOrdersEntry = userOrdersIter.next();
            personOrder.setUsername(userOrdersEntry.getKey());
            personOrder.setOrders(userOrdersEntry.getValue());
            personOrder.setInputPriceSum(personOrder.getOrders().stream().map(dailyOrder -> {
                return dailyOrder.getAmount() * dailyOrder.getInputPrice();
            }).reduce((a, b) -> a + b).get());
            personOrders.add(personOrder);
        }

        //规整为以菜品为中心
        Map<String, List<DailyOrder>> meatOrdersMap = dailyOrders.stream().collect(Collectors.groupingBy(OrderUtils::getOrderNameWithShop));
        Iterator<Map.Entry<String, List<DailyOrder>>> meatOrdersIter = meatOrdersMap.entrySet().iterator();
        List<MeatOrder> meatOrders = new ArrayList<>();
        while(meatOrdersIter.hasNext()){
            MeatOrder meatOrder = new MeatOrder();
            Map.Entry<String, List<DailyOrder>> meatOrdersEntry = meatOrdersIter.next();
            meatOrder.setMeat(meatOrdersEntry.getKey());
            meatOrder.setOrders(meatOrdersEntry.getValue());
            meatOrder.setAmount(meatOrder.getOrders().stream().map(DailyOrder::getAmount).reduce((a, b) -> a + b).get());
            meatOrder.setInputPriceSum(meatOrder.getOrders().stream().map(dailyOrder -> {
                return dailyOrder.getInputPrice() * dailyOrder.getAmount();
            }).reduce((a, b) -> a + b).get());
            meatOrders.add(meatOrder);
        }

        RsAllOrders rsAllOrders = new RsAllOrders();
        rsAllOrders.setMeatOrders(meatOrders);
        rsAllOrders.setPersonOrders(personOrders);
        return rsAllOrders;
    }

    private List<DailyOrder> queryTodayOrdersForUsers(List<String> usernames){
        LocalDateTime nextDay = LocalDateTime.now().plus(Duration.ofDays(1));
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = nextDay.truncatedTo(ChronoUnit.DAYS);
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        if(!usernames.isEmpty()){
            criteria.andUsernameIn(usernames);
        }else{
            List<DailyOrder> dailyOrders = new ArrayList<>();
            return dailyOrders;
        }
        List<DailyOrder> dailyOrders = dailyOrderMapper.selectByExample(dailyOrderExample);
        return dailyOrders;
    }

    private Boolean deleteTodayOrdersForUsers(List<String> usernames){
        LocalDateTime nextDay = LocalDateTime.now().plus(Duration.ofDays(1));
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = nextDay.truncatedTo(ChronoUnit.DAYS);
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        if(!usernames.isEmpty()){
            criteria.andUsernameIn(usernames);
        }else{
            return true;
        }
        int deleteNum = dailyOrderMapper.deleteByExample(dailyOrderExample);
        logger.info("delete orders:" + deleteNum);
        return true;
    }
}
