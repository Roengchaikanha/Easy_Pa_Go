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
// import คลาสที่จำเป็นสำหรับการทำงานต่างๆ
public class ShowData extends AppCompatActivity {
//ประกาศ Class ShowData
    private ImageView buildingImageView;
    private TextView buildingNameTextView;
    private TextView floorTextView;
    private TextView roomTextView;
    private TextView roomTypeTextView;
    private TextView capacityTextView;
    private Button goToMapButton;
    // ประกาศตัวแปรสำหรับเชื่อมโยงกับ UI (View) ต่างๆ ในหน้า layout
    private LocationItem currentLocation; // ตัวแปรสำหรับเก็บข้อมูลสถานที่ที่ได้รับมาจาก API ทั้งหมด

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        initializeViews(); // เรียก Method เพื่อเชื่อมตัวแปรเข้ากับ UI จากไฟล์ XML
        String locationId = getIntent().getStringExtra("LOCATION_ID");
        // รับค่า "LOCATION_ID" ที่ถูกส่งมาจากหน้าจอก่อนหน้าผ่าน Intent
        if (locationId != null && !locationId.isEmpty()) {
            fetchLocationData(locationId);
        } else {
            Toast.makeText(this, "ไม่พบรหัสสถานที่", Toast.LENGTH_SHORT).show();
        }
        // ตรวจสอบว่าได้รับ locationId มาหรือไม่ และไม่ใช่ค่าว่าง ถ้าไม่ได้รับ ID ให้แสดงข้อความแจ้งเตือน

        goToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigationInGoogleMaps();
            }
        });
    }
    // ตั้งค่าการทำงานเมื่อปุ่ม goToMapButton ถูกคลิก
    private void initializeViews() {
        buildingImageView = findViewById(R.id.buildingImageView);
        buildingNameTextView = findViewById(R.id.buildingNameTextView);
        floorTextView = findViewById(R.id.floorTextView);
        roomTextView = findViewById(R.id.roomTextView);
        roomTypeTextView = findViewById(R.id.roomTypeTextView);
        capacityTextView = findViewById(R.id.capacityTextView);
        goToMapButton = findViewById(R.id.goToMapButton);
    }
    // Methods สำหรับเชื่อมตัวแปรกับ UI ในไฟล์ layout (.XML)
    private void fetchLocationData(String locationId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LocationResponse> call = apiService.getLocationDetails(locationId);
    // เรียกใช้ API เพื่อดึงข้อมูลสถานที่จากฐานข้อมูลผ่าน location_id ที่รับมา
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                    // ตรวจสอบว่าการตอบกลับจาก API สำเร็จ ในข้อมูลเป็น "success"
                    currentLocation = response.body().getData(); // ตัวแปรสำหรับเก็บข้อมูลสถานที่ไว้

                    // นำข้อมูลที่ได้ไปแสดงผลบน TextView ต่างๆ
                    buildingNameTextView.setText(currentLocation.getBuildingName());
                    floorTextView.setText("ชั้น: " + currentLocation.getFloor());
                    roomTextView.setText("ห้อง: " + currentLocation.getRoomNumber());
                    roomTypeTextView.setText("ชนิดห้อง: " + currentLocation.getRoomType());
                    capacityTextView.setText("จำนวนที่นั่ง: " + String.valueOf(currentLocation.getCapacity()));

                    // ดึงชื่อไฟล์รูปภาพ
                    String imageName = currentLocation.getBuildingImage();
                    if (imageName != null && !imageName.isEmpty()) {
                        String imageUrl = ApiClient.BASE_URL + "images/" + imageName;
                        Glide.with(ShowData.this).load(imageUrl).into(buildingImageView);
                    }
                } else {
                    Toast.makeText(ShowData.this, "ไม่พบข้อมูลสถานที่", Toast.LENGTH_SHORT).show();
                }
            }
                    // ตรวจสอบว่ามีชื่อรูปภาพหรือไม่ ถ้าไม่มีให้แสดงข้อความแจ้งเตือน

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                Toast.makeText(ShowData.this, "การเชื่อมต่อล้มเหลว: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    // แสดงข้อความแจ้งเตือนข้อผิดพลาดในการเชื่อมต่อ
    /**
     * <<<<< เมธอดใหม่สำหรับเปิด Google Maps >>>>>
     */
    private void openNavigationInGoogleMaps() {
        if (currentLocation == null || currentLocation.getMapCoordinates() == null || currentLocation.getMapCoordinates().isEmpty()) {
            Toast.makeText(this, "ไม่มีข้อมูลพิกัดสำหรับสถานที่นี้", Toast.LENGTH_SHORT).show();
            return;
        }
        // ตรวจสอบว่ามีข้อมูลสถานที่ และมีข้อมูลพิกัดหรือไม่ ถ้าไม่มีให้แสดงข้อความแจ้งเตือน

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + currentLocation.getMapCoordinates());
        // สร้าง URI สำหรับส่งให้ Google Maps
        // รูปแบบคือ "google.navigation:q=lat,lng"
        // สร้าง Intent เพื่อเปิดแอป Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "ไม่พบแอปพลิเคชัน Google Maps", Toast.LENGTH_LONG).show();
        }
    }
    // สั่งให้เปิดแอป Google Maps ถ้าไม่มีให้แสดงข้อความแจ้งเตือน
    public static class LocationItem {
        @SerializedName("building_name") private String buildingName;
        @SerializedName("floor") private String floor;
        @SerializedName("room_number") private String roomNumber;
        @SerializedName("room_type") private String roomType;
        @SerializedName("capacity") private int capacity;
        @SerializedName("map_coordinates") private String mapCoordinates;
        @SerializedName("building_image") private String buildingImage;
        // Classses สำหรับเก็บข้อมูลสถานที่จาก API
        public String getBuildingName() { return buildingName; }
        public String getFloor() { return floor; }
        public String getRoomNumber() { return roomNumber; }
        public String getRoomType() { return roomType; }
        public int getCapacity() { return capacity; }
        public String getMapCoordinates() { return mapCoordinates; }
        public String getBuildingImage() { return buildingImage; }
    }
        // Getters: เมธอดสำหรับให้โค้ดส่วนอื่นเรียกใช้เพื่อดูค่าของตัวแปร (เนื่องจากตัวแปรเป็น private)
    public static class LocationResponse {
        @SerializedName("status") private String status;
        @SerializedName("data") private LocationItem data;

        // Getters
        public String getStatus() { return status; }
        public LocationItem getData() { return data; }
    }
}
// คลาสโมเดลข้อมูลสำหรับโครงสร้างการตอบกลับทั้งหมดจาก API ที่ส่งกลับมา

