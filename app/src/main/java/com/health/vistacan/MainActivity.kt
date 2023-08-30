package com.health.vistacan

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.health.vistacan.databinding.ActivityMainBinding
import com.health.vistacan.splash.SplashActivity
import com.health.vistacan.utils.Utils.backArrowScreens
import com.zeoner.vistacan.Sharedpref.Constants
import com.zeoner.vistacan.Sharedpref.SharedPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    private var actionBar: ActionBar? = null
    private var destinationId: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        // Set up the action bar with a hamburger icon
        actionBar = supportActionBar
        if (actionBar != null) {
            actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
            actionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.addOnDestinationChangedListener(listener)
        navClickEvent()

        val isNotification = intent.getBooleanExtra("notification", false)
        if (isNotification) {
            val fragmentId = intent.getStringExtra("fragmentId", )
            val id = Integer.parseInt(fragmentId);
            when (id) {
                1 -> navController.navigate(R.id.patientListFragment)
                2 -> navController.navigate(R.id.patientFragment)
                3 -> navController.navigate(R.id.encounterListFragment)
                4 -> navController.navigate(R.id.soundRecordFragment)
                5 -> navController.navigate(R.id.diagnosisFragment)
                6 -> navController.navigate(R.id.feeCodeFragment)
                7-> navController.navigate(R.id.encounterDateandServiceFragment)
            }
        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
            if (!TextUtils.isEmpty(token)) {
                Log.d("token", "retrieve token successful : $token")
            } else {
                Log.e("token", "token should not be null...")
            }
        }.addOnFailureListener { e: Exception? -> }.addOnCanceledListener {}
            .addOnCompleteListener { task: Task<String> ->
                Log.e(
                    "token",
                    "This is the token : " + task.result
                )
            }


    }

    private fun navClickEvent() {
        // Handle item selection events
        navigationView.setNavigationItemSelectedListener { item -> // Handle navigation item clicks here

            when (item.itemId) {
                R.id.dashboard-> {
                    navigate(R.id.homeFragment)
                }
               R.id.patient -> {
                    navigate(R.id.patientFragment)
                }
               R.id.encounters -> {
                    navigate(R.id.encounterListFragment)
                }
               R.id.reports -> {
                    navigate(R.id.reportFragment)
                }
                R.id.logout -> {
                    logOut()
                }
                R.id.setting -> {
                    navigate(R.id.settingFragment)
                }

            }
            drawerLayout.closeDrawers() // Close the drawer after an item is selected
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if(destinationId!=null &&backArrowScreens().contains(destinationId)){
                navController.popBackStack()
            }else{
            drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigate(resId: Int) {
        navController.navigate(resId)
    }

    private val listener: NavController.OnDestinationChangedListener
        get() = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            destinationId=destination.id
            if (actionBar != null) {
                if(backArrowScreens().contains(destination.id)){
                    actionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                }else{
                    actionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)
                }
                    actionBar!!.title = destination.label
                }
            }

    private fun logOut() {
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
            .setTitle(resources.getString(R.string.logout))
            .setMessage(resources.getString(R.string.logout_content))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.logout)) { dialog, _ ->
                dialog.dismiss()
                SharedPref.sessionClear(this, Constants.LOGIN_SESSION)
                startActivity(Intent(this, SplashActivity::class.java))
                finishAffinity()
            }
            .show()
    }

}