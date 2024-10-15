package com.example.scet;

import java.util.HashMap;

public class BookModel {
    public String gts,pack_date,pack_name,pack_pic,pack_price,pack_time,total_amount,username;

    public BookModel(String gts, String pack_date, String pack_name, String pack_pic, String pack_price, String pack_time, String total_amount, String username) {
        this.gts = gts;
        this.pack_date = pack_date;
        this.pack_name = pack_name;
        this.pack_pic = pack_pic;
        this.pack_price = pack_price;
        this.pack_time = pack_time;
        this.total_amount = total_amount;
        this.username = username;
    }

    public BookModel() {

    }

    public String getGts() {
        return gts;
    }

    public void setGts(String gts) {
        this.gts = gts;
    }

    public String getPack_date() {
        return pack_date;
    }

    public void setPack_date(String pack_date) {
        this.pack_date = pack_date;
    }

    public String getPack_name() {
        return pack_name;
    }

    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }

    public String getPack_pic() {
        return pack_pic;
    }

    public void setPack_pic(String pack_pic) {
        this.pack_pic = pack_pic;
    }

    public String getPack_price() {
        return pack_price;
    }

    public void setPack_price(String pack_price) {
        this.pack_price = pack_price;
    }

    public String getPack_time() {
        return pack_time;
    }

    public void setPack_time(String pack_time) {
        this.pack_time = pack_time;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public HashMap<String,Object> converttomap(BookModel model){
        HashMap<String,Object> map=new HashMap<>();

        map.put("gst",model.getGts());
        map.put("pack_date",model.getPack_date());
        map.put("pack_time",model.getPack_time());
        map.put("pack_name",model.getPack_name());
        map.put("pack_pic",model.getPack_pic());
        map.put("pack_price",model.getPack_price());
        map.put("total_amount",model.getTotal_amount());
        map.put("username",model.getUsername());

        return map;
    }
}
