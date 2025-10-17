package com.example.easy_pa_go;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // ***** URL หลักของเซิร์ฟเวอร์ (ให้แก้เป็น IP Address เครื่องของคุณเอง) *****//
    public static final String BASE_URL = "http://192.168.1.111/project_epg/";

    // ตัวแปรเก็บ instance ของ Retrofit เพื่อให้ใช้ซ้ำได้
    private static Retrofit retrofit = null;

    // สร้างหรือคืนค่า Retrofit client ที่เชื่อมต่อกับ BASE_URL
    public static Retrofit getClient() {
        // ตรวจสอบว่า retrofit ยังไม่ถูกสร้างมาก่อน
        if (retrofit == null) {
            // ถ้ายังไม่ถูกสร้าง ให้สร้างใหม่
            retrofit = new Retrofit.Builder()
                    // ตั้งค่า URL หลักของ API
                    .baseUrl(BASE_URL)
                    // แปลงข้อมูล JSON เป็น Java object โดยใช้ Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    // สร้าง Retrofit object
                    .build();
        }
        // คืนค่า retrofit ที่สร้างไว้ (จะใช้ซ้ำได้ ไม่ต้องสร้างใหม่ทุกครั้ง)
        return retrofit;
    }
}
