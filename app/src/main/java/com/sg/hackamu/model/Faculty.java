package com.sg.hackamu.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "Faculty")
public class Faculty implements Parcelable {
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo
    private String department;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "email")
    private String email;


    @ColumnInfo(name = "login")
    private boolean login;

    @ColumnInfo(name="uuid")
    private String uuid;

    public Faculty(String name, String department, String password, String email, long id, boolean login, String uuid) {
        this.name = name;
        this.department=department;
        this.password = password;
        this.id = id;
        this.email = email;
        this.login=login;
        this.uuid=uuid;
    }

    @Ignore
    public Faculty() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
    protected Faculty(Parcel in) {
        name = in.readString();
        department=in.readString();
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
        dest.writeString(department);
        dest.writeString(password);
        dest.writeLong(id);
        dest.writeString(email);
        dest.writeByte((byte) (login ? 0x01 : 0x00));
        dest.writeString(uuid);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Faculty> CREATOR = new Parcelable.Creator<Faculty>() {
        @Override
        public Faculty createFromParcel(Parcel in) {
            return new Faculty(in);
        }

        @Override
        public Faculty[] newArray(int size) {
            return new Faculty[size];
        }
    };
}
