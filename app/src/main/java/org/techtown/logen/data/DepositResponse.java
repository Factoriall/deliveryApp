package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

public class DepositResponse {
    @SerializedName("responseCode")
    private int responseCode;

    public int getResponseCode() {
        return responseCode;
    }
}
