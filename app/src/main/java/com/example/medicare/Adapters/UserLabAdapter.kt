package com.example.medicare.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.models.LabModel

class UserLabAdapter(private val labList: MutableList<LabModel>) : RecyclerView.Adapter<UserLabAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var hospital: TextView = itemView.findViewById(R.id.txt_hospital)
        var user: TextView = itemView.findViewById(R.id.txt_name)
        var doctor: TextView = itemView.findViewById(R.id.txt_doctor)
        var result: TextView = itemView.findViewById(R.id.txt_result)
        var date: TextView = itemView.findViewById(R.id.txt_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_lab_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return labList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var current = labList[position]
        holder.hospital.text = "Hospital Name: ${current.hospital}"
        holder.user.text = "User Name: ${current.userID}"
        holder.doctor.text = "Doctor Name: ${current.doctor}"
        holder.result.text = "Result: ${current.result}"
        holder.date.text = current.date
    }
}