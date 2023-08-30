package com.health.vistacan.encounter.view

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentFeeCodeBinding
import com.health.vistacan.encounter.adapter.FeeCodeAdapter
import com.health.vistacan.encounter.model.AdapterDataModel
import com.health.vistacan.encounter.model.FeecodeModel
import com.health.vistacan.encounter.viewmodel.EncounterViewModel

class FeeCodeFragment : Fragment(), FeeCodeAdapter.clicklistener ,View.OnClickListener{
    lateinit var fragmentFeeCodeBinding: FragmentFeeCodeBinding
    lateinit var encounterviewmodel: EncounterViewModel
    private lateinit var navController: NavController
    val feecodelist = ArrayList<FeecodeModel>()
    val list = ArrayList<AdapterDataModel>()
    lateinit var adapter: FeeCodeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        fragmentFeeCodeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_fee_code, container, false)
        navController = NavHostFragment.findNavController(this)
        adapter = FeeCodeAdapter(feecodelist, this, list)
        doInitContent()
        if (feecodelist.isEmpty() && list.isEmpty()) {
            setdata()
            setadapterdata()
        }
        fragmentFeeCodeBinding!!.feecodeRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            hasFixedSize()
            adapter = this@FeeCodeFragment.adapter
        }
        return fragmentFeeCodeBinding.root
    }

    private fun doInitContent() {
      fragmentFeeCodeBinding.submitBtn.setOnClickListener(this)
    }

    private fun setdata() {
        feecodelist.add(FeecodeModel("", "200", "usd"))
    }

    private fun setadapterdata() {
        list.add(AdapterDataModel("sampath"))
        list.add(AdapterDataModel("saroja"))
        list.add(AdapterDataModel("saguni"))
        list.add(AdapterDataModel("arun"))
        list.add(AdapterDataModel("aravinth"))
        list.add(AdapterDataModel("sam"))

    }

    override fun addbtnclicked(position: Int) {
        feecodelist.add(FeecodeModel("", "200", "usd"))

        adapter.notifyItemInserted(feecodelist.lastIndex)
    }

    override fun removebtnclicked(position: Int) {
        if (!feecodelist.size.equals(1)) {
            feecodelist.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    override fun onClick(v: View?) {
      when(v){

          fragmentFeeCodeBinding.submitBtn->{

              var isEmptyField = false
              for (i in feecodelist) {

                  val description = i.selectedstring.trim()
                  if (description.isEmpty() || !description.any { it.isLetter() } or  i.unit.isEmpty()) {
                      isEmptyField = true
                      break
                  }
//                  if (i.description.isEmpty() or  i.unit.isEmpty()) {
//                      isEmptyField = true
//                      break
//                  }
              }

              if (isEmptyField) {
                  Toast.makeText(requireContext(), "Field Cannot be Empty", Toast.LENGTH_SHORT).show()
              } else {
                  Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                  navController.navigate(R.id.encounterListFragment)
              }
          }
      }
    }
}