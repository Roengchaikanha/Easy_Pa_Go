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

    /**
     * <<<<< ส่วนที่เพิ่มเข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับสร้างเมนู (ไอคอนรูปคน) บน Toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // นำเมนูจากไฟล์ menu_user_interface.xml มาแสดง
        getMenuInflater().inflate(R.menu.menu_user_interface, menu);
        return true;
    }

    /**
     * <<<<< ส่วนที่เพิ่มเข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับจัดการเมื่อผู้ใช้กดที่เมนู
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // ตรวจสอบว่าปุ่มที่ถูกกดคือปุ่ม logout หรือไม่
        if (item.getItemId() == R.id.action_logout) {
            // --- เมื่อกดปุ่ม Logout ---
            showLogoutConfirmationDialog(); // เรียกใช้เมธอดแสดงกล่องข้อความยืนยัน
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * <<<<< ส่วนที่เพิ่มเข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับสร้างและแสดงกล่องข้อความยืนยันการออกจากระบบ
     */
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("ออกจากระบบ")
                .setMessage("คุณต้องการออกจากระบบหรือไม่?")
                .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // --- ถ้าผู้ใช้กด "ใช่" ---
                        logoutUser();
                    }
                })
                .setNegativeButton("ไม่", null) // ถ้ากด "ไม่" ก็ไม่ต้องทำอะไร กล่องข้อความจะปิดไปเอง
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * <<<<< ส่วนที่เพิ่มเข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับจัดการการออกจากระบบ
     */
    private void logoutUser() {
        // สร้าง Intent เพื่อกลับไปหน้า Login
        Intent intent = new Intent(UserInterface.this, Login.class);
        // ตั้งค่า Flag เพื่อล้างหน้าเก่าๆ ทั้งหมดออกจากระบบ (ป้องกันการกดย้อนกลับมาหน้านี้อีก)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // ปิดหน้านี้ทิ้ง
    }


    // --- พิมพ์เขียวสำหรับข้อมูล (Model Classes) ---
    public static class ScheduleItem {
        @SerializedName("day") private String day;
        @SerializedName("status") private String status;
        @SerializedName("course_id") private String courseId;
        @SerializedName("start_time") private String startTime;
        @SerializedName("end_time") private String endTime;
        @SerializedName("location_id") private String locationId;

        // Getters
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