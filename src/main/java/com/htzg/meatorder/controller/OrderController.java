package com.htzg.meatorder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.htzg.meatorder.domain.*;
import com.htzg.meatorder.domain.modifier.OrderModifiers;
import com.htzg.meatorder.service.ChickenService;
import com.htzg.meatorder.service.EventService;
import com.htzg.meatorder.service.OrderServiceImpl;
import com.htzg.meatorder.util.DataResponse;
import com.htzg.meatorder.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public DataResponse getDailyOrders(String date, String shopName,String username,
                                       @RequestParam(defaultValue = "false") boolean splitSupper){
        try{
            LocalDateTime day = LocalDateTime.now();
            if(StringUtils.isNotBlank(date)){
                Instant dateInstant = Instant.parse(date);
                day = LocalDateTime.ofInstant(dateInstant, shanghai);
            }
            logger.info("get daily orders date:" + day.toString());
            RsDailyOrder rsDailyOrder = orderService.getDailyOrder(day, shopName, username, splitSupper);
            return DataResponse.success(rsDailyOrder);
        } catch (Exception e){
            logger.error("get daily order error.", e);
            return DataResponse.failure("获取订单数据失败");
        }
    }

    @PostMapping("/orders")
    public DataResponse addDailyOrders(@RequestBody RsDailyOrder rsDailyOrder,
                                       @RequestParam(defaultValue = "false") boolean splitSupper){
        try{
            if(eventService.isDailyOrderLocked(null)){
                return DataResponse.failure("本日订餐已被锁定，无法提交订单。请联系管理员。");
            }
            //对于输入进行trim处理
            rsDailyOrder.setOrders(rsDailyOrder.getOrders().stream().map(order -> {
                order.setUsername(trimInput(order.getUsername()));
                order.setMeat(trimInput(order.getMeat()));
                order.setShop(trimInput(order.getShop()));
                order.setUnit(trimInput(order.getUnit()));
                order.setRemark(trimInput(order.getRemark()));
                return order;
            }).collect(Collectors.toList()));

            //堂食不允许输入0的价格
            if(rsDailyOrder.getOrders().stream().anyMatch(order -> {
                return "堂食".equals(order.getMeat())
                        && (order.getInputPrice() == null
                        || Float.valueOf(0).equals(order.getInputPrice()));
            })){
                return DataResponse.failure("堂食不允许设置价格为0");
            }
            Boolean result = orderService.addOrModifyDailyOrder(rsDailyOrder.getOrders(), splitSupper);
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
    public DataResponse queryAllOrders(String startDate, String endDate, String shopName, String statusListStr, @RequestParam(defaultValue="false") boolean onlySupper){
        try{
            Instant startInstant = Instant.parse(startDate);
            Instant endInstant = Instant.parse(endDate);
            LocalDateTime start = LocalDateTime.ofInstant(startInstant, shanghai);
            LocalDateTime end = LocalDateTime.ofInstant(endInstant, shanghai);
            List<SupportOrderStatus> statusList = null;
            if(StringUtils.isNotBlank(statusListStr)){
                statusList = JsonUtils.fromJson(statusListStr, new TypeReference<List<SupportOrderStatus>>() {
                });
            }
            RsAllOrders rsAllOrders = orderService.queryAllOrders(start, end, shopName, statusList, onlySupper);
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

    @GetMapping("/support/order-status")
    public DataResponse getSupportedOrderStatus(){
        List<SupportOrderStatus> supported = Stream.of(OrderStatus.values())
                .map(SupportOrderStatus::new).collect(Collectors.toList());
        RsSupportedOrderStatus rsSupportedOrderStatus = new RsSupportedOrderStatus();
        rsSupportedOrderStatus.setSupportedOrderStatusList(supported);
        return DataResponse.success(rsSupportedOrderStatus);
    }

    @PutMapping("/orders/{orderId}/status/{orderStatus}")
    public DataResponse putOrderStatus(@PathVariable String orderId, @PathVariable String orderStatus){
        try {
            int orderIdNum = Integer.valueOf(orderId);
            OrderStatus status = OrderStatus.valueOf(orderStatus);
            boolean result = orderService.modifyDailyOrderStatus(orderIdNum, status);
            if(!result){
                return DataResponse.failure("修改订单状态失败");
            }else{
                return DataResponse.success("修改订单状态成功");
            }
        } catch (Exception e){
            logger.error("[Change order status] error.", e);
            return DataResponse.failure("修改订单状态失败");
        }
    }

    @PostMapping("/orders/modifiers")
    public DataResponse queryDailyOrderModifiers(@RequestBody RsDailyOrder rsDailyOrder){
        try{
            //对于输入进行trim处理
            rsDailyOrder.setOrders(rsDailyOrder.getOrders().stream().map(order -> {
                order.setUsername(trimInput(order.getUsername()));
                order.setMeat(trimInput(order.getMeat()));
                order.setShop(trimInput(order.getShop()));
                order.setUnit(trimInput(order.getUnit()));
                return order;
            }).collect(Collectors.toList()));
            OrderModifiers result = orderService.getDailyOrderModifiers(rsDailyOrder.getOrders());
            return DataResponse.success(result);
        } catch (Exception e){
            logger.error("get daily order modifiers error.", e);
            return DataResponse.failure("查询订单修正数据失败");
        }
    }


    private String trimInput(String input){
        if(StringUtils.isNotBlank(input)){
            return input.trim();
        }else{
            return input;
        }
    }
}
