package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.MenuMapper;
import com.htzg.meatorder.domain.Menu;
import com.htzg.meatorder.domain.MenuExample;
import com.htzg.meatorder.domain.RsMenus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Created by jby on 2019/1/1.
 */
@Service
public class MemuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public RsMenus queryMenus() {
        List<Menu> menus =  menuMapper.selectByExample(null);
        RsMenus rsMenus = new RsMenus();
        rsMenus.setMenus(menus);
        return rsMenus;
    }

    @Override
    public RsMenus queryMenus(String meatName, boolean strict) {
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        if(strict){
            criteria.andMeatEqualTo(meatName);
        }else{
            criteria.andMeatLike("%" + meatName + "%");
        }

        List<Menu> menus =  menuMapper.selectByExample(menuExample);
        RsMenus rsMenus = new RsMenus();
        rsMenus.setMenus(menus);
        return rsMenus;
    }

    public Boolean deleteMenu(String meatName){
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andMeatEqualTo(meatName);
        int k =  menuMapper.deleteByExample(menuExample);
        return true;
    }

    public Boolean addMenu(RsMenus rsMenus){
        if(rsMenus.getMenu() != null){
            Menu rsMenu = rsMenus.getMenu();
            rsMenu.setCreateTime(Instant.now());
            rsMenu.setUpdateTime(Instant.now());
            if(StringUtils.isBlank(rsMenu.getUnit())){
                rsMenu.setUnit("份");
            }
            if(StringUtils.isBlank(rsMenu.getFlavor())){
                rsMenu.setFlavor("标准");
            }
            if(StringUtils.isBlank(rsMenu.getShop())){
                rsMenu.setShop("好嫂子（苹果园店）");
            }
            menuMapper.insert(rsMenus.getMenu());
        }
        if(rsMenus.getMenus() != null){
            rsMenus.getMenus().stream().forEach(rsMenu -> {
                rsMenu.setCreateTime(Instant.now());
                rsMenu.setUpdateTime(Instant.now());
                if(StringUtils.isBlank(rsMenu.getUnit())){
                    rsMenu.setUnit("份");
                }
                if(StringUtils.isBlank(rsMenu.getFlavor())){
                    rsMenu.setFlavor("标准");
                }
                if(StringUtils.isBlank(rsMenu.getShop())){
                    rsMenu.setShop("好嫂子（苹果园店）");
                }
                menuMapper.insert(rsMenu);
            });
        }
        return true;
    }

    @Override
    public Boolean modifyMenu(RsMenus rsMenus) {
        if(rsMenus.getMenu() != null){
            Menu rsMenu = rsMenus.getMenu();
            rsMenu.setCreateTime(Instant.now());
            rsMenu.setUpdateTime(Instant.now());
            if(StringUtils.isBlank(rsMenu.getUnit())){
                rsMenu.setUnit("份");
            }
            if(StringUtils.isBlank(rsMenu.getFlavor())){
                rsMenu.setFlavor("标准");
            }
            if(StringUtils.isBlank(rsMenu.getShop())){
                rsMenu.setShop("好嫂子（苹果园店）");
            }
            this.deleteMenu(rsMenu.getMeat());
            menuMapper.insert(rsMenus.getMenu());
        }
        if(rsMenus.getMenus() != null){
            rsMenus.getMenus().stream().forEach(rsMenu -> {
                rsMenu.setCreateTime(Instant.now());
                rsMenu.setUpdateTime(Instant.now());
                if(StringUtils.isBlank(rsMenu.getUnit())){
                    rsMenu.setUnit("份");
                }
                if(StringUtils.isBlank(rsMenu.getFlavor())){
                    rsMenu.setFlavor("标准");
                }
                if(StringUtils.isBlank(rsMenu.getShop())){
                    rsMenu.setShop("好嫂子（苹果园店）");
                }
                this.deleteMenu(rsMenu.getMeat());
                menuMapper.insert(rsMenu);
            });
        }
        return true;
    }


}