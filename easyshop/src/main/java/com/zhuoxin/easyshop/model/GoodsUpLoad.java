package com.zhuoxin.easyshop.model;

/**
 * Created by Administrator on 2017/7/25 0025.
 * {
 "description": "诚信商家，非诚勿扰",     //商品描述
 "master": "android",                    //商品发布者
 "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
 "price": "88",                          //商品价格
 "type": "gift"                          //商品类型
 }
 */
//商品上传时的实体类
public class GoodsUpLoad {
    private String name;//商品名称
    private String price;//商品的价格
    private String description;//商品描述
    private String type;//商品类型
    private String master;//商品发布者

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "GoodsUpLoad{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", master='" + master + '\'' +
                '}';
    }
}
