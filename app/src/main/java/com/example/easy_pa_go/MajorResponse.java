package com.example.easy_pa_go;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MajorResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Major> data;

    // Getter methods
    public String getStatus() {
        return status;
    }

    public List<Major> getData() {
        return data;
    }
}
