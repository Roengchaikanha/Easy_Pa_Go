package com.example.easy_pa_go;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    // --- ประกาศตัวแปร UI ด้วย camelCase ---
    private EditText userIdEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button goToRegisterButton;
    private ProgressBar loadingProgressBar;
    private View loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupButtonClickListeners();
    }

    /**
     * เมธอดสำหรับเชื่อมตัวแปรกับ View ใน Layout
     * แก้ไข ID ให้ถูกต้องตามไฟล์ XML ของคุณ
     */
    private void initializeViews() {
        // <<<<< แก้ไขจุดที่ 1 >>>>>
        // แก้ไข ID ให้ตรงกับไฟล์ activity_login.xml
        userIdEditText = findViewById(R.id.editUser);
        passwordEditText = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.buttonLogin);
        goToRegisterButton = findViewById(R.id.Register);

        // <<<<< เพิ่มเข้ามาใหม่ >>>>>
        // เชื่อมตัวแปรสำหรับ ProgressBar และ Layout หลัก
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        loginLayout = findViewById(R.id.buttonLogin);
    }

    /**
     * เมธอดสำหรับจัดการการคลิกปุ่มทั้งหมดในหน้านี้
     */
    private void setupButtonClickListeners() {
        // ตั้งค่าปุ่ม "เข้าสู่ระบบ" (GO)
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndLoginUser();
            }
        });

        // ตั้งค่าปุ่ม "ไปหน้าลงทะเบียน" (Register)
        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    /**
     * เมธอดสำหรับตรวจสอบข้อมูลและเริ่มกระบวนการล็อกอิน
     */
    private void validateAndLoginUser() {
        String userId = userIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (userId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
            return;
        }

        // <<<<< เพิ่มเข้ามาใหม่ >>>>>
        // แสดง ProgressBar ก่อนเริ่มส่งข้อมูล
        showLoading(true);
        performLogin(userId, password);
    }

    /**
     * เมธอดสำหรับส่งข้อมูลล็อกอินไปที่เซิร์ฟเวอร์
     */
    private void performLogin(String userId, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(userId, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    if ("success".equals(loginResponse.getStatus())) {
                        // --- ล็อกอินสำเร็จ ---
                        // <<<<< แก้ไขส่วนนี้ทั้งหมด >>>>>

                        // 1. สร้างข้อความต้อนรับ
                        String welcomeMessage = "ยินดีต้อนรับคุณ " + loginResponse.getUser().getFirstName();
                        Toast.makeText(Login.this, welcomeMessage, Toast.LENGTH_SHORT).show();

                        // 2. หน่วงเวลา 2 วินาที (2000 milliseconds)
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            // 3. สร้าง Intent และแนบข้อมูล
                            Intent intent = new Intent(Login.this, UserInterface.class);
                            intent.putExtra("USER_MAJOR_ID", loginResponse.getUser().getMajorId());
                            intent.putExtra("USER_FULL_NAME", loginResponse.getUser().getFirstName() + " " + loginResponse.getUser().getLastName());

                            // 4. เริ่มเดินทางและปิดหน้าปัจจุบัน
                            startActivity(intent);
                            finish();
                        }, 2000); // 2000ms = 2 วินาที

                    } else {
                        // --- ล็อกอินไม่สำเร็จ ---
                        showLoading(false); // ซ่อน ProgressBar
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    showLoading(false);
                    Toast.makeText(Login.this, "การล็อกอินผิดพลาด", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showLoading(false); // ซ่อน ProgressBar
                Toast.makeText(Login.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", "API Call Failed: ", t);
            }
        });
    }

    /**
     * <<<<< เพิ่มเมธอดนี้เข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับควบคุมการแสดงผลของ ProgressBar และฟอร์ม Login
     */
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        } else {
            loadingProgressBar.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        }
    }
}

