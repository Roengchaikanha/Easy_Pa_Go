package com.example.easy_pa_go;

import com.google.gson.annotations.SerializedName;

// คลาส UserData ใช้เก็บข้อมูลของผู้ใช้ที่ได้รับจากเซิร์ฟเวอร์หลังล็อกอินสำเร็จ
class UserData {
    // ระบุว่าข้อมูลจาก JSON "user_id" "first_name" "last_name" และ "major_id" จะถูฏเก็บไว้ใน userId, firstName, lastName และ majorId ตามลำดับ
    @SerializedName("user_id")
    private String userId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("major_id")
    private String majorId;

    // --- Getter methods ---
    // ใช้เรียกค่าชื่อผู้ใช้ และชื่อ-สกุล ของผู้ใช้
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


// คลาส LoginResponse ใช้เก็บผลลัพธ์ที่ได้จาก API login.php
public class LoginResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private UserData user;

    // --- Getter methods ใช้เรียกดูค่าที่ได้จาก API ---
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
