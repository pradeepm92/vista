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
import com.health.vistacan.R
import com.health.vistacan.encounter.model.AdapterDataModel
import com.health.vistacan.encounter.model.DiagnosisModel
import com.health.vistacan.encounter.model.FeecodeModel


class FeeCodeAdapter(
    private var feecodelist: ArrayList<FeecodeModel>,
    private var listener: clicklistener,
    private var list: ArrayList<AdapterDataModel>,

    ) : RecyclerView.Adapter<FeeCodeAdapter.MyViewholder>() {

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var description: AppCompatAutoCompleteTextView = view.findViewById(R.id.ed_txt_feecode)
        var change: AppCompatTextView = view.findViewById(R.id.ed_txt_fee_charge)
        var unit: AppCompatEditText = view.findViewById(R.id.ed_txt_fee_units)
        var add_btn: AppCompatImageButton = view.findViewById(R.id.btnAdd)
        var remove_btn: AppCompatImageButton = view.findViewById(R.id.btnRemove)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.feecode_row, parent, false)
        return MyViewholder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewholder, @SuppressLint("RecyclerView") position: Int) {


        holder.unit.setText(feecodelist[position].unit)
        holder.change.setText(feecodelist[position].change)
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            holder.description.context,
            android.R.layout.select_dialog_item,
            list.map { it.des }
        )
        holder.description.setText(feecodelist[position].selectedstring)
        holder.description.setThreshold(1)
        holder.description.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        holder.description.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Not needed for your case
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // Update the description field of the DiagnosisModel object
                feecodelist[holder.adapterPosition].selectedstring = s.toString()

            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed for your case
            }
        })

        holder.description.setOnItemClickListener { parent, view, p1, id ->

            feecodelist[position] = feecodelist[position].copy(
                selectedstring = parent.getItemAtPosition(p1).toString()
            )
//            notifyItemChanged(position)


        }

        holder.add_btn.setOnClickListener(View.OnClickListener {

            listener.addbtnclicked(holder.adapterPosition)



        })
        holder.remove_btn.setOnClickListener(View.OnClickListener {
//            if(position>0){
//                holder.description.setText(feecodelist[holder.adapterPosition].selectedstring)
//            }

            listener.removebtnclicked(
                holder.adapterPosition)
        })
    }

    override fun getItemCount(): Int {
        return feecodelist.size
    }

    interface clicklistener {
        fun addbtnclicked( position: Int)
        fun removebtnclicked( position: Int)

    }

}