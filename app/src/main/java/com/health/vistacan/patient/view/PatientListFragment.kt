package com.health.vistacan.patient.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentPatientListBinding
import com.health.vistacan.patient.adapter.PatientListAdapter
import com.health.vistacan.patient.model.PatientListModel
import com.health.vistacan.patient.viewmodel.PatientViewModel
import java.util.*


class PatientListFragment : Fragment(),PatientListAdapter.Editclicklistener {
    lateinit var patientListBinding: FragmentPatientListBinding
    lateinit var patientlistviewmodel: PatientViewModel
     lateinit var adapter: PatientListAdapter
     var patientlist= ArrayList<PatientListModel>()
    var selectedlist=ArrayList<PatientListModel>()
    private lateinit var  navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        patientListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_patient_list, container, false)
        navController = NavHostFragment.findNavController(this)
        adapter = PatientListAdapter(patientlist, this@PatientListFragment)
        if (patientlist.isEmpty()){
            setdata()
        }
        patientListBinding!!.patientRecycler.apply {
            layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            isNestedScrollingEnabled=false
            hasFixedSize()
            adapter = this@PatientListFragment.adapter
        }

        patientListBinding.patientsearch.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle query text submit
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle query text change
                search(newText.toString())
                return false
            }
        })
        return patientListBinding.root
    }

    private fun setdata() {
        patientlist.add(PatientListModel(0, "6596326933","sampath","male", "9956326932", "abc@gmail.com"))
        patientlist.add(PatientListModel(1, "963265633","raguvaran", "male", "9956326956", "ragu@gmail.com"))
        patientlist.add(PatientListModel(2, "8659632563","vijay", "male", "9956326362", "vijay@gmail.com"))
        patientlist.add(PatientListModel(3,"456321456", "rosini", "female", "9956326123", "rosu@gmail.com"))
        patientlist.add(PatientListModel(4, "89652369","savithiri", "female", "9956326932", "savithiri093@gmail.com"))

    }


    fun search(text: String) {
        selectedlist.clear()
        for (patient in patientlist) {

            if (patient.name!!.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                selectedlist.add(patient)

            }

        }
        if (selectedlist.isEmpty()) {
            patientListBinding.emptyView.visibility=View.VISIBLE
            patientListBinding.patientRecycler.visibility = View.GONE
        } else {
            patientListBinding.patientRecycler.visibility = View.VISIBLE
            patientListBinding.emptyView.visibility=View.GONE
            adapter!!.search(selectedlist)
        }


    }

    override fun Itemclicked(id: Int, data: PatientListModel) {
        val bundle = Bundle().apply {
            putString("name", data.name)
            putString("phn", data.phn)
            putString("phone", data.phone)
            putString("gender", data.gender)
            putString("email", data.email)
        }

        navController.navigate(R.id.action_patientListFragment_to_editPatientListFragment, bundle)
    }

}