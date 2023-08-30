package com.health.vistacan.encounter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.health.vistacan.R
import com.health.vistacan.encounter.model.EncounterModel

class EncounterAdapter(private var encounterlist: ArrayList<EncounterModel>,
                       private var listener: Expandclicklistener
):RecyclerView.Adapter<EncounterAdapter.MyViewholder>() {

    class MyViewholder(view: View):RecyclerView.ViewHolder(view) {
        var gender: TextView = view.findViewById(R.id.gender_txt)
        var phn: TextView = view.findViewById(R.id.phn_txt)
        var name: TextView = view.findViewById(R.id.name_txt)
        var dropicon: ImageView = view.findViewById(R.id.down_arrow)
        var expandablelayout: RelativeLayout = view.findViewById(R.id.expandable_layout)
        var headerlayout: MaterialCardView = view.findViewById(R.id.header)
        var encounterbtn: MaterialButton = view.findViewById(R.id.encounter_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.encounterlist_row, parent, false)
        return MyViewholder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        if (position==0){
            holder.headerlayout.visibility=View.VISIBLE
        }else{
            holder.headerlayout.visibility=View.GONE
        }
        holder.phn.text = encounterlist[position].phn
        holder.name.text = encounterlist[position].name
        holder.gender.text = encounterlist[position].gender
        holder.dropicon.load(R.drawable.ic_baseline_arrow_drop_down_24)
        val isExpandable: Boolean = encounterlist[position].isexpandable
        holder.expandablelayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.dropicon.setOnClickListener(View.OnClickListener {
//            listener.Itemclicked(encounterlist[position].id)
            val encounter = encounterlist[position]
            encounter.isexpandable = !encounter.isexpandable
            notifyItemChanged(position)
        })
        holder.encounterbtn.setOnClickListener(View.OnClickListener {
            listener.Itemclicked(position)
        })
    }

    override fun getItemCount(): Int {
       return encounterlist.size
    }
    interface Expandclicklistener {
        fun Itemclicked(pos: Int)
    }
}