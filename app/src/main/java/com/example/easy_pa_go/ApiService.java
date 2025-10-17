package com.example.easy_pa_go;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // ส่งข้อมูล user_id และ password ไปยัง login.php เพื่อเข้าสู่ระบบ
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("user_id") String userId,
            @Field("password") String password
    );

    // ส่งข้อมูลผู้ใช้ใหม่ (user_id, password, first_name, last_name, major_id) ไปยัง register.php เพื่อสมัครสมาชิก
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("user_id") String userId,
            @Field("password") String password,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("major_id") String majorId
    );

//ดึงข้อมูลสาขาจากฐานข้อมูลget_majors.php (ไม่ต้องส่งค่าอะไรไป)
    @GET("get_majors.php")
    Call<MajorResponse> getMajors();

//ส่ง major_id ไปให้ get_schedule.php เพื่อดึงข้อมูลตารางเรียนจากฐานข้อมูล
    @FormUrlEncoded
    @POST("get_schedule.php")
    Call<UserInterface.ScheduleResponse> getSchedule(@Field("major_id") String majorId);

    //ส่ง location_id ไปให้ get_location.php เพื่อดึงข้อมูลสถานที่จากฐานข้อมูล
    @FormUrlEncoded
    @POST("get_location.php")
    Call<ShowData.LocationResponse> getLocationDetails(@Field("location_id") String locationId);

}

