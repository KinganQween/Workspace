package com.zhuoxin.easyshop.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class GoodsDetail {
    //名称
    private String name;
    //类型
    private String type;
    //价格
    private String price;
    //商品描述
    private String description;
    //发布者
    private String master;
    //商品图片uri
    private List<ImageUri> pages;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public List<ImageUri> getPages() {
        return pages;
    }

    public void setPages(List<ImageUri> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "GoodsDetail{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", master='" + master + '\'' +
                ", pages=" + pages +
                '}';
    }

    public class ImageUri {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @Override
        public String toString() {
            return "ImageUri{" +
                    "uri='" + uri + '\'' +
                    '}';
        }
    }
}
