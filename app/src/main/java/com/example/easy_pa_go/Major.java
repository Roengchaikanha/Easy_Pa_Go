package com.example.easy_pa_go; // <-- ชื่อ package ของคุณ

import com.google.gson.annotations.SerializedName;

public class Major {

    @SerializedName("major_id")
    private String majorId;

    @SerializedName("major_name")
    private String majorName;

    // Getter methods
    public String getMajorId() {
        return majorId;
    }

    public String getMajorName() {
        return majorName;
    }
}
