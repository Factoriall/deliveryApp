package org.techtown.logen.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("responseCode")
    private int responseCode;

    @SerializedName("userCode")
    private int userCode;

    @SerializedName("teamCode")
    private int teamCode;

    @SerializedName("grade")
    private int grade;

    @SerializedName("username")
    private String username;

    public int getResponseCode() {
        return responseCode;
    }

    public int getUserCode() {
        return userCode;
    }

    public int getTeamCode() {
        return teamCode;
    }

    public int getGrade() { return grade; }

    public String getUsername() {
        return username;
    }
}