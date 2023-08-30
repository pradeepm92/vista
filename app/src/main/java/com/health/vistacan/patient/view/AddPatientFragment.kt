package com.health.vistacan.patient.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentPatientBinding
import com.health.vistacan.login.CustomArrayAdapter
import com.health.vistacan.patient.viewmodel.PatientViewModel
import java.util.*

class AddPatientFragment : Fragment(), View.OnClickListener {
    lateinit var patientbinding: FragmentPatientBinding
    private var mDate = 0
    private var mMonth: Int = 0
    private var mYear: Int = 0
    var mCalendar = Calendar.getInstance()
    private lateinit var  navController : NavController

    companion object {
        fun newInstance() = AddPatientFragment()
    }

    private lateinit var viewModel: PatientViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        patientbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient, container, false)
        navController = NavHostFragment.findNavController(this)
        doInitContent()
        setSpinnerData()
        onTimeValidate()
        patientbinding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                false
            } else {
                false
            }
        }

        return patientbinding.root


    }

    private fun setSpinnerData() {
        val items = listOf("Gender", "Male", "Female")
        val adapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        patientbinding.genderspinner.adapter = adapter
    }

    private fun doInitContent() {
        patientbinding.saveBtn.setOnClickListener(this)
        patientbinding.dob.setOnClickListener(this)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        when (v) {
            patientbinding.saveBtn -> {
                doCheckValidation()
            }
            patientbinding.dob -> {
                doOpenDateDialog()
            }
        }
    }

   fun onTimeValidate(){
       patientbinding.lastnameEdt.addTextChangedListener(object: TextWatcher {
           override fun afterTextChanged(s: Editable?) {
               // Does nothing intentionally
           }

           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
               val username = s.toString()
               if (username.isEmpty()) {
                   patientbinding.firstnamererror.setText("")

               }
           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


           }
       })
       patientbinding.phnEdt.addTextChangedListener(object: TextWatcher {
           override fun afterTextChanged(s: Editable?) {
               // Does nothing intentionally
           }

           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

               val phone = s.toString()
               if (phone.isEmpty()) {
                   patientbinding.lastnameerror.setText("")
                   patientbinding.phnerror.setText("")
               }else{
                   patientbinding.phnerror.setText(" ")
               }

           }
       })
       patientbinding.dob.addTextChangedListener(object: TextWatcher {
           override fun afterTextChanged(s: Editable?) {
               // Does nothing intentionally
           }

           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
               val dob = s.toString()
               if (dob.isEmpty()) {
                   patientbinding.phoneerror.setText("")

               }
           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



           }
       })
       patientbinding.emailEdt.addTextChangedListener(object: TextWatcher {

           override fun afterTextChanged(s: Editable?) {
               // Does nothing intentionally
           }

           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

               val email= s.toString()
               val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
               if (email.isEmpty()) {
                   patientbinding.gendererror.setText("")
                   patientbinding.emailerror.setText(" ")
               }else{
                   patientbinding.emailerror.setText("")
               }

           }
       })
       patientbinding.phoneEdt.addTextChangedListener(object: TextWatcher {
           override fun afterTextChanged(s: Editable?) {
               // Does nothing intentionally
           }

           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

               val phone = s.toString()
               if (phone.isEmpty()) {
                   patientbinding.emailerror.setText("")
                   patientbinding.phoneerror.setText("")
               } else if (phone.length < 10) {
                   patientbinding.phoneerror.setText(" Phonenumber Must Contain 10 Digits")
               }else{
                   patientbinding.phoneerror.setText(" ")
               }

           }
       })

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
                patientbinding.dob.setText("$date-$month-$year")
                mCalendar.set(year, month - 1, date)
            }, mYear, mMonth, mDate
        )
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }

    private fun doCheckValidation() {
        val firstname = patientbinding.firstnameEdt.text.toString()
        val lastname = patientbinding.lastnameEdt.text.toString()
        val phn=patientbinding.phnEdt.text.toString()
        val phone = patientbinding.phoneEdt.text.toString()
        val dob = patientbinding.dob.text.toString()
        val email = patientbinding.emailEdt.text.toString()
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (firstname.isEmpty()) {
            patientbinding.firstnamererror.setText("please Enter The FirstName")
        } else if (lastname.isEmpty()) {
            patientbinding.firstnamererror.setText("")
            patientbinding.lastnameerror.setText("Please Enter The LastName")
        } else if (phn.isEmpty()) {
            patientbinding.lastnameerror.setText("")
            patientbinding.phnerror.setText("Please Enter The PHN Number")
        }  else if (dob.isEmpty()) {
            patientbinding.phoneerror.setText("")
            patientbinding.doberror.setText("Please Select The DOB")
        } else if (patientbinding.genderspinner.getSelectedItem().toString()
                .equals("Gender", ignoreCase = true)
        ) {
            patientbinding.doberror.setText("")
            patientbinding.gendererror.setText("Please Select The Gender")
        } else if (email.isEmpty()) {
            patientbinding.gendererror.setText("")
            patientbinding.emailerror.setText("Please Enter The Email")
        } else if (!isValid) {
            patientbinding.emailerror.setText(" Invalid Email")
        } else if (phone.isEmpty()) {
            patientbinding.emailerror.setText("")
            patientbinding.phoneerror.setText("Please Enter The PhoneNumber")
        } else if (phone.length < 10) {
            patientbinding.phoneerror.setText(" PhoneNumber Must Contain 10 Digits")
        }else{
            patientbinding.phoneerror.setText(" ")
            Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.homeFragment)
        }
    }

}