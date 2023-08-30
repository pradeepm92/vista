package com.health.vistacan.setting.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.health.vistacan.databinding.FragmentProfileBinding
import com.health.vistacan.setting.viewmodel.SettingViewModel

class ProfileFragment : Fragment(),View.OnClickListener {
lateinit var profileBinding: FragmentProfileBinding
lateinit var settingViewModel: SettingViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        navController = NavHostFragment.findNavController(this)
        doInitContent()
        onTimeValidate()
        return profileBinding.root
    }

    private fun onTimeValidate() {
        profileBinding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val email = s.toString()
                if (email.isEmpty()) {
                    profileBinding.nameerror.setText("")


                } else {
                    profileBinding.emailerror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        profileBinding.phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    profileBinding.emailerror.setText("")


                } else {
                    profileBinding.phoneerror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        profileBinding.servicefacility.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    profileBinding.phoneerror.setText("")


                } else {
                    profileBinding.serviceerror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        profileBinding.mspPractioner.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    profileBinding.serviceerror.setText("")


                } else {
                    profileBinding.mspPractionererror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        profileBinding.mspPayee.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    profileBinding.mspPractionererror.setText("")


                } else {
                    profileBinding.mspPayeererror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        profileBinding.datacenterNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    profileBinding.mspPayeererror.setText("")


                } else {
                    profileBinding.datacentererror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        profileBinding.collegelicenseNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val username = s.toString()
                if (username.isEmpty()) {
                    profileBinding.datacentererror.setText("")


                } else {
                    profileBinding.collegelicenseError.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun doInitContent() {
        profileBinding.saveBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      when(v){
          profileBinding.saveBtn->{
              doCheckValidation()
          }
      }
    }

    private fun doCheckValidation() {
        val name = profileBinding.name.text.toString()
        val email = profileBinding.email.text.toString()
        val phone = profileBinding.phone.text.toString()
        val servicefacility = profileBinding.servicefacility.text.toString()
        val msppractioner=profileBinding.mspPractioner.text.toString()
        val msppayee = profileBinding.mspPayee.text.toString()
        val datacenter_num = profileBinding.datacenterNum.text.toString()
        val collegelicense_num = profileBinding.collegelicenseNum.text.toString()
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (name.isEmpty()) {
            profileBinding.nameerror.setText("please Enter The Name")
        } else if (email.isEmpty()) {
            profileBinding.nameerror.setText("")
            profileBinding.emailerror.setText("Please Enter The Email")
        } else if (!isValid) {
            profileBinding.emailerror.setText(" Invalid Email")
        }  else if (phone.isEmpty()) {
            profileBinding.emailerror.setText("")
            profileBinding.phoneerror.setText("Please Enter The PhoneNumber")
        }  else if (phone.length < 10) {
            profileBinding.phoneerror.setText(" PhoneNumber Must Contain 10 Digits")
        } else if (servicefacility.isEmpty()) {
            profileBinding.phoneerror.setText(" ")
            profileBinding.serviceerror.setText("Please Enter The ServiceFacility ")
        } else if (msppractioner.isEmpty()) {
            profileBinding.serviceerror.setText(" ")
            profileBinding.mspPractionererror.setText("Please Enter The MSP PractionerNumber")
        } else if (msppractioner.length < 6) {
            profileBinding.mspPractionererror.setText(" MSP PractionerNumber Must Contain Atleast 6 Digits")
        }else if (msppayee.isEmpty()) {
            profileBinding.mspPractionererror.setText(" ")
            profileBinding.mspPayeererror.setText("Please Enter The MSP PayeeNumber")
        } else if (msppayee.length < 6) {
            profileBinding.mspPayeererror.setText(" MSP PayeeNumber Must Contain  Atleast 6 Digits")
        }else if (datacenter_num.isEmpty()) {
            profileBinding.mspPayeererror.setText(" ")
            profileBinding.datacentererror.setText("Please Enter The Data Center Number")
        } else if (datacenter_num.length < 6) {
            profileBinding.datacentererror.setText(" Data Center Number Must Contain Atleast 6 Digits")
        }else if (collegelicense_num.isEmpty()) {
            profileBinding.datacentererror.setText(" ")
            profileBinding.collegelicenseError.setText("Please Enter The College License Number")
        } else if (collegelicense_num.length < 6) {
            profileBinding.collegelicenseError.setText("College License Number Must Contain Atleast 6 Digits")
        }
        else{
            profileBinding.nameerror.setText("")
            profileBinding.emailerror.setText("")
            profileBinding.phoneerror.setText(" ")
            profileBinding.serviceerror.setText(" ")
            profileBinding.mspPractionererror.setText(" ")
            profileBinding.mspPayeererror.setText(" ")
            profileBinding.datacentererror.setText(" ")
            profileBinding.collegelicenseError.setText("")
            Toast.makeText(requireContext(),"success", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.settingFragment)
        }
    }


}