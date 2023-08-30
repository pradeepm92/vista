package com.health.vistacan.encounter.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentEncounterListBinding
import com.health.vistacan.encounter.adapter.EncounterAdapter
import com.health.vistacan.encounter.model.EncounterModel
import com.health.vistacan.encounter.viewmodel.EncounterViewModel
import com.health.vistacan.patient.adapter.PatientListAdapter
import com.health.vistacan.patient.model.PatientListModel


class EncounterListFragment : Fragment() ,EncounterAdapter.Expandclicklistener{
    lateinit var Encounterlistbinding:FragmentEncounterListBinding
    lateinit var encounterviewmodel:EncounterViewModel
    lateinit var adapter: EncounterAdapter
    private lateinit var  navController : NavController
    var encounterlist= ArrayList<EncounterModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Encounterlistbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_encounter_list, container, false)
        navController = NavHostFragment.findNavController(this)
        adapter = EncounterAdapter(encounterlist,this )
        if (encounterlist.isEmpty() ) {
            setdata()

        }

        Encounterlistbinding!!.patientRecycler.apply {
            layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            isNestedScrollingEnabled=false
            hasFixedSize()
            adapter = this@EncounterListFragment.adapter
        }
        return Encounterlistbinding.root
    }

    private fun setdata() {
        encounterlist.add(EncounterModel(0, "sampath", "63256396", "male", ))
        encounterlist.add(EncounterModel(1, "raguvaran", "89653236", "male", ))
        encounterlist.add(EncounterModel(2, "vijay", "96586523", "male", ))
        encounterlist.add(EncounterModel(3, "rosini", "32569896", "female", ))
        encounterlist.add(EncounterModel(4, "savithiri", "65329636", "female", ))

    }

    override fun Itemclicked(pos: Int) {
        navController.navigate(R.id.soundRecordFragment)

//        navController.navigate(R.id.action_encounterListFragment_to_encounterPatientEntryFragment)
    }
}