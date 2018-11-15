package com.justcode.hxl.myapplication

import android.Manifest
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.justcode.hxl.localstorage.storage.InternalStorage.InternalStorage
import com.justcode.hxl.permission.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) {

                    } else {
                        Toast.makeText(this, "请给读写权限，否则可能会出现异常", Toast.LENGTH_SHORT).show()
                    }
                }
        btn_save_internal.setOnClickListener {
            InternalStorage.writeFileData(this, "internal.txt", et_internal_write.text.toString(), Context.MODE_PRIVATE)
        }
        btn_get_internal.setOnClickListener {
            tv_internal_read.text = InternalStorage.readFileData(this, "internal.txt")
        }
    }
}
