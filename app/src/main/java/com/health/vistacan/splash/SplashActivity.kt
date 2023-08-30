package com.health.vistacan.splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.health.vistacan.MainActivity
import com.health.vistacan.R
import com.health.vistacan.databinding.ActivitySplashBinding
import com.health.vistacan.forceupdate.ForceUpdateDialog
import com.health.vistacan.login.view.LoginActivity
import com.health.vistacan.onboaring.view.OnBoardingActivity
import com.health.vistacan.splash.viewmodel.SplashViewModel
import com.health.vistacan.utils.Resource
import com.zeoner.vistacan.Sharedpref.Constants
import com.zeoner.vistacan.Sharedpref.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    var PlayStoreVersionName: String = ""
    var DBVersionName: String = ""
    lateinit var splashBinding: ActivitySplashBinding
    lateinit var progress:CircularProgressIndicator
    private val splashViewModel:SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val backgroundImage: ImageView = findViewById(R.id.imageView)
         progress = findViewById(R.id.progressbar)

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_anim)
        backgroundImage.startAnimation(slideAnimation)

        splashViewModel.todo.observeForever {
            when (it) {
                is Resource.Success -> {
                    loader(false)
                    it.data?.let { newsResponse ->
                      Log.e("data",it.data.token)
                    }
                    doGenerateDelay()
                }
                is Resource.Error -> {
                    loader(false)
                    it.message?.let { message ->
                        Log.e("Errpr", "Error: $message")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    loader(true)
                }
            }

        }


//
//        if (!BuildConfig.DEBUG){
//            doGetPackageName()
////                doCallForceUpdate()
//        }else{
//            doGenerateDelay()
//        }

    }

    fun loader(load:Boolean){
        if(load){
            progress.visibility=View.VISIBLE
        }else{
            progress.visibility=View.GONE
        }
    }
    fun VersionCheck(){

        var result = VersionUtils.compare(PlayStoreVersionName, DBVersionName)
        if (result == 0) {

            doGenerateDelay()
            Log.e("check", "VersionCheck1: 111")
        } else if (result < 0) {
//           Version 1 is lower than version 2
            Log.e("check1", "VersionCheck1: 111")
            doshowDialog();

        } else {
//           Version 1 is higher than version 2
            doGenerateDelay()
            Log.e("check2", "VersionCheck1: 111")
        }
    }

    private fun doGenerateDelay() {

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000L)
            moveNextScreen()
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

    private fun moveNextScreen() {
        val SKIP_ONBOARD: Boolean = SharedPref.get(this,Constants.COMMON_SESSION, Constants.SKIP_ONBOARD, false) as Boolean
        if (SKIP_ONBOARD) {
            val IS_LOGIN: Boolean = SharedPref.get(this, Constants.LOGIN_SESSION,Constants.IS_LOGIN, false) as Boolean
            if (IS_LOGIN) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                SharedPref.save(this, Constants.LOGIN_SESSION,Constants.IS_LOGIN, true)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    object VersionUtils {
        fun compare(version1: String, version2: String): Int {

            val vals1 = version1.split("\\.".toRegex()).toTypedArray()
            val vals2 = version2.split("\\.".toRegex()).toTypedArray()
            var i = 0
            // set index to first non-equal ordinal or length of shortest version string
            while (i < vals1.size && i < vals2.size && vals1[i] == vals2[i]) {
                i++
            }
            // compare first non-equal ordinal number
            if (i < vals1.size && i < vals2.size) {
                val diff = Integer.valueOf(vals1[i]).compareTo(
                    Integer.valueOf(
                        vals2[i]
                    )
                )
                return Integer.signum(diff)
            }
            // the strings are equal or one string is a substring of the other
            // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
            return Integer.signum(vals1.size - vals2.size)
        }
    }
    private fun doGetPackageName() {
        val pm = applicationContext.packageManager
        val pkgName = applicationContext.packageName

        Log.e("pkgName", pkgName )
        Log.e("pm", pm.toString())
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0)
            PlayStoreVersionName = pkgInfo.versionName
            Log.e("version_name>>>>", PlayStoreVersionName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun doshowDialog() {
        try {
            val picker: ForceUpdateDialog = ForceUpdateDialog.newInstance("New Version Available")
            picker.setListener(object : ForceUpdateDialog.onDialogAction {
                override fun doSendPlayStore() {
                    val appPackageName =
                        packageName // getPackageName() from Context or Activity object
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                            )
                        )
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                            )
                        )
                    }
                }
            })
            picker.show(supportFragmentManager, "New Version Available")
        } catch (e: Exception) {
            Log.i("Error>>", e.toString())
        }
}
}