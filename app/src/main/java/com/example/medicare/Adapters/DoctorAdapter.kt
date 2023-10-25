package com.example.medicare.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.activity.DoctorDetailActivity
import com.example.medicare.models.Doctor

class DoctorAdapter(private val originalDoctorList: List<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>(), Filterable {

    private var onItemClickListener: OnItemClickListener? = null
    private var doctorModel: List<Doctor> = ArrayList()
    private var doctorList = originalDoctorList
    private var filteredDoctorList = originalDoctorList

    inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName) // Replace with your view IDs
        val textViewSpecialization: TextView = itemView.findViewById(R.id.textViewSpecialization) // Replace with your view IDs
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false) // Replace with your item layout
        return DoctorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val currentDoctor = filteredDoctorList[position]

        holder.textViewName.text = currentDoctor.name
        holder.textViewSpecialization.text = currentDoctor.specialization

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position, currentDoctor)
        }
    }

    override fun getItemCount(): Int {
        return filteredDoctorList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, doctorModel: Doctor)
    }

    // Search functionality
    private var searchQuery = ""

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchQuery = constraint?.toString() ?: ""
                val results = FilterResults()
                if (searchQuery.isEmpty()) {
                    results.values = originalDoctorList
                } else {
                    val filteredList = originalDoctorList.filter { doctor ->
                        doctor.name?.contains(searchQuery, ignoreCase = true) == true ||
                                doctor.specialization?.contains(searchQuery, ignoreCase = true) == true
                    }
                    results.values = filteredList
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDoctorList = results?.values as List<Doctor>
                notifyDataSetChanged()
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun submitList(newList: List<Doctor>) {
        doctorModel = newList
        notifyDataSetChanged()
    }
}
