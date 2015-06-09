package com.example.krishna.badgecountapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krishna on 11/5/15.
 */
public class MyBean implements Parcelable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public MyBean() {
    }

    private MyBean(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<MyBean> CREATOR = new Parcelable.Creator<MyBean>() {
        public MyBean createFromParcel(Parcel source) {
            return new MyBean(source);
        }

        public MyBean[] newArray(int size) {
            return new MyBean[size];
        }
    };
}
