package com.sg.hackamu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User implements Parcelable {
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="faculty_no")
    private String faculty_no;

    @ColumnInfo(name="enroll_no")
    private String enroll_no;

    @ColumnInfo(name = "email")
    private String email;


    @ColumnInfo(name = "login")
    private boolean login;

    @ColumnInfo(name="uuid")
    private String uuid;

    public User(String name, String faculty_no,String enroll_no,String password, String email,long id,boolean login,String uuid) {
        this.name = name;
        this.faculty_no=faculty_no;
        this.enroll_no=enroll_no;
        this.password = password;
        this.id = id;
        this.email = email;
        this.login=login;
        this.uuid=uuid;
    }

    @Ignore
    public User() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty_no() {
        return faculty_no;
    }

    public String getEnroll_no() {
        return enroll_no;
    }

    public void setFaculty_no(String faculty_no) {
        this.faculty_no = faculty_no;
    }

    public void setEnroll_no(String enroll_no) {
        this.enroll_no = enroll_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    @Ignore
    protected User(Parcel in) {
        name = in.readString();
        faculty_no=in.readString();
        enroll_no=in.readString();
        password = in.readString();
        id = in.readLong();
        email = in.readString();
        login = in.readByte() != 0x00;
        uuid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(faculty_no);
        dest.writeString(enroll_no);
        dest.writeString(password);
        dest.writeLong(id);
        dest.writeString(email);
        dest.writeByte((byte) (login ? 0x01 : 0x00));
        dest.writeString(uuid);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}