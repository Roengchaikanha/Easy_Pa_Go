package com.example.easy_pa_go;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("user_id") String userId, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("user_id") String userId,
            @Field("password") String password,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("major_id") String majorId
    );

    @GET("get_majors.php")
    Call<MajorResponse> getMajors();

    /**
     * ส่ง major_id ไปให้ get_schedule.php เพื่อดึงข้อมูลตารางเรียน
     */
    @FormUrlEncoded
    @POST("get_schedule.php")
    Call<UserInterface.ScheduleResponse> getSchedule(@Field("major_id") String majorId);

}

