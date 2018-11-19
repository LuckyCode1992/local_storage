package com.justcode.hxl.localstorage.sqlite.user;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.justcode.hxl.localstorage.sqlite.car.Car;
import com.justcode.hxl.localstorage.sqlite.car.CarDao;


@Database(entities = {User.class, Car.class}, version = 2, exportSchema = false)
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
                DB_NAME)
                //增加下面这一行
                //.addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build();
    }
   private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("ALTER TABLE 'user' ADD COLUMN 'gender' TEXT NOT NULL DEFAULT '男'");
        }
    };

    public abstract UserDao getUserDao();

    public abstract CarDao getCarDao();
}
