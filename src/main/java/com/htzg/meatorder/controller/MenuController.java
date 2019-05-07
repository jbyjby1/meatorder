package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.RsMenus;
import com.htzg.meatorder.service.MenuService;
import com.htzg.meatorder.util.DataResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.Instant;

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

}


