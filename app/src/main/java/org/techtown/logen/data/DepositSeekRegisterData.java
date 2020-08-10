package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DepositSeekRegisterData {
    @SerializedName("PID")
    int PID;

    @SerializedName("method")
    int method;

    @SerializedName("year")
    int year;

    @SerializedName("month")
    int month;

    @SerializedName("day")
    int day;

    public DepositSeekRegisterData(int PID, int method, int year, int month, int day) {
        this.PID = PID;
        this.method = method;
        this.year = year;
        this.month = month;
        this.day = day;
    }

}