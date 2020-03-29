package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.FastFoodItem;
import com.htzg.meatorder.domain.FastFoodItemExtended;
import com.htzg.meatorder.domain.menu.RsFastFoods;
import com.htzg.meatorder.domain.menu.RsUpdateFastFood;
import com.htzg.meatorder.service.FastFoodService;
import com.htzg.meatorder.util.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FastFoodController {

    public static Logger logger = LoggerFactory.getLogger(FastFoodController.class);

    @Autowired
    private FastFoodService fastFoodService;

    @GetMapping("/fastfoods")
    public DataResponse getFastFoods(){
        try{
            List<FastFoodItemExtended> fastFoods = fastFoodService.getAvailableFastFoodMenus();
            RsFastFoods rsFastFoods = new RsFastFoods();
            rsFastFoods.setFastFoodItems(fastFoods);
            return DataResponse.success(rsFastFoods);
        }catch (Exception e){
            logger.error("get fast foods error.", e);
            return DataResponse.failure("获取快餐数据失败");
        }
    }

    @PostMapping("/fastfoods")
    public DataResponse updateAllAvailableFoods(@RequestBody RsUpdateFastFood rsUpdateFastFood){
        try{
            List<FastFoodItem> fastFoodItems = rsUpdateFastFood.toItems();
            fastFoodService.updateAllFastFoods(fastFoodItems);
            return DataResponse.success("更新快餐数据成功");
        } catch (Exception e){
            logger.error("update fast foods error.", e);
            return DataResponse.failure("更新快餐数据失败");
        }
    }

}
