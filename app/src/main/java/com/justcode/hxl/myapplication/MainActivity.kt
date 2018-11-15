package com.justcode.hxl.myapplication

import android.Manifest
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.justcode.hxl.localstorage.sharedpreferences.PropertyBySharedPref
import com.justcode.hxl.localstorage.storage.ExternalStorage.ExternalStorage
import com.justcode.hxl.localstorage.storage.InternalStorage.InternalStorage
import com.justcode.hxl.localstorage.storage.Storage
import com.justcode.hxl.permission.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var input by PropertyBySharedPref(default = "")
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

        btn_save_sp.setOnClickListener {
            input = et_sp_write.text.toString()
        }
        btn_get_sp.setOnClickListener {
            tv_sp_read.text = input
        }

        btn_save_external.setOnClickListener {
            ExternalStorage.write(et_exteranal_write.text.toString(), "externalStroage.txt")
        }
        btn_get_external.setOnClickListener {
            tv_external_read.text = ExternalStorage.read("externalStroage.txt")
        }

        btn_get_internal_size.setOnClickListener {
            tv_internal_size.text = ""+Storage.getInternalDataSize(this)+"字节"
        }
        btn_get_external_size.setOnClickListener {
            tv_external_size.text = " "+ Storage.getExternalDataSize(this)+"字节"
        }
        btn_get_storage_size.setOnClickListener {
            tv_storage_size.text = ""+Storage.getDataSize(this)+"字节"
        }

        btn_clear_Internal.setOnClickListener {
            Storage.clearInternalData(this)
        }
        btn_clear_External.setOnClickListener {
            Storage.clearExternalData(this)
        }
        btn_clear_storage.setOnClickListener {
            Storage.clearData(this)
        }
    }
}
