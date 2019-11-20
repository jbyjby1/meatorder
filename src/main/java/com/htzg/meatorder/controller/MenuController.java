package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.RsDailyOrder;
import com.htzg.meatorder.domain.menu.RsMenus;
import com.htzg.meatorder.service.MenuService;
import com.htzg.meatorder.util.DataResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jby on 2019/1/1.
 */
@RestController
public class MenuController {

    public static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @GetMapping("/menus")
    public DataResponse queryMenus(String meatName, String shop){
        try{
            RsMenus rsMenus;
            if(StringUtils.isNotBlank(meatName)){
                rsMenus = menuService.queryMenus(meatName, null, shop, false);
            }else{
                rsMenus = menuService.queryMenus(shop, null);
            }
            return DataResponse.success(rsMenus);
        } catch (Exception e){
            logger.error("get menus error.", e);
            return DataResponse.failure("获取菜单数据失败");
        }
    }

    @PostMapping("/menus")
    public DataResponse addMenus(@RequestBody  RsMenus rsMenus){
        try{
            menuService.addMenu(rsMenus);
            return DataResponse.success(rsMenus);
        } catch (Exception e){
            logger.error("get menus error.", e);
            return DataResponse.failure("增加菜单数据失败");
        }
    }

    @PutMapping("/menus")
    public DataResponse modifyMenus(@RequestBody  RsMenus rsMenus){
        try{
            menuService.modifyMenu(rsMenus);
            return DataResponse.success(rsMenus);
        } catch (Exception e){
            logger.error("get menus error.", e);
            return DataResponse.failure("修改菜单数据失败");
        }
    }

    @DeleteMapping("/menus")
    public DataResponse deleteMenu(String meatName){
        try{
            menuService.deleteMenu(meatName);
            return DataResponse.success("删除菜单数据成功");
        } catch (Exception e){
            logger.error("get menus error.", e);
            return DataResponse.failure("删除菜单数据失败");
        }
    }

    @PostMapping("/menus/validation")
    public DataResponse validateDailyMenus(@RequestBody RsDailyOrder rsDailyOrder){
        try{
            rsDailyOrder.setOrders(rsDailyOrder.getOrders().stream().map(order -> {
                order.setUsername(trimInput(order.getUsername()));
                order.setMeat(trimInput(order.getMeat()));
                order.setShop(trimInput(order.getShop()));
                order.setUnit(trimInput(order.getUnit()));
                return order;
            }).collect(Collectors.toList()));
            List<String> messages = menuService.validateOrder(rsDailyOrder.getOrders(), null);
            Map<String, List<String>> messageMap = new HashMap<String, List<String>>(){{
                put("messages", messages);
            }};
            return DataResponse.success(messageMap);
        } catch (Exception e){
            logger.error("validation menus error.", e);
            return DataResponse.failure("校验菜单数据失败");
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


