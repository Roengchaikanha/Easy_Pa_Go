package com.example.easy_pa_go;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * คลาสนี้เป็นพิมพ์เขียวสำหรับ "กล่องพัสดุ" ทั้งหมด
 * ที่ได้รับจาก get_schedule.php ซึ่งมี status และรายการข้อมูล (data)
 */
public class ScheduleResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<ScheduleItem> data;

    // --- Getter methods ---
    public String getStatus() {
        return status;
    }

    public List<ScheduleItem> getData() {
        return data;
    }
}
