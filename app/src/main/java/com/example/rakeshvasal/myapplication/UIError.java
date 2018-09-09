package com.example.rakeshvasal.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class UIError implements Parcelable {
    public String title = "Hey";
    public String message = "Error";
    public int action = Constants.DISPLAY_ERROR;

    public UIError(String title, String message, int action) {
        this.title = title;
        this.message = message;
        this.action = action;
    }

    public UIError getDefaultUIError() {
        return new UIError();
    }

    public UIError() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeInt(this.action);
    }

    protected UIError(Parcel in) {
        this.title = in.readString();
        this.message = in.readString();
        this.action = in.readInt();
    }

    public static final Creator<UIError> CREATOR = new Creator<UIError>() {
        @Override
        public UIError createFromParcel(Parcel source) {
            return new UIError(source);
        }

        @Override
        public UIError[] newArray(int size) {
            return new UIError[size];
        }
    };
}
