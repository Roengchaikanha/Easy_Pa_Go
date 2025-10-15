package com.example.easy_pa_go;

import com.google.gson.annotations.SerializedName;

/**
 * คลาสนี้ทำหน้าที่เป็น "พิมพ์เขียว" หรือ Model
 * สำหรับข้อมูลตารางเรียนแต่ละรายการที่ได้รับมาจากเซิร์ฟเวอร์
 * เราจะใช้ camelCase ตามกฎที่เราตั้งไว้
 */
public class ScheduleItem {

    @SerializedName("day")
    private String day;

    @SerializedName("status")
    private String status;

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("location_id")
    private String locationId;

    // --- Getter methods ---
    public String getDay() {
        return day;
    }

    public String getStatus() {
        return status;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocationId() {
        return locationId;
    }
}