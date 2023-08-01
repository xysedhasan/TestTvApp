package com.app.beyondlottotv.Model;

public class Box {
    String game_image,game_no,ticket_no,ticket_value,pack_size;
    Boolean animation;

    public Box() {
    }

    public String getPack_size() {
        return pack_size;
    }

    public void setPack_size(String pack_size) {
        this.pack_size = pack_size;
    }

    public String getGame_image() {
        return game_image;
    }

    public void setGame_image(String game_image) {
        this.game_image = game_image;
    }

    public String getGame_no() {
        return game_no;
    }

    public void setGame_no(String game_no) {
        this.game_no = game_no;
    }

    public String getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

    public String getTicket_value() {
        return ticket_value;
    }

    public void setTicket_value(String ticket_value) {
        this.ticket_value = ticket_value;
    }

    public Boolean getAnimation() {
        return animation;
    }

    public void setAnimation(Boolean animation) {
        this.animation = animation;
    }
}
