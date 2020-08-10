package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DepositEditData {
    @SerializedName("PID")
    int PID;

    @SerializedName("method")
    int method;

    @SerializedName("date")
    Date date;

    @SerializedName("delivery")
    int delivery;

    @SerializedName("prepay")
    int prepay;

    @SerializedName("remark")
    String remark;

    public DepositEditData(int PID, int method, Date date, int delivery, int prepay, String remark) {
        this.PID = PID;
        this.method = method;
        this.date = date;
        this.delivery = delivery;
        this.prepay = prepay;
        this.remark = remark;
    }
}
