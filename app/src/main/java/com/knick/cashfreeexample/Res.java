package com.knick.cashfreeexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("cftoken")
    @Expose
    private String cftoken;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }
}
