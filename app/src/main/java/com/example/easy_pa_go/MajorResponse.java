package com.example.easy_pa_go;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// คลาส MajorResponse ใช้เก็บผลลัพธ์จาก API get_majors.php
public class MajorResponse {

    // สถานะของการเรียก API เช่น "success" หรือ "error"
    @SerializedName("status")
    private String status;

    // รายการสาขาที่ได้จาก API (List ของคลาส Major)
    @SerializedName("data")
    private List<Major> data;

    // --- Getter methods ---
    // ใช้เรียกสถานะของ API
    public String getStatus() {
        return status;
    }

    // ใช้เรียกรายการสาขาที่ได้จาก API
    public List<Major> getData() {
        return data;
    }
}
