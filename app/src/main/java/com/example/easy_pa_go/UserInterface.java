package com.example.easy_pa_go;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInterface extends AppCompatActivity {

    private RecyclerView scheduleRecyclerView;
    private ScheduleAdapter scheduleAdapter;
    private List<ScheduleItem> scheduleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        // 1. ตั้งค่า Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ตารางเรียน");
        }

        // 2. รับ major_id ที่ส่งมาจากหน้า Login
        String userMajorId = getIntent().getStringExtra("USER_MAJOR_ID");

        // 3. ตั้งค่า RecyclerView และ "จ้างช่างฝีมือ" (Adapter)
        scheduleRecyclerView = findViewById(R.id.scheduleRecyclerView);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        // 4. สั่งให้ดึงข้อมูลตารางเรียนจากเซิร์ฟเวอร์
        if (userMajorId != null && !userMajorId.isEmpty()) {
            fetchScheduleData(userMajorId);
        } else {
            Toast.makeText(this, "ไม่พบรหัสสาขา", Toast.LENGTH_SHORT).show();
        }
    }
//ดึงข้อมูลตารางวิชาเรียนมาจาก database มาสดงผล
    private void fetchScheduleData(String majorId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ScheduleResponse> call = apiService.getSchedule(majorId);

        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    scheduleList.clear();
                    scheduleList.addAll(response.body().getData());
                    scheduleAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserInterface.this, "ไม่พบตารางเรียน", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Toast.makeText(UserInterface.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    // --- พิมพ์เขียวสำหรับข้อมูล (Model Classes) ---
    public static class ScheduleItem {
        @SerializedName("day") private String day;
        @SerializedName("status") private String status;
        @SerializedName("course_id") private String courseId;
        @SerializedName("start_time") private String startTime;
        @SerializedName("end_time") private String endTime;
        @SerializedName("location_id") private String locationId;

        // Getters ดึงค่ามาแสดงตามที่ return
        public String getDay() { return day; }
        public String getStatus() { return status; }
        public String getCourseId() { return courseId; }
        public String getStartTime() { return startTime; }
        public String getEndTime() { return endTime; }
        public String getLocationId() { return locationId; }
    }

    public static class ScheduleResponse {
        @SerializedName("status") private String status;
        @SerializedName("data") private List<ScheduleItem> data;

        // Getters
        public String getStatus() { return status; }
        public List<ScheduleItem> getData() { return data; }
    }
}