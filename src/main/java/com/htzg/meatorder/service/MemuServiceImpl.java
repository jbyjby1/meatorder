package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.MenuMapper;
import com.htzg.meatorder.domain.Menu;
import com.htzg.meatorder.domain.MenuExample;
import com.htzg.meatorder.domain.RsMenus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static com.htzg.meatorder.util.CommonConstant.DEFAULT_RESTAURANT;

/**
 * Created by jby on 2019/1/1.
 */
@Service
public class MemuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public RsMenus queryMenus(String shop, String flavor) {
        return queryMenus(null, flavor, shop, false);
    }

    /**
     * 查找菜品
     * @param meatName 菜品名称
     * @param strict 是否模糊搜索
     * @return
     */
    @Override
    public RsMenus queryMenus(String meatName, String flavor, String shop,  boolean strict) {
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andShopEqualTo(shop);
        if(StringUtils.isNotBlank(meatName)){
            if(strict){
                criteria.andMeatEqualTo(meatName);
            }else{
                criteria.andMeatLike("%" + meatName + "%");
            }
        }
        if(StringUtils.isNotBlank(flavor)){
            criteria.andFlavorEqualTo(flavor);
        }

        List<Menu> menus =  menuMapper.selectByExample(menuExample);
        RsMenus rsMenus = new RsMenus();
        rsMenus.setMenus(menus);
        return rsMenus;
    }

    @Override
    public Boolean deleteMenu(String meatName){
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andMeatEqualTo(meatName);
        int k =  menuMapper.deleteByExample(menuExample);
        return true;
    }

    @Override
    public Boolean addMenu(RsMenus rsMenus){
        if(rsMenus.getMenu() != null){
            Menu rsMenu = rsMenus.getMenu();
            rsMenu.setCreateTime(LocalDateTime.now());
            rsMenu.setUpdateTime(LocalDateTime.now());
            if(StringUtils.isBlank(rsMenu.getUnit())){
                rsMenu.setUnit("份");
            }
            if(StringUtils.isBlank(rsMenu.getFlavor())){
                rsMenu.setFlavor("标准");
            }
            if(StringUtils.isBlank(rsMenu.getShop())){
                rsMenu.setShop(DEFAULT_RESTAURANT);
            }
            menuMapper.insert(rsMenus.getMenu());
        }
        return true;
    }

    @Override
    public Boolean modifyMenu(RsMenus rsMenus) {
        if(rsMenus.getMenu() != null){
            Menu rsMenu = rsMenus.getMenu();
            rsMenu.setCreateTime(LocalDateTime.now());
            rsMenu.setUpdateTime(LocalDateTime.now());
            if(StringUtils.isBlank(rsMenu.getUnit())){
                rsMenu.setUnit("份");
            }
            if(StringUtils.isBlank(rsMenu.getFlavor())){
                rsMenu.setFlavor("标准");
            }
            if(StringUtils.isBlank(rsMenu.getShop())){
                rsMenu.setShop(DEFAULT_RESTAURANT);
            }
            this.deleteMenu(rsMenu.getMeat());
            menuMapper.insert(rsMenus.getMenu());
        }
        return true;
    }


}
