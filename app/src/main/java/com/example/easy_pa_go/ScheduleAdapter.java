package com.example.easy_pa_go;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adapter ทำหน้าที่เป็น "ช่างฝีมือ" ที่นำข้อมูลตารางเรียน (ScheduleItem)
 * มาใส่ลงในแม่พิมพ์ (item_schedule.xml) เพื่อสร้างการ์ดแสดงผลแต่ละใบ
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleItem> scheduleList;

    // Constructor: รับรายการตารางเรียนเข้ามา
    public ScheduleAdapter(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    /**
     * เมธอดนี้จะถูกเรียกเมื่อ RecyclerView ต้องการสร้าง "แม่พิมพ์" (ViewHolder) ใหม่
     */
    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // สร้าง View จากไฟล์ layout item_schedule.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    /**
     * เมธอดนี้จะถูกเรียกเพื่อนำ "ข้อมูล" (จาก scheduleList) มาใส่ลงใน "แม่พิมพ์" (ViewHolder)
     * ตามตำแหน่ง (position) ของรายการนั้นๆ
     */
    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        // ดึงข้อมูลของวิชา ณ ตำแหน่งปัจจุบัน
        ScheduleItem currentItem = scheduleList.get(position);

        // นำข้อมูลไปใส่ใน TextView และ Button ต่างๆ
        holder.courseNameTextView.setText(currentItem.getCourseName());
        holder.courseIdTextView.setText(currentItem.getCourseId());
        holder.statusTextView.setText(currentItem.getStatus());
        holder.timeTextView.setText(currentItem.getStartTime() + " - " + currentItem.getEndTime());
        holder.locationTextView.setText(currentItem.getBuildingName() + ", ห้อง " + currentItem.getRoomNumber());
    }

    /**
     * เมธอดนี้จะบอก RecyclerView ว่ามีข้อมูลทั้งหมดกี่รายการที่ต้องแสดง
     */
    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    /**
     * ViewHolder ทำหน้าที่เหมือน "กล่องเครื่องมือ" ที่เก็บ View ต่างๆ
     * (TextView, Button) ที่อยู่ในแม่พิมพ์ item_schedule.xml
     * เพื่อให้เราเรียกใช้งานได้อย่างรวดเร็ว
     */
    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNameTextView;
        public TextView courseIdTextView;
        public TextView statusTextView;
        public TextView timeTextView;
        public TextView locationTextView;
        public Button mapButton;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            courseIdTextView = itemView.findViewById(R.id.courseIdTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            mapButton = itemView.findViewById(R.id.mapButton);
        }
    }
}
