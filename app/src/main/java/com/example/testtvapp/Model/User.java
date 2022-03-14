package com.example.testtvapp.Model;

import java.util.ArrayList;
import java.util.Date;

public class User {

    String address, email, name, phone, role,empty_box;
    int  total_boxes;
    boolean is_blocked, is_paid_user, login_status, show_header;
    Date created_at, updated_at;
    String empty_box_custom_image;
    ArrayList<String> array_for_boxes;

    public String getEmpty_box_custom_image() {
        return empty_box_custom_image;
    }

    public void setEmpty_box_custom_image(String empty_box_custom_image) {
        this.empty_box_custom_image = empty_box_custom_image;
    }


    public boolean isShow_header() {
        return show_header;
    }

    public void setShow_header(boolean show_header) {
        this.show_header = show_header;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getEmpty_box() {
        return empty_box;
    }

    public void setEmpty_box(String empty_box) {
        this.empty_box = empty_box;
    }

    public int getTotal_boxes() {
        return total_boxes;
    }

    public void setTotal_boxes(int total_boxes) {
        this.total_boxes = total_boxes;
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

    public ArrayList<String> getArray_for_boxes() {
        return array_for_boxes;
    }

    public void setArray_for_boxes(ArrayList<String> array_for_boxes) {
        this.array_for_boxes = array_for_boxes;
    }
}
