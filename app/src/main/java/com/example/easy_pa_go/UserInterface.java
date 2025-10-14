package com.example.easy_pa_go;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInterface extends AppCompatActivity {

    // --- ประกาศตัวแปร UI และ Adapter ---
    private RecyclerView scheduleRecyclerView;
    private ScheduleAdapter scheduleAdapter;
    private TextView userNameTextView;
    private Toolbar toolbar;

    // --- ตัวแปรสำหรับเก็บข้อมูล ---
    private List<ScheduleItem> scheduleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        // 1. เชื่อม UI และตั้งค่า Toolbar
        initializeViews();
        setupToolbar();

        // 2. รับข้อมูล (major_id และ full_name) ที่ส่งมาจากหน้า Login
        Intent intent = getIntent();
        String userMajorId = intent.getStringExtra("USER_MAJOR_ID");
        String userFullName = intent.getStringExtra("USER_FULL_NAME");

        // 3. แสดงชื่อผู้ใช้ที่ TextView ด้านล่าง
        if (userFullName != null) {
            userNameTextView.setText(userFullName);
        }

        // 4. ตั้งค่า RecyclerView ให้พร้อมใช้งาน
        setupRecyclerView();

        // 5. ดึงข้อมูลตารางเรียนจากเซิร์ฟเวอร์
        if (userMajorId != null) {
            fetchScheduleData(userMajorId);
        } else {
            Toast.makeText(this, "ไม่พบรหัสสาขา", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * เมธอดสำหรับเชื่อมตัวแปรกับ View ใน Layout
     */
    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        scheduleRecyclerView = findViewById(R.id.scheduleRecyclerView);
        userNameTextView = findViewById(R.id.userNameTextView);
    }

    /**
     * เมธอดสำหรับตั้งค่า Toolbar ให้เป็น ActionBar ของหน้านี้
     */
    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    /**
     * เมธอดสำหรับตั้งค่า RecyclerView และ Adapter
     */
    private void setupRecyclerView() {
        // กำหนด LayoutManager เพื่อให้รายการแสดงผลเป็นแนวตั้ง
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // สร้าง "ช่างฝีมือ" (Adapter) และส่ง "วัตถุดิบ" (list เปล่าๆ) ให้ไปก่อน
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        // บอก RecyclerView ว่าให้ใช้ช่างฝีมือคนนี้
        scheduleRecyclerView.setAdapter(scheduleAdapter);
    }

    /**
     * เมธอดสำหรับดึงข้อมูลตารางเรียนจากเซิร์ฟเวอร์
     */
    private void fetchScheduleData(String majorId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ScheduleResponse> call = apiService.getSchedule(majorId);

        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    // --- ดึงข้อมูลสำเร็จ ---
                    scheduleList.clear(); // ล้างข้อมูลเก่า (ถ้ามี)
                    scheduleList.addAll(response.body().getData()); // เพิ่มข้อมูลใหม่
                    scheduleAdapter.notifyDataSetChanged(); // บอก "ช่างฝีมือ" ว่ามีวัตถุดิบใหม่มาแล้ว ให้ทำงานได้!
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
     * เมธอดสำหรับสร้างเมนู (ไอคอนรูปคน) บน Toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_interface, menu);
        return true;
    }

    /**
     * เมธอดสำหรับจัดการเมื่อผู้ใช้กดที่เมนู
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            // --- เมื่อกดปุ่ม Logout ---
            Intent intent = new Intent(UserInterface.this, Login.class);
            // ตั้งค่า Flag เพื่อล้างหน้าเก่าๆ ทั้งหมดออกจากระบบ
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // ปิดหน้านี้ทิ้ง
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
