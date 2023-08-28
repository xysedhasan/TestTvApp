package com.app.beyondlottotv.Model;

public class LoginData {
    private String id;
    private String token;
    private long time;
    boolean status;

    public LoginData() {
    }

    public LoginData(String id, String token, long time, boolean status) {
        this.id = id;
        this.token = token;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
