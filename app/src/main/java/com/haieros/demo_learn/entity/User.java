package com.haieros.demo_learn.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kang on 2018/1/15.
 */

public class User implements Parcelable {

    private String name;
    private String age;

    protected User(Parcel in) {
        name = in.readString();
        age = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
    }
}
