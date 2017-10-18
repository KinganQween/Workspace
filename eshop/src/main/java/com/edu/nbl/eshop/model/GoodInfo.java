package com.edu.nbl.eshop.model;

/**
 * Created by Administrator on 2017/7/17 0017.
 *  "price": "66",        //商品价格
 "name": "单车",      //商品名称
 "description": "......",  //商品描述
 "page": "/images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG", //商品的第一张图片
 "type": "other",      //商品类型
 "uuid": "5606FF8EF60146A48F1FCDC34144907D",  //商品表中主键
 "master": "android"  //发布者
 * 商品详情
 */
public class GoodInfo {
    private String price;//商品价格
    private String name;//商品名称
    private String description;//商品描述
    private String page;//商品图片的URL
    private String type;//商品类型
    private String uuid;//商品表中主键
    private String master;//商品的发布者

    public GoodInfo() {
    }

    public GoodInfo(String price, String name, String description, String page, String type, String uuid, String master) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.page = page;
        this.type = type;
        this.uuid = uuid;
        this.master = master;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "GoodInfo{" +
                "price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", page='" + page + '\'' +
                ", type='" + type + '\'' +
                ", uuid='" + uuid + '\'' +
                ", master='" + master + '\'' +
                '}';
    }
}
