package com.health.vistacan.encounter.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.health.vistacan.R
import com.health.vistacan.encounter.model.AdapterDataModel
import com.health.vistacan.encounter.model.DiagnosisModel
import com.health.vistacan.encounter.model.FeecodeModel
import com.health.vistacan.encounter.model.MedicalImgmodel


class MedicalImageAdapter(
    private var medicalimg: ArrayList<MedicalImgmodel>,
    private var listener: clicklistener,


    ) : RecyclerView.Adapter<MedicalImageAdapter.MyViewholder>() {

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var medical_img: ImageView = view.findViewById(R.id.med_img)
        var delete_img: ImageView = view.findViewById(R.id.delete_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.medicalimg_row, parent, false)
        return MyViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewholder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.medical_img.load(medicalimg[position].img)
        holder.delete_img.setOnClickListener(View.OnClickListener {
            listener.deletebtnclicked(position)
        })
    }

    override fun getItemCount(): Int {
        return medicalimg.size
    }

    interface clicklistener {
        fun deletebtnclicked(position: Int)
    }

}