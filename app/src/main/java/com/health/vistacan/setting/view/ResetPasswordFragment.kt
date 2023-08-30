package com.health.vistacan.setting.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentResetPasswordBinding
import com.health.vistacan.setting.viewmodel.SettingViewModel

class ResetPasswordFragment : Fragment(), View.OnClickListener {
    lateinit var resetpasswordBinding: FragmentResetPasswordBinding
    lateinit var settingViewModel: SettingViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        resetpasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)
        navController = NavHostFragment.findNavController(this)
        doInitContent()
        onTimeValidate()
        return resetpasswordBinding.root

    }

    private fun onTimeValidate() {
        resetpasswordBinding.newpasswordEd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    resetpasswordBinding.currentPasswordError.setText("")


                } else {
                    resetpasswordBinding.newpasswordError.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        resetpasswordBinding.confirmpasswordEd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    resetpasswordBinding.newpasswordError.setText("")


                } else {
                    resetpasswordBinding.conpasswordError.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun doInitContent() {
        resetpasswordBinding.submitBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            resetpasswordBinding.submitBtn -> {
                doCheckValidation()
            }
        }
    }

    private fun doCheckValidation() {
        val currentpassword = resetpasswordBinding.currentPassEd.text.toString()
        val newpassword = resetpasswordBinding.newpasswordEd.text.toString()
        val confirm_pass = resetpasswordBinding.confirmpasswordEd.text.toString()
        if (currentpassword.isEmpty()) {
            resetpasswordBinding.currentPasswordError.setText("Please Enter The CurrentPassword")


        } else if (newpassword.isEmpty()) {
            resetpasswordBinding.currentPasswordError.setText("")
            resetpasswordBinding.newpasswordError.setText("Please Enter The Password")


        } else if (newpassword.length < 8) {
            resetpasswordBinding.newpasswordError.setText("Password Must Contains 8 Digits")

        } else if (confirm_pass.isEmpty()) {
            resetpasswordBinding.newpasswordError.setText("")
            resetpasswordBinding.conpasswordError.setText("Please Enter The Password")

        } else if (!confirm_pass.equals(newpassword)) {
            resetpasswordBinding.conpasswordError.setText("Password Must be Same")

        } else {
            resetpasswordBinding.currentPasswordError.setText("")
            resetpasswordBinding.newpasswordError.setText("")
            resetpasswordBinding.conpasswordError.setText("")
            Toast.makeText(requireContext(), "password changed", Toast.LENGTH_SHORT).show()
        }
    }


}