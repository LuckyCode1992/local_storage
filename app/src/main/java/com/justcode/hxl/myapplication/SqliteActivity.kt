package com.justcode.hxl.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.justcode.hxl.localstorage.sqlite.AndroidScheduler
import com.justcode.hxl.localstorage.sqlite.user.User
import com.justcode.hxl.localstorage.sqlite.user.UserDatabase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sqlite.*

class SqliteActivity : AppCompatActivity() {
    var i = 0

    val listSubscribe: MutableList<Disposable> = ArrayList()

    val userDao by lazy {
        val userDao = UserDatabase.getInstance(this)
                .userDao
        userDao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        btn_insert.setOnClickListener {

            val user = User()
            user.name = "lucky" + i++
            user.age = 18 + i++
            listSubscribe.add(
                    Observable.just(user)
                            .map {
                                return@map userDao.insert(user)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidScheduler.mainThread())
                            .subscribe {

                            }
            )


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
