package com.htzg.meatorder.service.modifier;

import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.modifier.ModifierExtended;

import java.util.List;

public interface ModifierService {

    public List<Modifier> queryAllModifiers();

    public boolean addModifier(Modifier modifier);

    public RsAllOrders countByModifier(RsAllOrders rsAllOrders);

}
