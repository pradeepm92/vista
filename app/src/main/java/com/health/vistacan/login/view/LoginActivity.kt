package com.health.vistacan.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.health.vistacan.MainActivity
import com.health.vistacan.R
import com.health.vistacan.databinding.ActivityLoginBinding
import com.health.vistacan.forgotpassword.ForgotActivity
import com.health.vistacan.login.CustomArrayAdapter
import com.health.vistacan.login.viewmodel.LoginViewModel
import com.zeoner.vistacan.Sharedpref.Constants
import com.zeoner.vistacan.Sharedpref.SharedPref

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var loginBinding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    private var selectedSpinnerIndex: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        doInitContent()
        onTimeValidate()

    }

    private fun onTimeValidate() {
        loginBinding.username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    loginBinding.spinnererror.setText("")

                } else {
                    loginBinding.usernameError.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })
        loginBinding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val password = s.toString()

                if (password.isEmpty()) {
                    loginBinding.usernameError.setText("")
                    loginBinding.spinnererror.setText("")

                } else {
                    loginBinding.passwordError.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })
    }

    private fun doInitContent() {
        loginBinding.loginBtn.setOnClickListener(this)
        loginBinding.ForgetPassword.setOnClickListener(this)
        val items = listOf("Clinics", "Seemymd.ca", "Vemr.ca", "Bookmymd.ca")
        val adapter = CustomArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        loginBinding.spinner.adapter = adapter
    }


    override fun onClick(v: View?) {
        when (v) {
            loginBinding.loginBtn -> {
                doCheckValidation()
            }
            loginBinding.ForgetPassword -> {
                val Intent = Intent(this, ForgotActivity::class.java)
                startActivity(Intent)
            }
        }
    }

    private fun doCheckValidation() {
        val username = loginBinding.username.text.toString()
        val password = loginBinding.password.text.toString()



        if (loginBinding.spinner.getSelectedItem().toString()
                .equals("Clinics", ignoreCase = true)
        ) {

            loginBinding.spinnererror.setText("Please Select The Clinic")


        } else if (username.isEmpty()) {
            loginBinding.spinnererror.setText("")
            loginBinding.usernameError.setText("Please Enter The UserName")

        } else if (password.isEmpty()) {
            loginBinding.usernameError.setText("")
            loginBinding.passwordError.setText("Please Enter The Password")

        } else {
            doMoveHomeScreen()
//            doicallApi()
            Toast.makeText(this, "validation over", Toast.LENGTH_SHORT).show()
        }
    }

    private fun doMoveHomeScreen() {
        SharedPref.save(applicationContext, Constants.COMMON_SESSION, Constants.CountryCode, "91")
        SharedPref.save(applicationContext, Constants.LOGIN_SESSION, Constants.IS_LOGIN, true)
        val i = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

}