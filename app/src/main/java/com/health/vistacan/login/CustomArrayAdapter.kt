package com.health.vistacan.login

import android.R
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat


class CustomArrayAdapter<T> (
    context: Context,
    resource: Int,
    objects: List<T>
) : ArrayAdapter<T>(context, resource, objects){
    override fun isEnabled(position: Int): Boolean {
        // Disable the first item (position 0)
        return position != 0
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)

        val textView = view.findViewById<TextView>(android.R.id.text1)

        if (position == 0) {
            view.isEnabled = false
            view.alpha = 0.5f
            textView.setTextColor(ContextCompat.getColor(context,R.color.darker_gray))
        } else {
            view.isEnabled = true
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
        return view
    }

}