package com.health.vistacan.patient.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentEditPatientListBinding
import com.health.vistacan.databinding.FragmentPatientBinding
import com.health.vistacan.login.CustomArrayAdapter
import com.health.vistacan.patient.viewmodel.PatientViewModel


class EditPatientListFragment : Fragment(),View.OnClickListener {
    lateinit var editPatientListBinding: FragmentEditPatientListBinding
    lateinit var patientViewModel: PatientViewModel
    private lateinit var  navController : NavController

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        editPatientListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_patient_list, container, false)
        navController = NavHostFragment.findNavController(this)

        doInitContent()
        doGetIntent()
        onTimeValidate()
        editPatientListBinding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                false
            } else {
                false
            }
        }
        return editPatientListBinding.root
    }

    private fun doGetIntent() {
        val items = listOf("Gender", "Male", "Female")
        val adapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editPatientListBinding.genderspinner.adapter = adapter
        val name = arguments?.getString("name")
        val phn = arguments?.getString("phn")
        val phone = arguments?.getString("phone")
        var gender = arguments?.getString("gender")
        val email = arguments?.getString("email")
        editPatientListBinding.nameEdt.setText(name)
        editPatientListBinding.phnEdt.setText(phn)
        editPatientListBinding.phoneEdt.setText(phone)
        editPatientListBinding.emailEdt.setText(email)
        gender = gender?.trim()
        val genderIndex = items.indexOfFirst { it.equals(gender, ignoreCase = true) }
        if (genderIndex != -1) {
            editPatientListBinding.genderspinner.setSelection(genderIndex)
        }

    }

    private fun doInitContent() {
        editPatientListBinding.saveBtn.setOnClickListener(this)
    }

    fun onTimeValidate(){

        editPatientListBinding.phnEdt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val phone = s.toString()
                if (phone.isEmpty()) {
                    editPatientListBinding.namererror.setText("")
                    editPatientListBinding.phnerror.setText("")
                }else{
                    editPatientListBinding.phnerror.setText(" ")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



            }
        })

        editPatientListBinding.emailEdt.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val email= s.toString()
                val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (email.isEmpty()) {
                    editPatientListBinding.phoneerror.setText("")
                    editPatientListBinding.emailerror.setText(" ")
                }else{
                    editPatientListBinding.emailerror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



            }
        })
        editPatientListBinding.phoneEdt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val phone = s.toString()
                if (phone.isEmpty()) {
                    editPatientListBinding.emailerror.setText("")
                    editPatientListBinding.phoneerror.setText("")
                } else if (phone.length < 10) {
                    editPatientListBinding.phoneerror.setText(" Phonenumber Must Contain 10 Digits")
                }else{
                    editPatientListBinding.phoneerror.setText(" ")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

    }

    private fun doCheckValidation() {
        val name = editPatientListBinding.nameEdt.text.toString()
        val phn=editPatientListBinding.phnEdt.text.toString()
        val phone = editPatientListBinding.phoneEdt.text.toString()
        val email = editPatientListBinding.emailEdt.text.toString()
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (name.isEmpty()) {
            editPatientListBinding.namererror.setText("Please Enter The Name")
        }  else if (phn.isEmpty()) {
            editPatientListBinding.namererror.setText("")
            editPatientListBinding.phnerror.setText("Please Enter The PHN Number")
        }   else if (editPatientListBinding.genderspinner.getSelectedItem().toString()
                .equals("Gender", ignoreCase = true)
        ) {
            editPatientListBinding.phoneerror.setText("")
            editPatientListBinding.gendererror.setText("Please Select The Gender")
        } else if (email.isEmpty()) {
            editPatientListBinding.gendererror.setText("")
            editPatientListBinding.emailerror.setText("Please Enter The Email")
        } else if (!isValid) {
            editPatientListBinding.emailerror.setText(" Invalid Email")
        } else if (phone.isEmpty()) {
            editPatientListBinding.emailerror.setText("")
            editPatientListBinding.phoneerror.setText("Please Enter The PhoneNumber")
        } else if (phone.length < 10) {
            editPatientListBinding.phoneerror.setText(" Phonenumber Must Contain 10 Digits")
        }else{
            editPatientListBinding.phoneerror.setText(" ")
            Toast.makeText(requireContext(),"saved",Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.patientListFragment)
        }
    }

    override fun onClick(v: View?) {
      when(v){
          editPatientListBinding.saveBtn->{
              doCheckValidation()

          }
      }
    }

}