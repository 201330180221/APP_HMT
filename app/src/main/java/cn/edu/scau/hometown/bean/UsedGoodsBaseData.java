package cn.edu.scau.hometown.bean;

import java.io.Serializable;

/**
 * 二手商品
 * Created by acer on 2015/9/20.
 */
public class UsedGoodsBaseData implements Serializable{
    private Integer secondgoods_id;
    private Double secondgoods_price;
    private String secondgoods_name;
    private Integer secondgoods_views;
    private String secondgoods_bewrite;
    private String secondgoods_postdate;
    private String secondgoods_pastdate;
    private String secondgoods_picture;

    public void setSecondgoods_id(Integer secondgoods_id) {
        this.secondgoods_id = secondgoods_id;
    }

    public void setSecondgoods_price(Double secondgoods_price) {
        this.secondgoods_price = secondgoods_price;
    }

    public void setSecondgoods_name(String secondgoods_name) {
        this.secondgoods_name = secondgoods_name;
    }

    public void setSecondgoods_views(Integer secondgoods_views) {
        this.secondgoods_views = secondgoods_views;
    }

    public void setSecondgoods_bewrite(String secondgoods_bewrite) {
        this.secondgoods_bewrite = secondgoods_bewrite;
    }

    public void setSecondgoods_postdate(String secondgoods_postdate) {
        this.secondgoods_postdate = secondgoods_postdate;
    }

    public void setSecondgoods_pastdate(String secondgoods_pastdate) {
        this.secondgoods_pastdate = secondgoods_pastdate;
    }

    public void setSecondgoods_picture(String secondgoods_picture) {
        this.secondgoods_picture = secondgoods_picture;
    }

    public Integer getSecondgoods_id() {

        return secondgoods_id;
    }

    public Double getSecondgoods_price() {
        return secondgoods_price;
    }

    public String getSecondgoods_name() {
        return secondgoods_name;
    }

    public Integer getSecondgoods_views() {
        return secondgoods_views;
    }

    public String getSecondgoods_bewrite() {
        return secondgoods_bewrite;
    }

    public String getSecondgoods_postdate() {
        return secondgoods_postdate;
    }

    public String getSecondgoods_pastdate() {
        return secondgoods_pastdate;
    }

    public String getSecondgoods_picture() {
        return secondgoods_picture;
    }

    public UsedGoodsBaseData() {

    }



    public UsedGoodsBaseData(Integer secondgoods_id, Double secondgoods_price,
                             String secondgoods_name, Integer secondgoods_views,
                             String secondgoods_bewrite, String secondgoods_postdate,
                             String secondgoods_pastdate, String secondgoods_picture) {
        this.secondgoods_id = secondgoods_id;
        this.secondgoods_price = secondgoods_price;
        this.secondgoods_name = secondgoods_name;
        this.secondgoods_views = secondgoods_views;
        this.secondgoods_bewrite = secondgoods_bewrite;
        this.secondgoods_postdate = secondgoods_postdate;
        this.secondgoods_pastdate = secondgoods_pastdate;
        this.secondgoods_picture = secondgoods_picture;
    }

    @Override
    public String toString() {
        return "UsedGoodsBaseData{" +
                "secondgoods_id=" + secondgoods_id +
                ", secondgoods_price=" + secondgoods_price +
                ", secondgoods_name='" + secondgoods_name + '\'' +
                ", secondgoods_views=" + secondgoods_views +
                ", secondgoods_bewrite='" + secondgoods_bewrite + '\'' +
                ", secondgoods_postdate='" + secondgoods_postdate + '\'' +
                ", secondgoods_pastdate='" + secondgoods_pastdate + '\'' +
                ", secondgoods_picture='" + secondgoods_picture + '\'' +
                '}';
    }
}
