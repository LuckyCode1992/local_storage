package com.justcode.hxl.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.localstorage.sqlite.AndroidScheduler
import com.justcode.hxl.localstorage.sqlite.user.User
import com.justcode.hxl.localstorage.sqlite.user.UserDatabase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sqlite.*

class SqliteActivity : AppCompatActivity() {
    var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        btn_insert.setOnClickListener {

            val user = User()
            user.name = "lucky" + i++
            user.age = 18 + i++
            UserDatabase.getInstance(this).userDao


        }

        btn_querry_all.setOnClickListener {

            Observable.just(0)
                    .map {
                      return@map  UserDatabase.getInstance(this)
                                .userDao
                                .getUser(it)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidScheduler.mainThread())
                    .subscribe {

                    }

        }
        btn_querry_age.setOnClickListener {

        }
        btn_querry_name.setOnClickListener {

        }

    }
}
