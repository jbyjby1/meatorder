package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.RsMenus;

/**
 * Created by jby on 2019/1/1.
 */
public interface MenuService {


    public RsMenus queryMenus();

    public RsMenus queryMenus(String meatName, boolean strict);

    public Boolean deleteMenu(String meatName);

    public Boolean addMenu(RsMenus rsMenus);

    public Boolean modifyMenu(RsMenus rsMenus);

}
