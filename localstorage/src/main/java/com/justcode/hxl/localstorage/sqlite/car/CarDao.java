package com.justcode.hxl.localstorage.sqlite.car;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.justcode.hxl.localstorage.sqlite.user.User;

import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM car")
    List<Car> getAllCar();

    @Query("SELECT * FROM car WHERE carownerNo=:carownerNo")
    List<Car> getCarbyCarownerNo(Long carownerNo);

    @Insert
    void insert(Car... cars);

    @Delete
    void delete(Car... cars);

    @Query("DELETE  FROM car")
    void deleteALL();



}
