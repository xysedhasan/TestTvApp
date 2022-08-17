package com.app.beyondlotto.Model;

import java.util.ArrayList;

public class Screen1 {
    ArrayList<String>  array_for_boxes = new ArrayList<>();
    ArrayList<Boolean> animation_for_boxes = new ArrayList<>();
    String empty_box,empty_box_custom_image,orientation;
    boolean show_header;
    int total_boxes;


    public ArrayList<Boolean> getAnimation_for_boxes() {
        return animation_for_boxes;
    }

    public void setAnimation_for_boxes(ArrayList<Boolean> animation_for_boxes) {
        this.animation_for_boxes = animation_for_boxes;
    }

    public ArrayList<String> getArray_for_boxes() {
        return array_for_boxes;
    }

    public void setArray_for_boxes(ArrayList<String> array_for_boxes) {
        this.array_for_boxes = array_for_boxes;
    }

    public String getEmpty_box() {
        return empty_box;
    }

    public void setEmpty_box(String empty_box) {
        this.empty_box = empty_box;
    }

    public String getEmpty_box_custom_image() {
        return empty_box_custom_image;
    }

    public void setEmpty_box_custom_image(String empty_box_custom_image) {
        this.empty_box_custom_image = empty_box_custom_image;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public boolean isShow_header() {
        return show_header;
    }

    public void setShow_header(boolean show_header) {
        this.show_header = show_header;
    }

    public int getTotal_boxes() {
        return total_boxes;
    }

    public void setTotal_boxes(int total_boxes) {
        this.total_boxes = total_boxes;
    }
}
