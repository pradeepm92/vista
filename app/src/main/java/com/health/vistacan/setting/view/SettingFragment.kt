package com.health.vistacan.setting.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentSettingBinding
import com.health.vistacan.setting.viewmodel.SettingViewModel


class SettingFragment : Fragment(), View.OnClickListener {
    lateinit var settingBinding: FragmentSettingBinding
    lateinit var settingViewModel: SettingViewModel
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        settingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        navController = NavHostFragment.findNavController(this)
        doInitContent()

        return settingBinding.root
    }

    private fun doInitContent() {
        settingBinding.profilecard.setOnClickListener(this)
        settingBinding.resetcard.setOnClickListener(this)
        settingBinding.aboutcard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            settingBinding.profilecard -> {
                navController.navigate(R.id.action_settingFragment_to_profileFragment)
            }
            settingBinding.resetcard -> {
                navController.navigate(R.id.action_settingFragment_to_resetPasswordFragment)
            }
            settingBinding.aboutcard -> {
                navController.navigate(R.id.action_settingFragment_to_aboutFragment)
            }
        }
    }


}