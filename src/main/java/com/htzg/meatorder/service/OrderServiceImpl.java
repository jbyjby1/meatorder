package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.DailyOrderMapper;
import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.util.JsonUtils;
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
import java.util.*;
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
    public RsDailyOrder getDailyOrder(LocalDateTime day, String shopName, String username) {
        LocalDateTime nextDay = day.plus(Duration.ofDays(1));
        LocalDateTime start = day.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = nextDay.truncatedTo(ChronoUnit.DAYS);
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        if(StringUtils.isNotBlank(shopName)){
            criteria.andShopEqualTo(shopName);
        }
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
            dailyOrder.setStatus(OrderStatus.CREATED);
            dailyOrder.setUnit("份");
            return dailyOrder;
        }).map(dailyOrder -> {
            //顺便增加新菜单
            RsMenus rsMenus = menuService.queryMenus(dailyOrder.getMeat(), dailyOrder.getFlavor(), dailyOrder.getShop(), true);

            if(CollectionUtils.isEmpty(rsMenus.getMenus())){
                //如果没有查到这种菜品，增加菜单
                if(dailyOrder.getInputPrice() == null){
                    dailyOrder.setInputPrice(new Float(0));
                }
                addMenuByOrder(dailyOrder);
                dailyOrder.setPrice(dailyOrder.getInputPrice());
            }else if(!"堂食".equals(dailyOrder.getMeat())){
                Menu menu = null;
                if(StringUtils.isBlank(dailyOrder.getFlavor())){
                    //如果规格为空，则匹配价格，如果匹配不上，则按照“标准”处理，增加菜单
                    Optional<Menu> menuOptional = rsMenus.getMenus().stream().filter(menu1 -> {
                        return menu1.getPrice().equals(dailyOrder.getInputPrice());
                    }).findAny();
                    if(menuOptional.isPresent()){
                        //有规格，设置规格
                        menu = menuOptional.get();
                        dailyOrder.setFlavor(menu.getFlavor());
                    }else{
                        //没有规格，按照标准处理，增加菜单
                        dailyOrder.setFlavor("标准");
                        menu = addMenuByOrder(dailyOrder);
                    }
                }else{
                    menu = rsMenus.getMenus().stream().findAny().get();
                }
                dailyOrder.setPrice(menu.getPrice());
                if(dailyOrder.getInputPrice() == null){
                    dailyOrder.setInputPrice(new Float(0));
                }
            }else{
                //堂食，需要额外查看是否有价格一样的
                if(!rsMenus.getMenus().stream().anyMatch(menu -> {
                    return menu.getPrice().equals(dailyOrder.getInputPrice());
                })){
                    //价格都不一样，增加菜单
                    Menu menu = new Menu();
                    menu.setMeat(dailyOrder.getMeat());
                    menu.setShop(dailyOrder.getShop());
                    RsMenus rsMenusToAdd = new RsMenus();
                    rsMenusToAdd.setMenu(menu);
                    menu.setPrice(dailyOrder.getInputPrice());
                    menuService.addMenu(rsMenusToAdd);
                }
                dailyOrder.setPrice(dailyOrder.getInputPrice());
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

    /**
     * 根据订单创建菜单
     * @param dailyOrder
     */
    private Menu addMenuByOrder(DailyOrder dailyOrder){
        Menu menu = new Menu();
        menu.setMeat(dailyOrder.getMeat());
        menu.setShop(dailyOrder.getShop());
        menu.setFlavor(dailyOrder.getFlavor());
        RsMenus rsMenusToAdd = new RsMenus();
        rsMenusToAdd.setMenu(menu);
        menu.setPrice(dailyOrder.getInputPrice());
        menuService.addMenu(rsMenusToAdd);
        return menu;
    }

    @Override
    public boolean modifyDailyOrderStatus(int dailyOrderId, OrderStatus status) {
        DailyOrder dailyOrder = dailyOrderMapper.selectByPrimaryKey(dailyOrderId);
        if(dailyOrder == null){
            logger.error("[Modify daily order status] not found order " + dailyOrderId);
            return false;
        }
        dailyOrder.setUpdateTime(LocalDateTime.now());
        dailyOrder.setStatus(status);
        dailyOrderMapper.updateByPrimaryKey(dailyOrder);
        return true;
    }

    @Override
    public RsAllOrders queryAllOrders(LocalDateTime startDate, LocalDateTime endDate, String shopName, List<SupportOrderStatus> statusList, boolean onlySupper) {
        //获取所有的订单信息
        LocalDateTime start = startDate.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = endDate.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
        if(onlySupper){
            //如果有条件：只有晚餐，则只查询最后一天14:00之后点的餐
            start = end.minus(Duration.ofHours(10));
        }
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        if(StringUtils.isNotBlank(shopName)){
            criteria.andShopEqualTo(shopName);
        }
        if(!CollectionUtils.isEmpty(statusList)){
            criteria.andStatusIn(statusList.stream()
                    .map(SupportOrderStatus::getOrderStatus).collect(Collectors.toList()));
        }
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
            personOrder.setOrders(
                    userOrdersEntry.getValue().stream().map(OrderUtils::dailyOrderToExtended).collect(Collectors.toList()));
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
            meatOrder.setOrders(
                    meatOrdersEntry.getValue().stream().map(OrderUtils::dailyOrderToExtended).collect(Collectors.toList()));
            meatOrder.setAmount(meatOrder.getOrders().stream().map(DailyOrder::getAmount).reduce((a, b) -> a + b).get());
            meatOrder.setUnitPrice(meatOrder.getOrders().stream().findAny().get().getPrice());
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

    @Override
    public List<String> queryDailyOrderPersons() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plus(Duration.ofDays(1)).truncatedTo(ChronoUnit.DAYS);
        DailyOrderExample dailyOrderExample = new DailyOrderExample();
        DailyOrderExample.Criteria criteria = dailyOrderExample.createCriteria();
        criteria.andCreateTimeBetween(start, end);
        List<DailyOrder> dailyOrders = dailyOrderMapper.selectByExample(dailyOrderExample);
        return dailyOrders.stream().map(DailyOrder::getUsername).distinct().collect(Collectors.toList());
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
