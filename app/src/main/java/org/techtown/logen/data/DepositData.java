package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DepositData {
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

    @SerializedName("delivery")
    int delivery;

    @SerializedName("prepay")
    int prepay;

    @SerializedName("remark")
    String remark;

    public DepositData(int userCode, int method, int year,
                       int month, int day, int delivery, int prepay, String remark) {
        this.userCode = userCode;
        this.method = method;
        this.year = year;
        this.month = month;
        this.day = day;
        this.delivery = delivery;
        this.prepay = prepay;
        this.remark = remark;
    }
}
