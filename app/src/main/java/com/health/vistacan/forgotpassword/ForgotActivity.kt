package com.health.vistacan.forgotpassword

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.health.vistacan.R
import com.health.vistacan.databinding.ActivityForgotBinding
import com.health.vistacan.login.CustomArrayAdapter
import com.health.vistacan.login.viewmodel.LoginViewModel
import com.health.vistacan.otp.view.OTPActivity

class ForgotActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var forgotbinding: ActivityForgotBinding
    lateinit var loginViewModel: LoginViewModel
    var email_isselected = true
    var phone_isselected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotbinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot)
        doInitContent()
        validate()
    }

    private fun doInitContent() {
        forgotbinding.mobileBtn.setOnClickListener(this)
        forgotbinding.emailBtn.setOnClickListener(this)
        forgotbinding.submitBtn.setOnClickListener(this)
        val items = listOf("Clinics", "Seemymd.ca", "Vemr.ca", "Bookmymd.ca")
        val adapter = CustomArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        forgotbinding.spinner.adapter = adapter
    }


    fun validate() {

            forgotbinding.email.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Does nothing intentionally
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Does nothing intentionally
                }

                @SuppressLint("SuspiciousIndentation")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email = s.toString()
                    val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                    if (email.isEmpty()){
                        forgotbinding.spinnererror.setText("")
                        forgotbinding.emailerror.setText("Please Enter The Email")
                    }else
                    if (!isValid) {
                        forgotbinding.emailerror.setText(" Invalid Email")
                    } else {
                        forgotbinding.emailerror.setText("")
                    }


                }
            })

            forgotbinding.mobile.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Does nothing intentionally
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Does nothing intentionally
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val mobile = s.toString()

                    if (mobile.isEmpty()) {
                        forgotbinding.spinnererror.setText("")
                        forgotbinding.emailerror.setText("")
                        forgotbinding.mobileerror.setText("Please Enter The Mobile Number")
                    } else if (mobile.length < 10) {
                        forgotbinding.mobileerror.setText("MobileNumber Contains Atleast 10 Digits")
                    } else {
                        forgotbinding.emailerror.setText("")
                        forgotbinding.mobileerror.setText("")

                    }

                }
            })


    }

    fun docheckValidation() {
        val email = forgotbinding.email.text.toString()
        val mobile = forgotbinding.mobile.text.toString()
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (email_isselected.equals(true)) {
            if (forgotbinding.spinner.getSelectedItem().toString()
                    .equals("Clinics", ignoreCase = true)
            ) {
                forgotbinding.spinnererror.setText("Please Select The Clinic")
            }else if (email.isEmpty()) {
                    forgotbinding.spinnererror.setText("")
                    forgotbinding.emailerror.setText("Please Enter The Email")
                }else if (!isValid) {
                forgotbinding.emailerror.setText(" Invalid Email")
            } else {
                forgotbinding.emailerror.setText("")
            }
        }

        if (phone_isselected.equals(true)) {
            if (forgotbinding.spinner.getSelectedItem().toString()
                    .equals("Clinics", ignoreCase = true)
            ) {
                forgotbinding.spinnererror.setText("Please Select The Clinic")
            }else if (mobile.isEmpty()) {
                forgotbinding.spinnererror.setText("")
                forgotbinding.mobileerror.setText("please Enter The Mobile Number")

            }else if (mobile.length < 10) {
                forgotbinding.mobileerror.setText("Mobile Contains Atleast 10Digits")
            } else {
                forgotbinding.spinnererror.setText("")
                forgotbinding.mobileerror.setText("")
                val Intent=Intent(this,OTPActivity::class.java)
                startActivity(Intent)

            }
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            forgotbinding.emailBtn -> {
                email_isselected = true
                phone_isselected = false
                if (email_isselected.equals(true)) {
                    forgotbinding.mobileLayout.visibility = View.GONE
                    forgotbinding.emailLayout.visibility = View.VISIBLE
                    forgotbinding.emailBtn.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            this,
                            R.color.md_deep_purple_600
                        )
                    )
                    forgotbinding.mobileBtn.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            this,
                            R.color.md_deep_purple_200
                        )
                    )
                }

            }
            forgotbinding.mobileBtn -> {
                email_isselected = false
                phone_isselected = true
                if (phone_isselected.equals(true)) {
                    forgotbinding.mobileLayout.visibility = View.VISIBLE
                    forgotbinding.emailLayout.visibility = View.GONE
                    forgotbinding.mobileBtn.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            this,
                            R.color.md_deep_purple_600
                        )
                    )
                    forgotbinding.emailBtn.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                            this,
                            R.color.md_deep_purple_200
                        )
                    )
                }
            }
            forgotbinding.submitBtn -> {
                docheckValidation()
            }
        }
    }
}