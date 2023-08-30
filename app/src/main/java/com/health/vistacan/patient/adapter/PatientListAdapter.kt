package com.health.vistacan.patient.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.health.vistacan.R
import com.health.vistacan.patient.model.PatientListModel

class PatientListAdapter(
    private var patientlist: ArrayList<PatientListModel>,
    private var listener: Editclicklistener
) : RecyclerView.Adapter<PatientListAdapter.MyViewholder>() {


    fun search(selectedPatientList: ArrayList<PatientListModel>) {
        patientlist = selectedPatientList
        notifyDataSetChanged()
    }
    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {

        var cardView: MaterialCardView = view.findViewById(R.id.card_patrow)
        var gender: TextView = view.findViewById(R.id.gender_row)
        var phn: TextView = view.findViewById(R.id.phn_row)
        var name: TextView = view.findViewById(R.id.name_row)
        var phone: TextView = view.findViewById(R.id.phone_row)
        var email: TextView = view.findViewById(R.id.email_row)
        var editicon: ImageView = view.findViewById(R.id.edit_icon)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.patientlist_row, parent, false)
        return MyViewholder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.phn.text = patientlist[position].phn
        holder.name.text = patientlist[position].name
        holder.gender.text = patientlist[position].gender
        holder.phone.text = patientlist[position].phone
        holder.email.text = patientlist[position].email
        holder.editicon.load(R.drawable.ic_sharp_edit_24)
        val data = patientlist[position]
        holder.editicon.setOnClickListener(View.OnClickListener {
            listener.Itemclicked(patientlist[position].id,data)

        })
    }

    override fun getItemCount(): Int {
        return patientlist.size
    }

    interface Editclicklistener {
        fun Itemclicked(id: Int,data:PatientListModel)
    }
}