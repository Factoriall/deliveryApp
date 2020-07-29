package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("userUID")
    String userUID;

    @SerializedName("userPwd")
    String userPwd;

    public LoginData(String userUID, String userPwd) {
        this.userUID = userUID;
        this.userPwd = userPwd;
    }
}