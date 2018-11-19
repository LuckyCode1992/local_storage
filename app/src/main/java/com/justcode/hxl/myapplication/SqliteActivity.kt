package com.justcode.hxl.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.localstorage.sqlite.AndroidScheduler
import com.justcode.hxl.localstorage.sqlite.car.Car
import com.justcode.hxl.localstorage.sqlite.user.User
import com.justcode.hxl.localstorage.sqlite.user.UserDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sqlite.*
import java.util.*

class SqliteActivity : AppCompatActivity() {


    val listSubscribe: MutableList<Disposable> = ArrayList()

    val userDao by lazy {
        val userDao = UserDatabase.getInstance(this)
                .userDao
        userDao
    }
    val carDao by lazy {
        val carDao = UserDatabase.getInstance(this).carDao
        carDao
    }
    var isInsertUser = false
    var isInsertCar = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)


        btn_insert_car.setOnClickListener {
            if (isInsertCar) {
                return@setOnClickListener
            }
            val car1 = Car()
            val car2 = Car()
            val car3 = Car()
            val car4 = Car()

            car1.car_id = 101
            car2.car_id = 102
            car3.car_id = 103
            car4.car_id = 104
            car1.name = "宝马"
            car2.name = "奔驰"
            car3.name = "奥迪"
            car4.name = "法拉利"
            car1.money = 200.0
            car2.money = 400.0
            car3.money = 800.0
            car4.money = 5500.0

            car1.carownerNo = 101
            car2.carownerNo = 101
            car3.carownerNo = 101
            car4.carownerNo = 102


            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map carDao.insert(car1, car2, car3, car4)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe { }
            )
            isInsertCar = true

        }

        btn_insert.setOnClickListener {

            if (isInsertUser) {
                return@setOnClickListener
            }
            val user1 = User()
            val user2 = User()
            val user3 = User()
            user1.name = "hello"
            user2.name = "mark"
            user3.name = "ding"
            user1.age = 18
            user2.age = 28
            user3.age = 38

            user1.carownerNo = 0
            user2.carownerNo = 101
            user3.carownerNo = 102


            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map userDao.insert(user1, user2, user3)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {

                            }
            )

            isInsertUser = true
        }
        btn_update.setOnClickListener {
            val user = User()
            user.id = 35
            user.age = 100
            user.name = "hello 我是更新"
            listSubscribe.add(
                    Observable.just(user)
                            .map {
                                return@map userDao.update(user)
                            }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {

                            }

            )


        }
        btn_querry_all.setOnClickListener {
            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map userDao.allUser
                            }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = it.toString()
                            }
            )


        }
        btn_querry_all_car.setOnClickListener {
            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map carDao.allCar
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = it.toString()
                            }
            )
        }
        btn_querry_age.setOnClickListener {
            listSubscribe.add(
                    Observable.just(19)

                            .map {
                                return@map userDao.getUsersByAge(it)
                            }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = it.toString()
                            }
            )
        }
        btn_querry_age1_age2.setOnClickListener {
            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map userDao.getUserAgeBya1a2(19, 25)
                            }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = it.toString()
                            }
            )

        }
        btn_querry_name.setOnClickListener {
            listSubscribe.add(
                    Observable.just("lucky4")
                            .map {
                                return@map userDao.getUsersByName(it)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = it.toString()
                            }
            )

        }
        btn_del.setOnClickListener {
            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map userDao.deleteALL()
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = ""
                            }

            )
        }
        btn_del_car.setOnClickListener {
            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map carDao.deleteALL()
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = ""
                            }
            )

        }
        btn_querry_mark_car.setOnClickListener {
            listSubscribe.add(
                    Observable.just(0)
                            .map {
                                return@map carDao.getCarbyCarownerNo(userDao.getUsersByName("mark")[0].carownerNo)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {
                                tv_querry.text = it.toString()
                            }
            )
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        if (listSubscribe.size != 0) {
            listSubscribe.forEach {
                if (!it.isDisposed) {
                    it.dispose()
                }
            }
        }
    }
}
