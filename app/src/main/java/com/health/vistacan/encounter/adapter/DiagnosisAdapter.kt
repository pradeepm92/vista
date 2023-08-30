package com.health.vistacan

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.health.vistacan.R
import com.health.vistacan.encounter.model.AdapterDataModel
import com.health.vistacan.encounter.model.DiagnosisModel


class DiagnosisAdapter(
    private var diagnosislist: ArrayList<DiagnosisModel>,
    private var listener: clicklistener,
    private var list: ArrayList<AdapterDataModel>,

    ) : RecyclerView.Adapter<DiagnosisAdapter.MyViewholder>() {

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var description: AppCompatAutoCompleteTextView = view.findViewById(R.id.ed_txt_summary)
        var add_btn: ImageView = view.findViewById(R.id.btnAdd)
        var remove_btn: ImageView = view.findViewById(R.id.btnRemove)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.diagnosis_row, parent, false)
        return MyViewholder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewholder,
        @SuppressLint("RecyclerView") position: Int
    )


    {

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            holder.description.context,
            android.R.layout.select_dialog_item,
            list.map { it.des }
        )


        holder.description.setText(diagnosislist[position].selectedString)
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
                diagnosislist[holder.adapterPosition].selectedString = s.toString()

            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed for your case
            }
        })

        holder.description.setOnItemClickListener { parent, view, p1, id ->
            diagnosislist[position] = diagnosislist[position].copy(
                selectedString = parent.getItemAtPosition(p1).toString()
            )
//            notifyItemChanged(position)


        }


        holder.add_btn.setOnClickListener(View.OnClickListener {
            listener.addbtnclicked(holder.adapterPosition)
        })
        holder.remove_btn.setOnClickListener(View.OnClickListener {

            try {
//                if (holder.adapterPosition > 0 ) {
//                    holder.description.setText(diagnosislist[holder.adapterPosition].selectedString)
//                }
//                holder.description.clearFocus()
//                notifyItemRangeChanged(holder.adapterPosition, diagnosislist.size)
                listener.removebtnclicked(holder.adapterPosition)
            } catch (e: Exception) {
                Log.e("TAG", "onBindViewHolder: ", e)
            }
        })
    }

    override fun getItemCount(): Int {
        return diagnosislist.size
    }

    interface clicklistener {
        fun addbtnclicked(position: Int)
        fun removebtnclicked(position: Int)

    }

}