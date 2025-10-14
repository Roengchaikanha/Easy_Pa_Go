package com.example.easy_pa_go;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        // ของเดิม: findViewById(R.id.editPassword)
        // แก้เป็น: R.id.editUser ให้ตรงกับช่องกรอก User
        userIdEditText = findViewById(R.id.editUser);

        // บรรทัดนี้ถูกต้องแล้ว
        passwordEditText = findViewById(R.id.editPassword);

        // บรรทัดนี้ถูกต้องแล้ว
        loginButton = findViewById(R.id.buttonLogin);

        // <<<<< แก้ไขจุดที่ 2 >>>>>
        // ของเดิม: findViewById(R.id.Register)
        // แก้เป็น: R.id.buttonRegister ให้ตรงกับ ID ของปุ่ม Register
        goToRegisterButton = findViewById(R.id.Register);
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
                        Toast.makeText(Login.this, "ล็อกอินสำเร็จ!", Toast.LENGTH_SHORT).show();

                        // ไปยังหน้า UserInterface หลังจากล็อกอินสำเร็จ
                        Intent intent = new Intent(Login.this, UserInterface.class);
                        String userMajorId = loginResponse.getUser().getMajorId();
                        intent.putExtra("USER_MAJOR_ID", userMajorId);
                        startActivity(intent);
                        finish(); // ปิดหน้า Login ทิ้งไปเลย

                    } else {
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "การล็อกอินผิดพลาด", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", "API Call Failed: ", t);
            }
        });
    }
}

