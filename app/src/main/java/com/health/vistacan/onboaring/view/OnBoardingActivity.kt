package com.health.vistacan.onboaring.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.health.vistacan.MainActivity
import com.health.vistacan.R
import com.health.vistacan.databinding.ActivityOnboardingBinding
import com.health.vistacan.login.view.LoginActivity
import com.thecode.onboardingviewagerexamples.adapters.OnboardingViewPagerAdapter
import com.thecode.onboardingviewagerexamples.utils.Animatoo
import com.zeoner.vistacan.Sharedpref.Constants
import com.zeoner.vistacan.Sharedpref.SharedPref


class OnBoardingActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var textSkip: TextView

    private lateinit var binding: ActivityOnboardingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mViewPager = binding.viewPager
        mViewPager.adapter = OnboardingViewPagerAdapter(this, this)
        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()
        textSkip = findViewById(R.id.text_skip)
        textSkip.setOnClickListener {
            moveLogin()
        }
        val btnNextStep: Button = findViewById(R.id.btn_next_step)


        btnNextStep.setOnClickListener {
            if (getItem() > mViewPager.childCount) {
                moveLogin()
            } else {
                mViewPager.setCurrentItem(getItem() + 1, true)
            }
        }
    }

    private fun moveLogin(){
        SharedPref.save(this,Constants.COMMON_SESSION, Constants.SKIP_ONBOARD,true)
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        Animatoo.animateSlideLeft(this)
        finish()
    }


    private fun getItem(): Int {
        return mViewPager.currentItem
    }

}
