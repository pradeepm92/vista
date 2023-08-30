package com.health.vistacan.encounter.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentPatientPictureBinding
import com.health.vistacan.encounter.adapter.MedicalImageAdapter
import com.health.vistacan.encounter.model.MedicalImgmodel
import com.health.vistacan.encounter.viewmodel.EncounterViewModel
import com.health.vistacan.utils.ContentUriUtils.getFilePath
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import java.io.ByteArrayOutputStream
import java.io.IOException


class PatientPictureFragment : Fragment(),MedicalImageAdapter.clicklistener,View.OnClickListener {
    lateinit var patientPictureBinding: FragmentPatientPictureBinding
    lateinit var encounterViewModel: EncounterViewModel
    private lateinit var navController: NavController
    lateinit var adapter: MedicalImageAdapter
    var medicalimg= ArrayList<MedicalImgmodel>()
    var imageData:String=""
    private var images = ArrayList<Image>()
   val REQUEST_CODE_STORAGE_PERMISSION=1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        patientPictureBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_patient_picture,
            container,
            false
        )
        navController = NavHostFragment.findNavController(this)
        adapter = MedicalImageAdapter(medicalimg,this )
        doInitContent()
        setdata()
        patientPictureBinding!!.medicalimgRecycler.apply {
            layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            isNestedScrollingEnabled=false
            hasFixedSize()
            adapter = this@PatientPictureFragment.adapter}
        return patientPictureBinding.root

    }

    private fun doInitContent() {
        patientPictureBinding.uploadpatientpicBtn.setOnClickListener(this)
        patientPictureBinding.nextBtn.setOnClickListener(this)
    }

    fun setdata() {
        medicalimg.add(MedicalImgmodel(R.drawable.medimg1 ))
        medicalimg.add(MedicalImgmodel(R.drawable.medimg2 ))
        medicalimg.add(MedicalImgmodel(R.drawable.medimg3 ))
        medicalimg.add(MedicalImgmodel(R.drawable.medimg1))
        medicalimg.add(MedicalImgmodel(R.drawable.medimg2))
        medicalimg.add(MedicalImgmodel(R.drawable.medimg3))
        medicalimg.add(MedicalImgmodel(R.drawable.medimg1))


    }

    override fun deletebtnclicked(position: Int) {
        Log.e("position", position.toString(), )
    }

    override fun onClick(v: View?) {
     when(v){

         patientPictureBinding.uploadpatientpicBtn->{
             val dialog = Dialog(requireContext())
             dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
             dialog.setContentView(R.layout.picker_dialogue)
             val camera = dialog.findViewById(R.id.camera) as LinearLayout
             val gallery = dialog.findViewById(R.id.gallery) as LinearLayout
             dialog.setCancelable(true)
             val lWindowParams = WindowManager.LayoutParams()
             lWindowParams.copyFrom(dialog.window!!.getAttributes())
             lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT
             lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
             dialog.show()
             dialog.window?.attributes = lWindowParams
             camera.setOnClickListener {
                 if (dialog.isShowing) {
                     dialog.dismiss()
                 }


                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                     if (ContextCompat.checkSelfPermission(
                             requireContext(),
                             android.Manifest.permission.CAMERA
                         ) != PackageManager.PERMISSION_GRANTED
                     ) {
                         // Request the permission
                         ActivityCompat.requestPermissions(
                             requireActivity(),
                             arrayOf(android.Manifest.permission.CAMERA),
                             REQUEST_CODE_STORAGE_PERMISSION
                         )
                     } else {
                         imagePicker(true)
                     }
                 }


             }
             gallery.setOnClickListener {
                 if (dialog.isShowing) {
                     dialog.dismiss()
                 }


                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                     if (ContextCompat.checkSelfPermission(
                             requireContext(),
                             android.Manifest.permission.READ_MEDIA_IMAGES
                         ) != PackageManager.PERMISSION_GRANTED
                     ) {
                         // Request the permission
                         ActivityCompat.requestPermissions(
                             requireActivity(),
                             arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                             REQUEST_CODE_STORAGE_PERMISSION
                         )
                     } else {
                         imagePicker(false)
                     }


                 }else{
                     if (ContextCompat.checkSelfPermission(
                             requireContext(),
                             android.Manifest.permission.READ_EXTERNAL_STORAGE
                         ) != PackageManager.PERMISSION_GRANTED
                     ) {
                         // Request the permission
                         ActivityCompat.requestPermissions(
                             requireActivity(),
                             arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                             REQUEST_CODE_STORAGE_PERMISSION
                         )
                     } else {
                         imagePicker(false)
                     }
                 }


             }
         }
         patientPictureBinding.nextBtn->{
             navController.navigate(R.id.action_patientPictureFragment_to_newEncounterFragment)
         }
         }
     }

    private fun imagePicker(camera:Boolean){
        val config = ImagePickerConfig(
            statusBarColor = "#00796B",
            isLightStatusBar = false,
            toolbarColor = "#009688",
            toolbarTextColor = "#FFFFFF",
            toolbarIconColor = "#FFFFFF",
            backgroundColor = "#000000",
            progressIndicatorColor = "#009688",
            selectedIndicatorColor = "#2196F3",
            isCameraOnly = camera,
            isMultipleMode = false,
            isFolderMode = true,
            doneTitle = "DONE",
            folderTitle = "Albums",
            imageTitle = "Photos",
            isShowCamera = false,
            isShowNumberIndicator = true,
            isAlwaysShowDoneButton = true,
            rootDirectory = RootDirectory.DCIM,
            subDirectory = "Photos",
            maxSize = 1,
            limitMessage = "You could only select up to 1 photos",
            selectedImages = images
        )
        launcher.launch(config)
    }

    private val launcher = registerImagePicker {
        images = it
        for (image in images) {
            patientPictureBinding.delete.setOnClickListener() {
                patientPictureBinding.imgEncounterPatientPicture.load(R.drawable.noimage)
            }
            patientPictureBinding.imgEncounterPatientPicture.setImageURI(image.uri)
            val path = getFilePath(requireContext(), image.uri)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, image.uri)

                val stream = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

                val bytes = stream.toByteArray()

                imageData = Base64.encodeToString(bytes, Base64.DEFAULT).replace("\\s".toRegex(), "")

                patientPictureBinding.imgEncounterPatientPicture.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }





    }
}




