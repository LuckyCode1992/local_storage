package com.justcode.hxl.localstorage.sqlite.car;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.justcode.hxl.localstorage.sqlite.user.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Car {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private double money;
    private Long car_id;
    private Long carownerNo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", car_id=" + car_id +
                ", carownerNo=" + carownerNo +
                '}';
    }
}
