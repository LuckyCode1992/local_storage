package com.justcode.hxl.localstorage.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 通过代理从[SharedPreferences]获取或保存变量
 * var account by PropertyBySharedPref(default = "")
 */
class PropertyBySharedPref<T>(context: Context = context0!!, spName: String = "sharedpreferences_name", private val keyName: String = "", private val default: T) : ReadWriteProperty<Any, T> {

    /**
     * 记得初始化这个context
     */
    companion object {
        var context0:Context?=null
    }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val name = if (keyName.isEmpty()) property.name else keyName
        return with(sharedPreferences) {
            @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
            when (default) {
                is String -> getString(name, default) as T
                is Int -> getInt(name, default) as T
                is Float -> getFloat(name, default) as T
                is Boolean -> getBoolean(name, default) as T
                is Long -> getLong(name, default) as T
                else -> throw IllegalArgumentException("not support type")
            }
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val name = if (keyName.isEmpty()) property.name else keyName
        val editor = sharedPreferences.edit()
        with(editor) {
            @Suppress("IMPLICIT_CAST_TO_ANY")
            when (value) {
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Boolean -> putBoolean(name, value)
                is Long -> putLong(name, value)
                else -> throw IllegalArgumentException("not support type")
            }
        }
        editor.apply()
    }
}