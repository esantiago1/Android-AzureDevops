package com.demo.demosomosoh;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {

    private String email;
    private String lastName;
    private String age;
    private String birthday;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.lastName);
        dest.writeString(this.age);
        dest.writeString(this.birthday);
    }

    public Model() {
    }

    protected Model(Parcel in) {
        this.email = in.readString();
        this.lastName = in.readString();
        this.age = in.readString();
        this.birthday = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
