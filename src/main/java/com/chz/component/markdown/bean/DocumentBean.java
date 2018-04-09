package com.chz.component.markdown.bean;

import java.util.List;

public class DocumentBean {

    private String name;

    private List<GroupBean> groupBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupBean> getGroupBeans() {
        return groupBeans;
    }

    public void setGroupBeans(List<GroupBean> groupBeans) {
        this.groupBeans = groupBeans;
    }
}
