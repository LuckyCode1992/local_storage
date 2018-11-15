package com.justcode.hxl.localstorage.sqlite.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE id=:id")
    User getUser(int id);

    @Query("SELECT * FROM user WHERE age=:age")
    List<User> getUsersByAge(int age);

    @Query("SELECT * FROM user WHERE age=:age LIMIT :max")
    List<User> getUsersByAge(int max, int... age);




    @Insert
    void insert(User... users);

    @Insert
    void insert(User user);

    @Insert
    void insert(List<User> userLists);
    //其他方法同理

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);




}
