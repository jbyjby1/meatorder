package com.htzg.meatorder.service;

import com.htzg.meatorder.dao.MenuMapper;
import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.Menu;
import com.htzg.meatorder.domain.MenuExample;
import com.htzg.meatorder.domain.menu.RsMenus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        criteria.andDeletedEqualTo(false);
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

    /**
     * 校验订单中的项目是否符合菜单
     * @param dailyOrders
     * @param shop 可选传入
     * @return 校验结果（文字）
     */
    @Override
    public List<String> validateOrder(List<DailyOrder> dailyOrders, String shop) {
        List<String> results = new ArrayList<>();
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        List<String> meats = dailyOrders.stream().map(DailyOrder::getMeat).collect(Collectors.toList());
        criteria.andMeatIn(meats);
        criteria.andDeletedEqualTo(false);
        if(StringUtils.isNotBlank(shop)){
            //TODO: 后续如果一个订单可以进行多个饭店的预订，则开始进行饭店筛选，现在没必要
            criteria.andShopEqualTo(shop);
        }
        List<Menu> menus =  menuMapper.selectByExample(menuExample);
        Map<String, List<Menu>> menuNameMap = menus.stream().collect(Collectors.groupingBy(Menu::getMeat));
        for (DailyOrder dailyOrder : dailyOrders){
            if(menuNameMap.containsKey(dailyOrder.getMeat())){
                //如果菜单中包含点的菜，那么校验规格和价格
                List<Menu> sameMeats = menuNameMap.get(dailyOrder.getMeat());
                Menu sameFlavorMenu = sameMeats.stream().filter(menu ->
                        menu.getFlavor().equals(dailyOrder.getFlavor())).findAny().orElse(null);
                if(sameFlavorMenu != null){
                    boolean hasSamePrice = sameFlavorMenu.getPrice().equals(dailyOrder.getInputPrice());
                    if(!hasSamePrice){
                        //价格不满足
                        results.add(String.format("菜品【%s-%s】在菜单中的价格为%s，而输入的价格为%s，请确认是否是选择的菜品。",
                                dailyOrder.getMeat(), dailyOrder.getFlavor(), sameFlavorMenu.getPrice(), dailyOrder.getInputPrice()));

                    }
                    continue;
                }
                //规格不满足
            }
            //菜品没找到
            results.add(String.format("菜品【%s-%s】在菜单中没有找到，请确认是否是选择的菜品。",
                    dailyOrder.getMeat(), dailyOrder.getFlavor()));
        }
        return results;
    }


}
