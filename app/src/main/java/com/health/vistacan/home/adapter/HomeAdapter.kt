package com.health.vistacan.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.health.vistacan.R
import com.health.vistacan.home.HomeFragment

class HomeAdapter(
    private var data: ArrayList<HomeFragment.HomeModel>, private var listener: HomeFragment
):RecyclerView.Adapter<HomeAdapter.MyViewholder>() {


    class MyViewholder(view: View):RecyclerView.ViewHolder(view) {
        var cardView: MaterialCardView = view.findViewById(R.id.cardView)
        var speName: TextView = view.findViewById(R.id.speName)
        var icon: ImageView = view.findViewById(R.id.icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)
        return MyViewholder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.speName.text = data[position].name
        holder.icon.load(data[position].icon)
        holder.cardView.setOnClickListener {
            listener.itemClicked(data[position].id)
        }

    }

    override fun getItemCount(): Int {
      return data.size
    }
    interface ItemClickListener {
        fun itemClicked(id: Int)
    }
}