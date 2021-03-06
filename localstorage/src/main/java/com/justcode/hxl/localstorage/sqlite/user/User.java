package com.justcode.hxl.localstorage.sqlite.user;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private Long id;
    private int age;
    private String name;
    private Long carownerNo;
//    @ColumnInfo(name = "gender")
    private String gender;
    private int home_size;

    public int getHome_size() {
        return home_size;
    }

    public void setHome_size(int home_size) {
        this.home_size = home_size;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getCarownerNo() {
        return carownerNo;
    }

    public void setCarownerNo(Long carownerNo) {
        this.carownerNo = carownerNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", carownerNo=" + carownerNo +
                ", gender='" + gender + '\'' +
                ", home_size=" + home_size +
                '}';
    }
}
