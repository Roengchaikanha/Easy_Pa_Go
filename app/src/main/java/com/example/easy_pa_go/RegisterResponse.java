package com.example.easy_pa_go; // ชื่อ package ของคุณ

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    // --- Getter methods ---
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}