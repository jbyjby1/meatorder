package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.domain.menu.MeatType;
import com.htzg.meatorder.domain.modifier.ModifierParameters;
import com.htzg.meatorder.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ModifierServiceImplTest {

    @Test
    void testModifierParameters(TestReporter testReporter) throws Exception{
        ModifierParameters modifierParameters = new ModifierParameters();
        Map<String, List<MeatType>> meatTypeConditions = new HashMap<>();
        List<MeatType> meatTypes = new ArrayList<MeatType>(){{
            add(MeatType.RICE_NOODLE);
            add(MeatType.NOODLE);
        }};
        List<MeatType> meatTypes2 = new ArrayList<MeatType>(){{
            add(MeatType.CHINESE_HAMBURGER);
        }};
        List<MeatType> meatTypes4 = Arrays.stream(MeatType.values()).filter(meatType -> {
            //盖浇饭、炒菜、面食、米粉都可以
            return MeatType.PILAFF.equals(meatType)
                    || MeatType.FRY.equals(meatType)
                    || MeatType.NOODLE.equals(meatType)
                    || MeatType.RICE_NOODLE.equals(meatType);
        }).collect(Collectors.toList());
        List<MeatType> meatTypes3 = new ArrayList<MeatType>(){{
            add(MeatType.DRINK);
        }};
        List<MeatType> meatTypes5 = new ArrayList<MeatType>(){{
            add(MeatType.PICKLE);
        }};
        meatTypeConditions.put("first", meatTypes3);
        meatTypeConditions.put("second", meatTypes3);
        //meatTypeConditions.put("third", meatTypes3);
        modifierParameters.setMeatTypeConditions(meatTypeConditions);
        testReporter.publishEntry(JsonUtils.toJson(modifierParameters));
        String a = "{\"meatTypeConditions\":{\"first\":[\"DRINK\"],\"second\":[\"DRINK\"]}}";
    }

}