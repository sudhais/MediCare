package com.example.medicare.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.activity.AddLabActivity
import com.example.medicare.models.LabModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminLabAdapter(private val labList: MutableList<LabModel>) : RecyclerView.Adapter<AdminLabAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var hospital: TextView = itemView.findViewById(R.id.txt_hospital)
        var user: TextView = itemView.findViewById(R.id.txt_name)
        var doctor: TextView = itemView.findViewById(R.id.txt_doctor)
        var result: TextView = itemView.findViewById(R.id.txt_result)
        var date: TextView = itemView.findViewById(R.id.txt_date)
        var delete: Button = itemView.findViewById(R.id.btn_delete)
        var edit: Button = itemView.findViewById(R.id.btn_edit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_lab_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return labList.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val current = labList[position]
        holder.hospital.text = "Hospital Name: ${current.hospital}"
        holder.user.text = "User Name: ${current.userID}"
        holder.doctor.text = "Doctor Name: ${current.doctor}"
        holder.result.text = "Result: ${current.result}"
        holder.date.text = current.date

        val context = holder.itemView.context

        holder.delete.setOnClickListener {

            val ref: DatabaseReference = FirebaseDatabase.getInstance().reference.child("LabTest")
             ref.child(current.id!!).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Deleted succesfully", Toast.LENGTH_SHORT).show()
//                    notifyItemRemoved(position)
                }

        }

        holder.edit.setOnClickListener {
            val i = Intent(context, AddLabActivity::class.java)
            i.putExtra("id", 3)
            i.putExtra("hospital", current.hospital)
            i.putExtra("userID", current.userID)
            i.putExtra("labID", current.id)
            i.putExtra("date", current.date)
            i.putExtra("result", current.result)
            i.putExtra("doctor", current.doctor)
            context.startActivity(i)
        }
    }
}