package com.justcode.hxl.localstorage.sqlite.user;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.justcode.hxl.localstorage.sqlite.car.Car;
import com.justcode.hxl.localstorage.sqlite.car.CarDao;


@Database(entities = {User.class, Car.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "UserDatabase.db";
    private static volatile UserDatabase instance;


    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static UserDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME).build();
    }

    public abstract UserDao getUserDao();

    public abstract CarDao getCarDao();
}
