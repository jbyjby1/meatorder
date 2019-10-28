package com.htzg.meatorder.controller;

import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.service.modifier.ModifierService;
import com.htzg.meatorder.util.DataResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class ModifierController {

    public static Logger logger = LoggerFactory.getLogger(ModifierController.class);

    @Autowired
    private ModifierService modifierService;

    @GetMapping("/modifiers")
    public DataResponse queryMenus(){
        try{
            List<Modifier> modifiers = modifierService.queryAllModifiers();
            return DataResponse.success(modifiers);
        } catch (Exception e){
            logger.error("get modifiers error.", e);
            return DataResponse.failure("获取修正器数据失败");
        }
    }

    //TODO: 不完善的处理
    @PostMapping("/modifiers")
    public DataResponse addModifiers(@RequestBody Modifier modifier){
        try{
            modifierService.addModifier(modifier);
            Map<String, Modifier> response = new HashMap<>();
            response.put("modifier", modifier);
            return DataResponse.success(response);
        } catch (Exception e){
            logger.error("add modifier error.", e);
            return DataResponse.failure("增加修正器数据失败");
        }
    }

}
