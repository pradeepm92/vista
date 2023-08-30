package com.health.vistacan.password.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.health.vistacan.R
import com.health.vistacan.databinding.ActivityPasswordBinding
import com.health.vistacan.password.viewmodel.PasswordViewModel

class PasswordActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var passwordbinding: ActivityPasswordBinding
    lateinit var passwordViewModel: PasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passwordbinding = DataBindingUtil.setContentView(this, R.layout.activity_password)
        doInitContent()
        passwordbinding.confirmpasswordEd.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    passwordbinding.passwordError.setText("")


                }else{
                    passwordbinding.conpasswordError.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun doInitContent() {
        passwordbinding.back.setOnClickListener(this)
        passwordbinding.submitBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            passwordbinding.back -> {
                onBackPressed()
            }
            passwordbinding.submitBtn -> {
                doCheckValidation()
            }
        }
    }

    private fun doCheckValidation() {
        val password = passwordbinding.passwordEd.text.toString()
        val confirm_pass = passwordbinding.confirmpasswordEd.text.toString()
        if (password.isEmpty()) {
            passwordbinding.passwordError.setText("Please Enter The Password")
            Log.e("password", "doCheckValidation: entry")

        } else if (password.length < 8) {
            passwordbinding.passwordError.setText("Password Must Contains 8 Digits")

        } else if (confirm_pass.isEmpty()) {
            passwordbinding.passwordError.setText("")
            passwordbinding.conpasswordError.setText("Please Enter The Password")

        } else if (!confirm_pass.equals(password)) {
            passwordbinding.conpasswordError.setText("Password Must be Same")

        }else{
            passwordbinding.passwordError.setText("")
            passwordbinding.conpasswordError.setText("")
        }
    }
}