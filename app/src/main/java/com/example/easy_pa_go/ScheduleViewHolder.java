import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easy_pa_go.R;

/**
 * ViewHolder ทำหน้าที่เหมือน "กล่องเครื่องมือ" ที่เก็บ View ต่างๆ
 * (TextView, Button) ที่อยู่ในแม่พิมพ์ item_schedule.xml
 * เพื่อให้เราเรียกใช้งานได้อย่างรวดเร็ว
 */
public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
    // --- ทำให้ตัวแปรทั้งหมดเป็น private ตามหลัก OOP ---
    private TextView courseNameTextView;
    private TextView courseIdTextView;
    private TextView statusTextView;
    private TextView timeTextView;
    private TextView locationTextView;
    private Button mapButton;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        // --- ค้นหาและเก็บเครื่องมือทั้งหมดจากแม่พิมพ์ให้ครบ ---
        courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
        courseIdTextView = itemView.findViewById(R.id.courseIdTextView);
        statusTextView = itemView.findViewById(R.id.statusTextView);
        timeTextView = itemView.findViewById(R.id.timeTextView);
        locationTextView = itemView.findViewById(R.id.locationTextView);
        mapButton = itemView.findViewById(R.id.mapButton);
    }
}