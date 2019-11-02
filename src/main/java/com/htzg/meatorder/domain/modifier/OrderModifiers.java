package com.htzg.meatorder.domain.modifier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.util.JsonUtils;
import com.htzg.meatorder.util.ModifierUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.print.attribute.IntegerSyntax;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderModifiers {

    public static Logger logger = LoggerFactory.getLogger(OrderModifiers.class);

    private String displayTime;

    private List<Modifier> allModifiers;

    @JsonIgnore
    private Map<ModifierExtended, Long> countedModifiersMap;

    public Map<ModifierExtended, Long> getCountedModifiersMap(){
        if(this.countedModifiersMap != null){
            return this.countedModifiersMap;
        }
        if(CollectionUtils.isEmpty(allModifiers)){
            return new HashMap<>();
        }
        Map<ModifierExtended, Long> modifierCountMap = allModifiers.stream().map(modifier -> {
            try{
                return JsonUtils.fromJson(JsonUtils.toJson(modifier), ModifierExtended.class);
            } catch (Exception e){
                logger.error("convert modifier error.", e);
                return null;
            }
        }).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return modifierCountMap;
    }

    public List<RsCountedModifier> getCountedModifiers(){
        Map<ModifierExtended, Long> countedModifiersMap = this.getCountedModifiersMap();
        return ModifierUtils.modifiersMap2RSModifiers(countedModifiersMap);
    }

    public void setCountedModifiersMap(Map<ModifierExtended, Long> countedModifiersMap) {
        this.countedModifiersMap = countedModifiersMap;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public List<Modifier> getAllModifiers() {
        return allModifiers;
    }

    public void setAllModifiers(List<Modifier> allModifiers) {
        this.allModifiers = allModifiers;
    }
}
