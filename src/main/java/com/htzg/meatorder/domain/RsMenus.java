package com.htzg.meatorder.domain;

import java.util.List;

/**
 * Created by jby on 2019/1/1.
 */
public class RsMenus {

    private Menu menu;

    private List<Menu> menus;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
