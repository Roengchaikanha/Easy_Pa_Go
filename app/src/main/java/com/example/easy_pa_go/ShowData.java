package com.example.easy_pa_go;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowData extends AppCompatActivity {
//ประกาศ Class ShowData
    private ImageView buildingImageView;
    private TextView buildingNameTextView;
    private TextView floorTextView;
    private TextView roomTextView;
    private TextView roomTypeTextView;
    private TextView capacityTextView;
    private Button goToMapButton;
//ประกาศตัวแปรใช้เชื่อมโยงกับ UI
    private LocationItem currentLocation; // ตัวแปรสำหรับเก็บข้อมูลสถานที่ปัจจุบัน

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        initializeViews();
        String locationId = getIntent().getStringExtra("LOCATION_ID");

        if (locationId != null && !locationId.isEmpty()) {
            fetchLocationData(locationId);
        } else {
            Toast.makeText(this, "ไม่พบรหัสสถานที่", Toast.LENGTH_SHORT).show();
        }

        // <<<<< ส่วนที่เพิ่มเข้ามาใหม่ >>>>>
        // ตั้งค่าให้ปุ่ม "เปิดแผนที่นำทาง" ทำงาน
        goToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationInGoogleMaps();
            }
        });
    }

    private void initializeViews() {
        buildingImageView = findViewById(R.id.buildingImageView);
        buildingNameTextView = findViewById(R.id.buildingNameTextView);
        floorTextView = findViewById(R.id.floorTextView);
        roomTextView = findViewById(R.id.roomTextView);
        roomTypeTextView = findViewById(R.id.roomTypeTextView);
        capacityTextView = findViewById(R.id.capacityTextView);
        goToMapButton = findViewById(R.id.goToMapButton);
    }
    //เชื่อมตัวแปรกับ UI ในไฟล์ .XML
    private void fetchLocationData(String locationId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LocationResponse> call = apiService.getLocationDetails(locationId);
    //ดึงข้อมูลจาก API ตาม LocationID
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    currentLocation = response.body().getData(); // เก็บข้อมูลสถานที่ไว้

                    // นำข้อมูลไปแสดงผล
                    buildingNameTextView.setText(currentLocation.getBuildingName());
                    floorTextView.setText("ชั้น: " + currentLocation.getFloor());
                    roomTextView.setText("ห้อง: " + currentLocation.getRoomNumber());
                    roomTypeTextView.setText("ชนิดห้อง: " + currentLocation.getRoomType());
                    capacityTextView.setText("จำนวนที่นั่ง: " + String.valueOf(currentLocation.getCapacity()));

                    // โหลดรูปภาพ
                    String imageName = currentLocation.getBuildingImage();
                    if (imageName != null && !imageName.isEmpty()) {
                        String imageUrl = ApiClient.BASE_URL + "images/" + imageName;
                        Glide.with(ShowData.this).load(imageUrl).into(buildingImageView);
                    }
                } else {
                    Toast.makeText(ShowData.this, "ไม่พบข้อมูลสถานที่", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Toast.makeText(ShowData.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
//สิ้นสุดการแสดงหน้าของ ShowData
    /**
     * <<<<< เมธอดใหม่สำหรับเปิด Google Maps >>>>>
     */
    private void openNavigationInGoogleMaps() {
        if (currentLocation == null || currentLocation.getMapCoordinates() == null || currentLocation.getMapCoordinates().isEmpty()) {
            Toast.makeText(this, "ไม่มีข้อมูลพิกัดสำหรับสถานที่นี้", Toast.LENGTH_SHORT).show();
            return;
        }

        // สร้าง URI สำหรับส่งให้ Google Maps
        // รูปแบบคือ "google.navigation:q=lat,lng"
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + currentLocation.getMapCoordinates());

        // สร้าง Intent เพื่อเปิดแอป Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // ลองเปิดแอป Google Maps
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            // กรณีที่ผู้ใช้ไม่มีแอป Google Maps ในเครื่อง
            Toast.makeText(this, "ไม่พบแอปพลิเคชัน Google Maps", Toast.LENGTH_LONG).show();
        }
    }

    // --- พิมพ์เขียวสำหรับข้อมูล (Model Classes) ---
    public static class LocationItem {
        @SerializedName("building_name") private String buildingName;
        @SerializedName("floor") private String floor;
        @SerializedName("room_number") private String roomNumber;
        @SerializedName("room_type") private String roomType;
        @SerializedName("capacity") private int capacity;
        @SerializedName("map_coordinates") private String mapCoordinates;
        @SerializedName("building_image") private String buildingImage;

        // Getters
        public String getBuildingName() { return buildingName; }
        public String getFloor() { return floor; }
        public String getRoomNumber() { return roomNumber; }
        public String getRoomType() { return roomType; }
        public int getCapacity() { return capacity; }
        public String getMapCoordinates() { return mapCoordinates; }
        public String getBuildingImage() { return buildingImage; }
    }

    public static class LocationResponse {
        @SerializedName("status") private String status;
        @SerializedName("data") private LocationItem data;

        // Getters
        public String getStatus() { return status; }
        public LocationItem getData() { return data; }
    }
}

