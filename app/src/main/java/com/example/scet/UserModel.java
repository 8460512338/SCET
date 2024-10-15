package com.example.scet;

import java.util.HashMap;

public class UserModel {

    public String email,password,phone,username,fullname;

    public UserModel(String email, String password, String phone, String username, String fullname) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.fullname = fullname;
    }

    public UserModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public static HashMap<String, Object> convertomap(UserModel model){
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", model.getEmail());
        map.put("password", model.getPassword());
        map.put("fullname", model.getFullname());
        map.put("phone", model.getPhone());
        map.put("username", model.getUsername());
        return map;
    }
}
