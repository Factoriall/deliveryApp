package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

public class DepositSeekRegisterResponse {
    @SerializedName("responseCode")
    private int responseCode;

    @SerializedName("isExist")
    private boolean isExist;

    @SerializedName("isLeaderAssigned")
    private boolean isLeaderAssigned;

    @SerializedName("isDepartmentAssigned")
    private boolean isDepartmentAssigned;

    @SerializedName("delivery")
    private int delivery;

    @SerializedName("prepay")
    private int prepay;

    @SerializedName("remark")
    private String remark;

    public boolean getIsExist() {
        return isExist;
    }

    public boolean getIsLeaderAssigned() {
        return isLeaderAssigned;
    }

    public boolean getIsDepartmentAssigned() {
        return isDepartmentAssigned;
    }

    public int getDelivery() { return delivery; }

    public int getPrepay() {
        return prepay;
    }

    public String getRemark() { return remark; }
}