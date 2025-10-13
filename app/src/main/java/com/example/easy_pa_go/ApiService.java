package com.example.easy_pa_go; // ชื่อ package ของคุณ

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * ส่งข้อมูล user และ password ไปให้ login.php ตรวจสอบ
     */
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("user_id") String userId,
            @Field("password") String password
    );

    /**
     * ส่งข้อมูลผู้ใช้ใหม่ไปให้ register.php
     */
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("user_id") String userId,
            @Field("password") String password,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("major_id") String majorId
    );

    /**
     * ดึงรายชื่อสาขาทั้งหมดจาก get_majors.php
     */
    @GET("get_majors.php")
    Call<MajorResponse> getMajors();

    // ในอนาคตเราจะมาเพิ่มคำสั่ง API อื่นๆ ที่นี่
}
