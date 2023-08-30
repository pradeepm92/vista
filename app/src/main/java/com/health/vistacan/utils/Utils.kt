package com.health.vistacan.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.health.vistacan.R
import kotlinx.coroutines.*

object Utils {

    fun getBaseUrl(context: Context): String? {
        return "https://seemymd.ca/vscanapi"
    }
    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }

    fun backArrowScreens(): ArrayList<Int> {
        var screens: ArrayList<Int> = ArrayList()
        screens.add(R.id.soundRecordFragment)
        screens.add(R.id.diagnosisFragment)
        screens.add(R.id.feeCodeFragment)
        screens.add(R.id.encounterPatientEntryFragment)
        screens.add(R.id.encounterDateandServiceFragment)
        screens.add(R.id.patientPictureFragment)
        screens.add(R.id.editPatientListFragment)
        screens.add(R.id.aboutFragment)
        screens.add(R.id.profileFragment)
        screens.add(R.id.resetPasswordFragment)

        return screens
    }

}