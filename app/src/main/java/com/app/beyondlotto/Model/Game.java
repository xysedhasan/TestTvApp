package com.app.beyondlotto.Model;

import java.util.Date;

public class Game {
    String description,image_url,number,ticket_value;
    boolean is_active;
    Date created_at;


    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(String ticket_value) {
        this.ticket_value = ticket_value;
    }

}
