package com.example.easy_pa_go;

import com.google.gson.annotations.SerializedName;

/**
 * คลาสนี้ทำหน้าที่เป็น "พิมพ์เขียว" หรือ Model
 * สำหรับข้อมูลตารางเรียนแต่ละรายการที่ได้รับมาจากเซิร์ฟเวอร์
 * เป็นไปตามหลัก OOP และใช้ camelCase
 */
public class ScheduleItem {

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("course_name")
    private String courseName;

    @SerializedName("day")
    private String day;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("building_name")
    private String buildingName;

    @SerializedName("room_number")
    private String roomNumber;

    @SerializedName("location_id")
    private String locationId;

    // --- Getter methods ---
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getLocationId() {
        return locationId;
    }
}

