package org.techtown.logen;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable{

    int userCode;
    String username;
    int teamCode;
    int grade;

    public int getUserCode(){
        return userCode;
    }

    public String getUsername(){
        return username;
    }

    public int getTeamCode(){
        return teamCode;
    }

    public int getGrade(){
        return grade;
    }

    public SimpleData(int userCode, String username, int teamCode, int grade){
        this.userCode = userCode;
        this.username = username;
        this.teamCode = teamCode;
        this.grade = grade;
    }

    public SimpleData(Parcel src){
        userCode = src.readInt();
        username = src.readString();
        teamCode = src.readInt();
        grade = src.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Object createFromParcel(Parcel parcel) {
            return new SimpleData(parcel);
        }

        @Override
        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userCode);
        parcel.writeString(username);
        parcel.writeInt(teamCode);
        parcel.writeInt(grade);
    }
}
