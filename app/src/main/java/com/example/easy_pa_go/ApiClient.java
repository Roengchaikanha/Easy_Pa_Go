package com.example.easy_pa_go; // ชื่อ package ของคุณ

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // ***** แก้ไขตรงนี้เป็น IP Address ของเครื่องคุณ *****
    public static final String BASE_URL = "http://192.168.1.10/project_epg/";

    private static Retrofit retrofit = null;

    /**
     * เมธอดนี้จะสร้างและส่งคืน Retrofit object
     * โดยจะสร้างแค่ครั้งแรกครั้งเดียว (Singleton Pattern)
     */
    public static Retrofit getClient() {
        // ถ้า retrofit ยังไม่เคยถูกสร้าง
        if (retrofit == null) {
            // ให้สร้างขึ้นมาใหม่
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        // ส่งคืน object ที่สร้างไว้ (ไม่ว่าจะสร้างใหม่หรือของเดิม)
        return retrofit;
    }
}