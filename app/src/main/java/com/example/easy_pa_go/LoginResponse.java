package com.example.easy_pa_go; // ชื่อ package ของคุณ

import com.google.gson.annotations.SerializedName;

/**
 * คลาสนี้เป็นพิมพ์เขียวสำหรับข้อมูล User ที่ได้รับหลัง Login สำเร็จ
 * ถูกสร้างขึ้นเพื่อรองรับโครงสร้าง JSON ที่ซ้อนกันอยู่ (Nested JSON)
 * เป็นไปตามหลักการ Object-Oriented Programming
 */
class UserData {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("major_id")
    private String majorId;

    // --- Getter methods ---
    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMajorId() {
        return majorId;
    }
}


/**
 * คลาสหลักที่เป็นพิมพ์เขียวสำหรับ JSON ทั้งหมดที่ได้รับจาก login.php
 */
public class LoginResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private UserData user;

    // --- Getter methods ---
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserData getUser() {
        return user;
    }
}
