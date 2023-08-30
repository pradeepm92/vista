package com.zeoner.vistacan.Sharedpref

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.lang.ref.WeakReference

object SharedPref {
     fun doGetSecureSharedPreference(context: Context,sessionName:String): SharedPreferences? {

        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return  EncryptedSharedPreferences.create(
            context,
            sessionName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun save(context: Context?, sessionName:String,key: String, value: Any) {
        val contextWeakReference = WeakReference(context)
        val context = contextWeakReference.get()
        if (context != null) {
            val prefs = doGetSecureSharedPreference(context,sessionName)
            val editor = prefs!!.edit()
            when (value) {
                is Int -> editor.putInt(key, value)
                is String -> editor.putString(key, value.toString())
                is Boolean -> editor.putBoolean(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                is Double -> editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            }
            editor.apply()
        }
    }

    fun get(context: Context, sessionName:String, key: String, defaultValue: Any): Any? {
        val contextWeakReference = WeakReference(context)
        val context = contextWeakReference.get()
        if (context != null) {
            val prefs = doGetSecureSharedPreference(context,sessionName)
            try {
                when (defaultValue) {
                    is String -> return prefs!!.getString(key, defaultValue.toString())
                    is Int -> return prefs!!.getInt(key, defaultValue)
                    is Boolean -> return prefs!!.getBoolean(key, defaultValue)
                    is Long -> return prefs!!.getLong(key, defaultValue)
                    is Float -> return prefs!!.getFloat(key, defaultValue)
                    is Double -> return java.lang.Double.longBitsToDouble(
                        prefs!!.getLong(
                            key,
                            java.lang.Double.doubleToLongBits(defaultValue)
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("Exception: ", e.message.toString())
                return defaultValue
            }
        }
        return defaultValue
    }

    fun sessionClear(context: Context,sessionName:String) {
        val contextWeakReference = WeakReference(context)
        val context=contextWeakReference.get()
        if (context != null) {
            val prefs = doGetSecureSharedPreference(context,sessionName)
            val editor = prefs!!.edit()
            editor.clear()
            editor.apply()
        }
    }

    fun hasKey(context: Context, sessionName:String, key: String): Boolean {
        val contextWeakReference = WeakReference(context)
        val context=contextWeakReference.get()
        if (context != null) {
            val prefs = doGetSecureSharedPreference(context,sessionName)
            return prefs!!.contains(key)
        }
        return false
    }
}