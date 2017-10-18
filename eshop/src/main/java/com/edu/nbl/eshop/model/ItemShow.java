package com.edu.nbl.eshop.model;

/**
 * Created by Administrator on 2017/7/21 0021.
 * 用于展示个人信息页面的实体类
 */

public class ItemShow {
    private String  item_title;//单行布局的名称
    private String item_content;//单行布局的内容

    public ItemShow() {
    }

    public ItemShow(String item_title, String item_content) {
        this.item_title = item_title;
        this.item_content = item_content;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_content() {
        return item_content;
    }

    public void setItem_content(String item_content) {
        this.item_content = item_content;
    }

    @Override
    public String toString() {
        return "ItemShow{" +
                "item_title='" + item_title + '\'' +
                ", item_content='" + item_content + '\'' +
                '}';
    }
}
