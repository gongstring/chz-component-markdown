package com.chz.component.markdown.bean;

import java.util.List;

/**
 * 分组
 */
public class GroupBean {

    private String name;

    private List<MenuBean> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBean> menus) {
        this.menus = menus;
    }
}
