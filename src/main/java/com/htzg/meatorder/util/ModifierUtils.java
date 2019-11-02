package com.htzg.meatorder.util;

import com.htzg.meatorder.domain.modifier.ModifierExtended;
import com.htzg.meatorder.domain.modifier.RsCountedModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModifierUtils {

    public static List<RsCountedModifier> modifiersMap2RSModifiers(Map<ModifierExtended, Long> countedModifiersMap){
        List<RsCountedModifier> result = new ArrayList<>();
        for (Map.Entry<ModifierExtended, Long> entry : countedModifiersMap.entrySet()){
            RsCountedModifier rsCountedModifier = new RsCountedModifier();
            rsCountedModifier.setId(entry.getKey().getId());
            rsCountedModifier.setCount(entry.getValue());
            rsCountedModifier.setDisplayName(entry.getKey().getDisplayName());
            rsCountedModifier.setModifierValue(entry.getKey().getModifierValue());
            result.add(rsCountedModifier);
        }
        return result;
    }

}
