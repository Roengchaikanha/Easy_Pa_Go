package com.example.easy_pa_go;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<UserInterface.ScheduleItem> scheduleList;

    public ScheduleAdapter(List<UserInterface.ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        UserInterface.ScheduleItem currentItem = scheduleList.get(position);

        // --- ส่วนนี้เหมือนเดิม ---
        holder.dayTextView.setText(currentItem.getDay() != null ? currentItem.getDay() : "-");
        holder.courseIdTextView.setText(currentItem.getCourseId() != null ? currentItem.getCourseId() : "N/A");

        String time = (currentItem.getStartTime() != null ? currentItem.getStartTime() : "00:00") +
                " - " +
                (currentItem.getEndTime() != null ? currentItem.getEndTime() : "00:00");
        holder.timeTextView.setText(time);

        holder.statusTextView.setText("สถานะ: " + (currentItem.getStatus() != null ? currentItem.getStatus() : "N/A"));

        // <<<<< ส่วนที่เพิ่มเข้ามาใหม่ทั้งหมด >>>>>
        // ตั้งค่าให้ไอคอนแผนที่ทำงานเมื่อถูกกด
        holder.mapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. ดึง location_id ของรายการนี้ออกมา
                String locationId = currentItem.getLocationId();

                // 2. สร้าง "ตั๋วเดินทาง" (Intent) เพื่อไปที่หน้า ShowData
                Context context = v.getContext();
                Intent intent = new Intent(context, ShowData.class);

                // 3. แนบ "สัมภาระ" (location_id) ไปกับตั๋ว
                // เราจะใช้ "LOCATION_ID" เป็นป้ายชื่อสำหรับสัมภาระชิ้นนี้
                intent.putExtra("LOCATION_ID", locationId);

                // 4. เริ่มการเดินทาง
                context.startActivity(intent);
            }
        });
        // --- จบส่วนที่เพิ่ม ---
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTextView;
        public TextView courseIdTextView;
        public TextView timeTextView;
        public TextView statusTextView;
        public ImageButton mapImageButton;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            courseIdTextView = itemView.findViewById(R.id.courseIdTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            mapImageButton = itemView.findViewById(R.id.mapImageButton);
        }
    }
}