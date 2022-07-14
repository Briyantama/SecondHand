package com.binar.secondhand.helper

import android.content.Context
import android.content.SharedPreferences

class Sharedpref(context: Context) {

    private val info = "SP_INFO"
    private val sharedPref = context.getSharedPreferences(info, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun putBooleanKey(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBooleanKey(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun putStringKey(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getStringKey(key: String): String? {
        return sharedPref.getString(key, "")
    }

    fun clear(){
        editor.clear().apply()
    }
}