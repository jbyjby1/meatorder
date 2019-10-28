package com.htzg.meatorder.domain.modifier;

import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifierExtended extends Modifier {

    public static Logger logger = LoggerFactory.getLogger(ModifierExtended.class);

    public ModifierParameters getRealModifierParameters(){
        String parameters = getModifierParameters();
        if(StringUtils.isNotBlank(parameters)){
            try{
                return JsonUtils.fromJson(parameters, ModifierParameters.class);
            } catch (Exception e){
                logger.error("convert modifier error.", e);
                return null;
            }
        }else{
            logger.warn("modifier {} has no parameter.");
            return null;
        }
    }

    @Override
    public int hashCode() {
        try{
            return JsonUtils.toJson(this).hashCode();
        } catch (Exception e){
            logger.error("count hash of modifier error.", e);
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        try{
            return JsonUtils.toJson(this).equals(JsonUtils.toJson(obj));
        } catch (Exception e){
            logger.error("count hash of modifier error.", e);
        }
        return super.equals(obj);
    }
}
