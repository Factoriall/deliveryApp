package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DepositSeekRegisterData {
    @SerializedName("userCode")
    int userCode;

    @SerializedName("method")
    int method;

    @SerializedName("year")
    int year;

    @SerializedName("month")
    int month;

    @SerializedName("day")
    int day;

    public DepositSeekRegisterData(int userCode, int method, int year, int month, int day) {
        this.userCode = userCode;
        this.method = method;
        this.year = year;
        this.month = month;
        this.day = day;
    }

}