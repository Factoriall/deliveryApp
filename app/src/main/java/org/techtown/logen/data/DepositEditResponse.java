package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

public class DepositEditResponse {
    @SerializedName("responseCode")
    private int responseCode;

    public int getResponseCode() {
        return responseCode;
    }
}
