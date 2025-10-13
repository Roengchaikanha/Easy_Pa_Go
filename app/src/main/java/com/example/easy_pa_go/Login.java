package com.example.easy_pa_go;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Instant;

public class Login extends AppCompatActivity {
    //ประกาศตัวแปร ปุ่มไปหน้าลงทะเบียน
    private Button BRegister;

    //ประกาศตัวแปร ปุ่ม login
    private Button Blogin;

    //login --> User,Password
    EditText user, password;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ปุ่มไปหน้า Register
        BRegister = findViewById(R.id.buttonRegister);
        BRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Register");
                Intent Register = new Intent(getApplicationContext(), Register.class);
                startActivity(Register);
            }
        });

        //User,Password,ButtonLogin
        user = (EditText) findViewById(R.id.editUser);
        password = (EditText) findViewById(R.id.editPassword);

        Blogin = (Button) findViewById(R.id.buttonLogin);
        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getText().toString().equals("6712247012") && password.getText().toString().equals("1234")) {
                    Intent Login = new Intent(getApplicationContext(), UserInterface.class);
                    startActivity(Login);
                } else {
                    Toast.makeText(getApplicationContext(), "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
