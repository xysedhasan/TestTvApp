package com.app.beyondlottotv.Api;

import com.google.gson.annotations.SerializedName;

public class RequestData {
    @SerializedName("uid")
    String uid;

    public RequestData(String uid) {
        this.uid = uid;
    }
}