package org.techtown.logen.data;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("UID")
    String UID;

    @SerializedName("pwd")
    String pwd;

    public LoginData(String UID, String pwd) {
        this.UID = UID;
        this.pwd = pwd;
    }
}