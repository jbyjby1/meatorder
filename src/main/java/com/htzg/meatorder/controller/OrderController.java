package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.service.ChickenService;
import com.htzg.meatorder.service.EventService;
import com.htzg.meatorder.service.OrderServiceImpl;
import com.htzg.meatorder.util.DataResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.htzg.meatorder.util.CommonConstant.shanghai;

/**
 * Created by jby on 2018/12/28.
 */
@RestController
public class OrderController {

    public static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ChickenService chickenService;

    @GetMapping("/orders")
    public DataResponse getDailyOrders(String date, String shopName,String username){
        try{
            LocalDateTime day = LocalDateTime.now();
            if(StringUtils.isNotBlank(date)){
                Instant dateInstant = Instant.parse(date);
                day = LocalDateTime.ofInstant(dateInstant, shanghai);
            }
            logger.info("get daily orders date:" + day.toString());
            RsDailyOrder rsDailyOrder = orderService.getDailyOrder(day, shopName, username);
            return DataResponse.success(rsDailyOrder);
        } catch (Exception e){
            logger.error("get daily order error.", e);
            return DataResponse.failure("获取订单数据失败");
        }
    }

    @PostMapping("/orders")
    public DataResponse addDailyOrders(@RequestBody RsDailyOrder rsDailyOrder){
        try{
            if(eventService.isDailyOrderLocked(null)){
                return DataResponse.failure("本日订餐已被锁定，无法提交订单。请联系管理员。");
            }
            //堂食不允许输入0的价格
            if(rsDailyOrder.getOrders().stream().anyMatch(order -> {
                return "堂食".equals(order.getMeat())
                        && (order.getInputPrice() == null
                        || Float.valueOf(0).equals(order.getInputPrice()));
            })){
                return DataResponse.failure("堂食不允许设置价格为0");
            }
            Boolean result = orderService.addOrModifyDailyOrder(rsDailyOrder.getOrders());
            if(result){
                return DataResponse.success("增加订单成功");
            }else{
                return DataResponse.failure("增加订单失败");
            }
        } catch (Exception e){
            logger.error("get daily order error.", e);
            return DataResponse.failure("增加订单失败");
        }
    }

    @GetMapping("/all-orders")
    public DataResponse queryAllOrders(String startDate, String endDate, String shopName){
        try{
            Instant startInstant = Instant.parse(startDate);
            Instant endInstant = Instant.parse(endDate);
            LocalDateTime start = LocalDateTime.ofInstant(startInstant, shanghai);
            LocalDateTime end = LocalDateTime.ofInstant(endInstant, shanghai);
            RsAllOrders rsAllOrders = orderService.queryAllOrders(start, end, shopName);
            //对于个人进行吃鸡设置
            List<DailyChicken> dailyChickens = chickenService.queryDailyChicken();
            Set<String> luckyPersons = dailyChickens.stream().map(DailyChicken::getChickenName).collect(Collectors.toSet());
            List<PersonOrder> personOrders = rsAllOrders.getPersonOrders();
            List<PersonOrder> luckyPersonOrders = personOrders.stream().map(personOrder -> {
                boolean isLucky = luckyPersons.contains(personOrder.getUsername());
                personOrder.setLucky(isLucky);
                return personOrder;
            }).collect(Collectors.toList());
            rsAllOrders.setPersonOrders(luckyPersonOrders);
            return DataResponse.success(rsAllOrders);
        } catch (Exception e){
            logger.error("get daily order error.", e);
            return DataResponse.failure("获取订单数据失败");
        }
    }
}
