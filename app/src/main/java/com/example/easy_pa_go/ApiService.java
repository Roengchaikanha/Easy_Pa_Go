package com.example.easy_pa_go;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("user_id") String userId,
            @Field("password") String password
    );

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

    @FormUrlEncoded
    @POST("get_schedule.php")
    Call<UserInterface.ScheduleResponse> getSchedule(@Field("major_id") String majorId);

    /**
     * <<<<< ส่วนที่แก้ไขให้ถูกต้อง >>>>>
     * ส่ง location_id ไปให้ get_location.php เพื่อดึงข้อมูลสถานที่
     */
    @FormUrlEncoded
    @POST("get_location.php")
    Call<ShowData.LocationResponse> getLocationDetails(@Field("location_id") String locationId);

}

