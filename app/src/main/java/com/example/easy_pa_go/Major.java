package com.example.easy_pa_go; // <-- ชื่อ package ของคุณ

import com.google.gson.annotations.SerializedName;
// คลาส Major ใช้เก็บข้อมูลสาขาจากเซิร์ฟเวอร์
public class Major {
    // ระบุว่า JSON "major_id" จะถูกเก็บในตัวแปร majorId
    @SerializedName("major_id")
    private String majorId;
    // ระบุว่า JSON "major_name" จะถูกเก็บในตัวแปร majorName
    @SerializedName("major_name")
    private String majorName;

    // Getter methods
    // ใช้เรียกค่า major ID
    public String getMajorId() {
        return majorId;
    }
    // ใช้เรียกค่า major name
    public String getMajorName() {
        return majorName;
    }
}
