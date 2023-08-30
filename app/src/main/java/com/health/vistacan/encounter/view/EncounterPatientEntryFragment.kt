package com.health.vistacan.encounter.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentEncounterPatientEntryBinding
import com.health.vistacan.encounter.model.AdapterDataModel
import com.health.vistacan.encounter.viewmodel.EncounterViewModel
import com.health.vistacan.login.CustomArrayAdapter
import com.health.vistacan.utils.Utils.hideKeyboard
import java.util.*


class EncounterPatientEntryFragment : Fragment(),View.OnClickListener {
 lateinit var encounterPatientEntryFragment: FragmentEncounterPatientEntryBinding
 lateinit var encounterViewModel: EncounterViewModel
    private lateinit var  navController : NavController
    val list = ArrayList<AdapterDataModel>()
    private var mDate = 0
    private var mMonth: Int = 0
    private var mYear: Int = 0
    var mCalendar = Calendar.getInstance()
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        encounterPatientEntryFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_encounter_patient_entry, container, false)
        navController = NavHostFragment.findNavController(this)
        doInitContent()
        adapterdata()
        setadapter()
        setSpinnerData()
        onTimeValidate()
        encounterPatientEntryFragment.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                false
            } else {
                false
            }
        }
        return encounterPatientEntryFragment.root
    }




    private fun doInitContent() {
      encounterPatientEntryFragment.dob.setOnClickListener(this)
        encounterPatientEntryFragment.submitBtn.setOnClickListener(this)
    }

    private fun setSpinnerData() {
        val items = listOf("Gender", "Male", "Female")
        val adapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        encounterPatientEntryFragment.genderspinner.adapter = adapter
    }

    override fun onClick(v: View?) {
       when(v){
           encounterPatientEntryFragment.dob->{
               doOpenDateDialog()
           }
           encounterPatientEntryFragment.submitBtn->{
               doCheckValidation()
           }
       }
    }
    private fun setadapter(){
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            encounterPatientEntryFragment.patientnameTxt.context,
            android.R.layout.select_dialog_item,
            list.map { it.des }
        )

        encounterPatientEntryFragment.patientnameTxt.setThreshold(1)
        encounterPatientEntryFragment.patientnameTxt.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    private fun adapterdata() {
        list.add(AdapterDataModel("sampath"))
        list.add(AdapterDataModel("saroja"))
        list.add(AdapterDataModel("saguni"))
        list.add(AdapterDataModel("arun"))
        list.add(AdapterDataModel("aravinth"))
        list.add(AdapterDataModel("sam"))

    }
    private fun doOpenDateDialog() {
        val calendar = Calendar.getInstance()
        mDate = calendar[Calendar.DATE]
        mMonth = calendar[Calendar.MONTH]
        mYear = calendar[Calendar.YEAR]
        val datePickerDialog = DatePickerDialog(
            requireContext(), android.R.style.Theme_DeviceDefault_Dialog,
            { datePicker, year, month, date ->
                var month = month
                month = month + 1
                encounterPatientEntryFragment.dob.setText("$date-$month-$year")
                mCalendar.set(year, month - 1, date)
            }, mYear, mMonth, mDate
        )
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }
    private fun doCheckValidation() {
        val patientname =  encounterPatientEntryFragment.patientnameTxt.text.toString()
        val phn= encounterPatientEntryFragment.phntxt.text.toString()
        val dob =  encounterPatientEntryFragment.dob.text.toString()

        if (patientname.isEmpty()) {
            encounterPatientEntryFragment.patientnameerror.setText("Please Enter The PatientName")
        } else if (phn.isEmpty()) {
            encounterPatientEntryFragment.patientnameerror.setText("")
            encounterPatientEntryFragment.phnerror.setText("Please Enter The PHN")
        }   else if (dob.isEmpty()) {
            encounterPatientEntryFragment.phnerror.setText("")
            encounterPatientEntryFragment.doberror.setText("Please Select The DOB")
        } else if ( encounterPatientEntryFragment.genderspinner.getSelectedItem().toString()
                .equals("Gender", ignoreCase = true)
        ) {
            encounterPatientEntryFragment.doberror.setText("")
            encounterPatientEntryFragment.gendererror.setText("Please Select The Gender")
        } else{
            encounterPatientEntryFragment.patientnameerror.setText("")
            encounterPatientEntryFragment.gendererror.setText(" ")
            encounterPatientEntryFragment.doberror.setText("")
            encounterPatientEntryFragment.phnerror.setText("")
            navController.navigate(R.id.action_encounterPatientEntryFragment_to_encounterDateandServiceFragment)
        }
    }
    fun onTimeValidate(){
        encounterPatientEntryFragment.patientnameTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val name = s.toString()
                if (name.isEmpty()) {
                    encounterPatientEntryFragment.patientnameerror.setText("")

                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })
        encounterPatientEntryFragment.phntxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val phone = s.toString()
                if (phone.isEmpty()) {
                    encounterPatientEntryFragment.patientnameerror.setText("")
                    encounterPatientEntryFragment.phnerror.setText("")
                }else{
                    encounterPatientEntryFragment.phnerror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



            }
        })
        encounterPatientEntryFragment.dob.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val dob = s.toString()
                if (dob.isEmpty()) {
                    encounterPatientEntryFragment.doberror.setText("")

                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })



    }
}