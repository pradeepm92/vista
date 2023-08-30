package com.health.vistacan.otp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.health.vistacan.R
import com.health.vistacan.databinding.ActivityOtpactivityBinding
import com.health.vistacan.otp.viewmodel.OTPViewModel
import com.health.vistacan.password.view.PasswordActivity
import com.thecode.onboardingviewagerexamples.utils.Animatoo

class OTPActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var otpViewModel: OTPViewModel
    lateinit var otpactivityBinding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otpactivityBinding=DataBindingUtil.setContentView(this,R.layout.activity_otpactivity)
        otpactivityBinding.back.setOnClickListener(this)
        otpactivityBinding.submitBtn.setOnClickListener(this)
        otpactivityBinding.resendotp.setOnClickListener(this)
    }
    private fun startTimer() {
        otpactivityBinding.submitBtn.isClickable = false
        otpactivityBinding.resendotp.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.md_red_900
            )
        )
        object : CountDownTimer(10000, 1000) {
            var secondsLeft = 0
            override fun onTick(ms: Long) {
                if (Math.round(ms.toFloat() / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round(ms.toFloat() / 1000.0f)
                    otpactivityBinding.resendotp.text =
                        resources.getString(R.string.resent_otp) + "( $secondsLeft )"
                }
            }

            override fun onFinish() {
                otpactivityBinding.submitBtn.isClickable = true
                otpactivityBinding.resendotp.text = resources.getString(R.string.resent_otp)
                otpactivityBinding.resendotp.setTextColor(
                    ContextCompat.getColor(
                        this@OTPActivity,
                        R.color.dark_blue
                    )
                )
            }
        }.start()
    }

    override fun onClick(v: View?) {
       when(v){
           otpactivityBinding.back->{
               onBackPressed()
           }
           otpactivityBinding.resendotp->{
               startTimer()
           }
           otpactivityBinding.submitBtn->{
               if ( otpactivityBinding.PinView.text!!.isEmpty()||(!otpactivityBinding.PinView.length().equals(6)) ){
                   Toast.makeText(this,"Please Enter The OTP", Toast.LENGTH_SHORT).show()

               }else{
//                   doCallApi()
                   val Intent = Intent(applicationContext,  PasswordActivity::class.java)
                   startActivity(Intent)
                   Animatoo.animateSlideLeft(this)
               }
           }
       }
    }
}