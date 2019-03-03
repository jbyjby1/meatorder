package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.DailyChicken;
import com.htzg.meatorder.service.ChickenService;
import com.htzg.meatorder.util.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 用于获取和抽取今天的吃鸡大佬的controller
 */
@RestController
@RequestMapping("/chickens")
public class ChickenController {

    public static Logger logger = LoggerFactory.getLogger(ChickenController.class);

    @Autowired
    ChickenService chickenService;

    @GetMapping("/daily")
    public DataResponse getDailyChicken(){
        try{
            List<DailyChicken> dailyChickens = chickenService.queryDailyChicken();
            return DataResponse.success(dailyChickens);
        }catch (Exception e){
            logger.error("query chicken error.", e);
            return DataResponse.failure("查询吃鸡选手失败");
        }
    }

    @PutMapping("/daily")
    public DataResponse generateDailyChickenNumber(){
        try{
            List<DailyChicken> result = chickenService.generateDailyChickenNumber();
            return DataResponse.success(result);
        }catch (Exception e){
            logger.error("generate chicken error.", e);
            return DataResponse.failure("生成吃鸡编号失败。");
        }
    }
}
