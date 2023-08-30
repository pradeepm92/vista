package com.health.vistacan.encounter.view
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentEncounterDateandServiceBinding
import com.health.vistacan.encounter.viewmodel.EncounterViewModel
import com.health.vistacan.login.CustomArrayAdapter
import java.text.SimpleDateFormat
import java.util.*


class EncounterDateandServiceFragment : Fragment(), View.OnClickListener {
    lateinit var dateandServiceBinding: FragmentEncounterDateandServiceBinding
    lateinit var encounterViewModel: EncounterViewModel
    private lateinit var navController: NavController
    private var mDate = 0
    private var mMonth: Int = 0
    private var mYear: Int = 0
    var mCalendar = Calendar.getInstance()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dateandServiceBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_encounter_dateand_service,
            container,
            false
        )
        navController = NavHostFragment.findNavController(this)
        doInitContent()
        setSpinnerData()
        doTimeValidation()
        dateandServiceBinding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                false
            } else {
                false
            }
        }
        return dateandServiceBinding.root
    }

    private fun doTimeValidation() {
        dateandServiceBinding.dateofservicetxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val name = s.toString()
                if (name.isEmpty()) {
                    dateandServiceBinding.dateserviceerror.setText("")

                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



            }
        })
        dateandServiceBinding.time.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Does nothing intentionally
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val phone = s.toString()
                if (phone.isEmpty()) {
                    dateandServiceBinding.dateserviceerror.setText("")
                    dateandServiceBinding.timeerror.setText("")
                }else{
                    dateandServiceBinding.timeerror.setText("")
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



            }
        })
        dateandServiceBinding.visitspinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                l: Long
            ) {

                if (view != null) {
                    val selectedValue: String = dateandServiceBinding.visitspinner.selectedItem.toString()
                    if (selectedValue == "Select The Category") {
                        dateandServiceBinding.timeerror.setText("")
                        dateandServiceBinding.visitspinnererror.setText("")
                    } else {
                        dateandServiceBinding.timeerror.setText("")
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        })



        dateandServiceBinding.facilityspinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                l: Long
            ) {
                if (view != null) {
                    val selectedValue: String =
                        dateandServiceBinding.facilityspinner.getSelectedItem().toString()
                    if (selectedValue == "Select The Facility") {
                        dateandServiceBinding.visitspinnererror.setText("")
                        dateandServiceBinding.facilityspinnerrerror.setText("")
                    } else {
                        dateandServiceBinding.visitspinnererror.setText("")
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        })
        dateandServiceBinding.billfacilityspinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                l: Long
            ) {
                if (view != null) {
                    val selectedValue: String =
                        dateandServiceBinding.billfacilityspinner.getSelectedItem().toString()
                    if (selectedValue == "Select The Bill Facility") {
                        dateandServiceBinding.facilityspinnerrerror.setText("")
                        dateandServiceBinding.billfacilityspinnerrerror.setText("")
                    } else {
                        dateandServiceBinding.facilityspinnerrerror.setText("")
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // Handle the case when no item is selected
            }
        })
        dateandServiceBinding.sensitivityspinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                l: Long
            ) {
                if (view != null) {
                    val selectedValue: String =
                        dateandServiceBinding.sensitivityspinner.getSelectedItem().toString()
                    if (selectedValue == "Select Sensitivity Type") {
                        dateandServiceBinding.billfacilityspinnerrerror.setText("")
                        dateandServiceBinding.sensitivityspinnererror.setText("")
                    } else {
                        dateandServiceBinding.billfacilityspinnerrerror.setText("")
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        })
    }

    private fun doInitContent() {
        dateandServiceBinding.nextBtn.setOnClickListener(this)
        dateandServiceBinding.dateofservicetxt.setOnClickListener(this)
        dateandServiceBinding.time.setOnClickListener(this)
    }

    private fun setSpinnerData() {
        val visitcategory = listOf("Select The Category", "Online", "Hospital")
        val sensitivity= listOf("Select Sensitivity","High","Medium","Low")
        val billfacility= listOf( "Select The Bill Facility","Cash","Upi")
        val facility= listOf( "Select The Facility","Facitity1","Facitity2")
        val visitadapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            visitcategory
        )
        visitadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateandServiceBinding.visitspinner.adapter = visitadapter


        val adapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            facility
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateandServiceBinding.facilityspinner.adapter = adapter
        val sen_adapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sensitivity
        )
        sen_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateandServiceBinding.sensitivityspinner.adapter = sen_adapter
        val billadapter = CustomArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            billfacility
        )
        billadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateandServiceBinding.billfacilityspinner.adapter = billadapter
    }

    override fun onClick(v: View?) {
        when (v) {
            dateandServiceBinding.nextBtn -> {
//                navController.navigate(R.id.action_encounterDateandServiceFragment_to_patientPictureFragment)
                doCheckValidation()
            }
            dateandServiceBinding.dateofservicetxt->{
                doOpenDateDialog()
            }
            dateandServiceBinding.time->{
                showTimePickerDialog()
            }
        }
    }

    private fun doOpenDateDialog() {
        val calendar = Calendar.getInstance()
        val mDate = calendar.get(Calendar.DATE)
        val mMonth = calendar.get(Calendar.MONTH)
        val mYear = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(
            requireContext(), android.R.style.Theme_DeviceDefault_Dialog,
            DatePickerDialog.OnDateSetListener { _, year, month, date ->
                val formattedMonth = (month + 1).toString().padStart(2, '0')
                val formattedDate = date.toString().padStart(2, '0')
                val selectedDate = "$formattedDate-$formattedMonth-$year"
                dateandServiceBinding.dateofservicetxt.setText(selectedDate)
                mCalendar.set(year, month, date)
            }, mYear, mMonth, mDate
        )

        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }
    private fun doCheckValidation() {

        val dateofservice =  dateandServiceBinding.dateofservicetxt.text.toString()
        val time= dateandServiceBinding.time.text.toString()


        if (dateofservice.isEmpty()) {
            dateandServiceBinding.dateserviceerror.setText("Please Enter The Date of Service")
        } else if (time.isEmpty()) {
            dateandServiceBinding.dateserviceerror.setText("")
            dateandServiceBinding.timeerror.setText("Please Enter The Time")
        }   else if ( dateandServiceBinding.visitspinner.getSelectedItem().toString()
                .equals("Select The Category", ignoreCase = true)
        ) {
            dateandServiceBinding.timeerror.setText("")
            dateandServiceBinding.visitspinnererror.setText("Please Select The Category")
        }else if ( dateandServiceBinding.facilityspinner.getSelectedItem().toString()
                .equals("Select The Facility", ignoreCase = true)
        ) {
            dateandServiceBinding.visitspinnererror.setText("")
            dateandServiceBinding.facilityspinnerrerror.setText("Please Select The Facility")
        }else if ( dateandServiceBinding.billfacilityspinner.getSelectedItem().toString()
                .equals("Select The Bill Facility", ignoreCase = true)
        ) {
            dateandServiceBinding.facilityspinnerrerror.setText("")
            dateandServiceBinding.billfacilityspinnerrerror.setText("Please Select The  BillFacility")
        }
        else if ( dateandServiceBinding.sensitivityspinner.getSelectedItem().toString()
                .equals("Select Sensitivity", ignoreCase = true)
        ) {
            dateandServiceBinding.billfacilityspinnerrerror.setText("")
            dateandServiceBinding.sensitivityspinnererror.setText("Please Select The  Sensitivity Type")
        }
        else{
            dateandServiceBinding.dateserviceerror.setText("")
            dateandServiceBinding.timeerror.setText("")
            dateandServiceBinding.visitspinnererror.setText("")
            dateandServiceBinding.facilityspinnerrerror.setText("")
            dateandServiceBinding.billfacilityspinnerrerror.setText("")
            dateandServiceBinding.sensitivityspinnererror.setText(" ")
            navController.navigate(R.id.action_encounterDateandServiceFragment_to_patientPictureFragment)
        }

    }
    private fun showTimePickerDialog() {
        val c = Calendar.getInstance()
        val currentHour = c.get(Calendar.HOUR_OF_DAY)
        val currentMinute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val selectedTime = simpleDateFormat.format(calendar.time)

                dateandServiceBinding.time.setText( selectedTime)
            },
            currentHour,
            currentMinute,
            false
        )

        timePickerDialog.show()
    }


}