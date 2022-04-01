package com.example.testtvapp.Model;

import java.util.Date;

public class UserNew {

    String address,device,email,last_login,name,phone,role;
    Date created_at, updated_at,subscription_date;
    boolean is_blocked, is_paid_user, login_status;
    Screen1 screen1;
    Screen1 screen2;
    Screen3 screen3;
    Screen3 screen4;
    Screen3 screen5;

    public Date getSubscription_date() {
        return subscription_date;
    }

    public void setSubscription_date(Date subscription_date) {
        this.subscription_date = subscription_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public boolean isIs_paid_user() {
        return is_paid_user;
    }

    public void setIs_paid_user(boolean is_paid_user) {
        this.is_paid_user = is_paid_user;
    }

    public boolean isLogin_status() {
        return login_status;
    }

    public void setLogin_status(boolean login_status) {
        this.login_status = login_status;
    }



    public Screen1 getScreen1() {
        return screen1;
    }

    public void setScreen1(Screen1 screen1) {
        this.screen1 = screen1;
    }

    public Screen1 getScreen2() {
        return screen2;
    }

    public void setScreen2(Screen1 screen2) {
        this.screen2 = screen2;
    }

    public Screen3 getScreen3() {
        return screen3;
    }

    public void setScreen3(Screen3 screen3) {
        this.screen3 = screen3;
    }

    public Screen3 getScreen4() {
        return screen4;
    }

    public void setScreen4(Screen3 screen4) {
        this.screen4 = screen4;
    }

    public Screen3 getScreen5() {
        return screen5;
    }

    public void setScreen5(Screen3 screen5) {
        this.screen5 = screen5;
    }
}
