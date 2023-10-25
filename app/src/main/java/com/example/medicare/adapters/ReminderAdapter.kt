package com.example.medicare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.ReminderModel

class ReminderAdapter(private val reminderList: MutableList<ReminderModel>) : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    var firebaseHelper = FirebaseHelper()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name:TextView = itemView.findViewById(R.id.txt_Rname)
        var time:TextView = itemView.findViewById(R.id.txt_Rtime)
        var btn_delete:ImageView = itemView.findViewById(R.id.img_delete)
        var btn_edit:ImageView = itemView.findViewById(R.id.img_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = reminderList[position]
        holder.name.text = current.name
        holder.time.text = current.time.toString()

        holder.btn_edit.setOnClickListener {

        }

        holder.btn_delete.setOnClickListener {

            holder.btn_delete.setImageResource(R.drawable.selected_delete)

            val context = holder.itemView.context
            firebaseHelper.deleteUserReminder(current.rid!!, {
                // Task creation was successful
                Toast.makeText(context, "reminder deleted successfully", Toast.LENGTH_SHORT).show()

            }, { exception ->
                // Task creation failed, handle the error
                Toast.makeText(context, "reminder delete failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })

        }
    }
}