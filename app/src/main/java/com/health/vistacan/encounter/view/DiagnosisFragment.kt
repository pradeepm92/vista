package com.health.vistacan

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentDiagnosisBinding
import com.health.vistacan.encounter.model.AdapterDataModel
import com.health.vistacan.encounter.model.DiagnosisModel

class DiagnosisFragment : Fragment(), View.OnClickListener, DiagnosisAdapter.clicklistener {
    lateinit var fragmentDiagnosisBinding: FragmentDiagnosisBinding
    private lateinit var navController: NavController
    val diagnosislist = ArrayList<DiagnosisModel>()
    val list = ArrayList<AdapterDataModel>()
    lateinit var adapter: DiagnosisAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        fragmentDiagnosisBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_diagnosis, container, false)
        navController = NavHostFragment.findNavController(this)
        adapter = DiagnosisAdapter(diagnosislist, this, list)
        doInitData()
        if (diagnosislist.isEmpty()&& list.isEmpty()){
            setdata()
            setadapterdata()
        }

        fragmentDiagnosisBinding!!.diagnosisRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            hasFixedSize()
            adapter = this@DiagnosisFragment.adapter
        }

        return fragmentDiagnosisBinding.root
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("diagnosisList", ArrayList(diagnosislist))
        outState.putParcelableArrayList("adapterDataList", ArrayList(list))
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val savedDiagnosisList = savedInstanceState.getParcelableArrayList<DiagnosisModel>("diagnosisList")
            val savedAdapterDataList = savedInstanceState.getParcelableArrayList<AdapterDataModel>("adapterDataList")

            if (savedDiagnosisList != null) {
                diagnosislist.clear()
                diagnosislist.addAll(savedDiagnosisList)
            }

            if (savedAdapterDataList != null) {
                list.clear()
                list.addAll(savedAdapterDataList)
            }

            adapter.notifyDataSetChanged()
        }
    }

    private fun doInitData() {
        fragmentDiagnosisBinding.submitBtn.setOnClickListener(this)
    }


    private fun setdata() {
        diagnosislist.add(DiagnosisModel(""))
    }

    private fun setadapterdata() {
        list.add(AdapterDataModel("sampath"))
        list.add(AdapterDataModel("saroja"))
        list.add(AdapterDataModel("saguni"))
        list.add(AdapterDataModel("arun"))
        list.add(AdapterDataModel("aravinth"))
        list.add(AdapterDataModel("sam"))

    }


    override fun onClick(v: View?) {
        when (v) {
            fragmentDiagnosisBinding.submitBtn -> {
                var isEmptyField = false
                for (diagnosis in diagnosislist) {

                    val description = diagnosis.selectedString.trim()
                    if (description.isEmpty() || !description.any { it.isLetter() }) {
                        Log.e("diagnosis_des1", diagnosis.description )
                        isEmptyField = true
                        break
                    }
                }



                if (isEmptyField) {
                    Toast.makeText(requireContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(R.id.action_diagnosisFragment_to_feeCodeFragment)
                }


            }
        }
    }

    override fun addbtnclicked(position: Int) {

        diagnosislist.add(DiagnosisModel(""))
        adapter.notifyItemInserted(diagnosislist.lastIndex)

    }

    override fun removebtnclicked(position: Int) {
        if (!diagnosislist.size.equals(1)) {
            diagnosislist.removeAt(position)
            adapter.notifyItemRemoved(position)

        }
    }


}