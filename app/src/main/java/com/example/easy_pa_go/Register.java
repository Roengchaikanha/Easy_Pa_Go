package com.example.easy_pa_go;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    // --- ประกาศตัวแปร UI ด้วย camelCase ---
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userIdEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner majorSpinner;
    private Button registerButton;
    private Button backToLoginButton;

    // --- ตัวแปรสำหรับจัดการข้อมูล ---
    private List<Major> majorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setupButtonClickListeners(); // <-- เพิ่มเข้ามาใหม่
        fetchMajors();
    }

    private void initializeViews() {
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        userIdEditText = findViewById(R.id.editTextUserIdReg);
        passwordEditText = findViewById(R.id.editTextPasswordReg);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        majorSpinner = findViewById(R.id.spinnerMajor);
        registerButton = findViewById(R.id.Register);
        backToLoginButton = findViewById(R.id.buttonBackToLogin);
    }

    /**
     * <<<<< ส่วนที่ 1: เพิ่มเมธอดนี้เข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับจัดการการคลิกปุ่มทั้งหมดในหน้านี้
     */
    private void setupButtonClickListeners() {
        // ตั้งค่าการคลิกของปุ่ม "ยืนยัน"
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // เมื่อผู้ใช้กดปุ่ม "ยืนยัน" ให้เรียกใช้เมธอด validateAndRegisterUser()
                // ซึ่งเมธอดนี้จะตรวจสอบข้อมูลและลงทะเบียนผู้ใช้
                validateAndRegisterUser();
            }
        });

        // ตั้งค่าการคลิกของปุ่ม "กลับไปหน้าเข้าสู่ระบบ"
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // เมื่อผู้ใช้กดปุ่ม "กลับ" ให้ปิด Activity ปัจจุบัน
                // ทำให้หน้าจอกลับไปหน้า Login ที่อยู่ด้านหลังใน stack
                finish();
            }
        });
    }

    // ฟังก์ชันนี้ใช้ดึงข้อมูลสาขาวิชาทั้งหมดจากฐานข้อมูลผ่าน API get_majors.php ที่อยู่ในเครื่อง
    private void fetchMajors() {
        // apiService เพื่อเรียกใช้งาน API
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // สร้างคำสั่งเรียก API getMajors() ซึ่งจะส่ง request ไปที่เซิร์ฟเวอร์
        Call<MajorResponse> call = apiService.getMajors();
        // ส่ง request แบบ asynchronous (ไม่บล็อก UI) และรอ callback
        call.enqueue(new Callback<MajorResponse>() {
            @Override
            public void onResponse(Call<MajorResponse> call, Response<MajorResponse> response) {

                // ตรวจสอบว่า response สำเร็จ และมีข้อมูล ไม่เป็น null และ status = "success"
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {

                    // ดึงรายการสาขาจาก response มาเก็บใน majorList
                    majorList = response.body().getData();


                    // สร้าง List<String> สำหรับเก็บชื่อสาขา
                    List<String> majorNames = new ArrayList<>();
                    for (Major major : majorList) {
                        majorNames.add(major.getMajorName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Register.this,
                            android.R.layout.simple_spinner_item, majorNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    majorSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(Register.this, "ไม่สามารถดึงข้อมูลสาขาได้", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MajorResponse> call, Throwable t) {
                Toast.makeText(Register.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ฟังก์ชันนี้ใช้ตรวจสอบข้อมูลที่ผู้ใช้กรอกในหน้า Register และดึงค่าจาก EditText / Spinner เพื่อเตรียมส่งไปสมัครสมาชิก
    private void validateAndRegisterUser() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String userId = userIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        int selectedPosition = majorSpinner.getSelectedItemPosition();

        // --- เริ่มการตรวจสอบข้อมูล ---
        if (firstName.isEmpty() || lastName.isEmpty() || userId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_SHORT).show();
            return; // หยุดทำงานถ้ามีช่องว่าง
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "รหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT).show();
            return; // หยุดทำงานถ้ารหัสผ่านไม่ตรงกัน
        }

        if (majorList.isEmpty()) {
            Toast.makeText(this, "กรุณารอสักครู่ กำลังโหลดข้อมูลสาขา...", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- ถ้าข้อมูลถูกต้องทั้งหมด ---
        String majorId = majorList.get(selectedPosition).getMajorId();
        performRegistration(userId, password, firstName, lastName, majorId);
    }

    /**
     * <<<<< ส่วนที่ 3: เพิ่มเมธอดนี้เข้ามาใหม่ทั้งหมด >>>>>
     * เมธอดสำหรับส่งข้อมูลไปที่เซิร์ฟเวอร์
     */
    private void performRegistration(String userId, String password, String firstName, String lastName, String majorId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<RegisterResponse> call = apiService.register(userId, password, firstName, lastName, majorId);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // แสดงข้อความที่ได้รับจากเซิร์ฟเวอร์ (เช่น "ลงทะเบียนสำเร็จ")
                    Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    // ถ้าสำเร็จ ให้กลับไปหน้า Login
                    if ("success".equals(response.body().getStatus())) {
                        finish();
                    }
                } else {
                    Toast.makeText(Register.this, "การลงทะเบียนผิดพลาด", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Register.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}