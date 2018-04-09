package com.chz.component.markdown.bean;

import java.util.List;

public class MenuBean {

    private String id;

    private String name;

    /**
     * 是否使用绝对路径
     */
    private Boolean absolute = false;

    /**
     * 文档路径
     */
    private String path;

    /**
     * 子菜单
     */
    private List<MenuBean> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAbsolute() {
        return absolute;
    }

    public void setAbsolute(Boolean absolute) {
        this.absolute = absolute;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<MenuBean> getChildren() {
        return children;
    }

    public void setChildren(List<MenuBean> children) {
        this.children = children;
    }
}
