package com.example.medicare.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.activity.AdminMedicine
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.MedicineModel

class AdminMedAdapter(private val medicineList: MutableList<MedicineModel>) : RecyclerView.Adapter<AdminMedAdapter.ViewHolder>() {

    var firebaseHelper = FirebaseHelper()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.txt_medname)
        var company: TextView = itemView.findViewById(R.id.txt_company)
        var price: TextView = itemView.findViewById(R.id.txt_price)
        var date: TextView = itemView.findViewById(R.id.txt_date)
        var stock: TextView = itemView.findViewById(R.id.txt_stock)
        var btn_delete: ImageView = itemView.findViewById(R.id.btn_delete)
        var btn_edit: ImageView = itemView.findViewById(R.id.btn_edit)
        var img_view: ImageView = itemView.findViewById(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_med_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
       return  medicineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = medicineList[position]
        holder.name.text = current.name
        holder.company.text = current.company
        holder.date.text = current.date
        holder.price.text = current.price.toString()
        holder.stock.text = current.stock.toString()

        val bytes = android.util.Base64.decode(current.image,android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.img_view.setImageBitmap(bitmap)

        holder.btn_delete.setOnClickListener {
            holder.btn_delete.setImageResource(R.drawable.selected_delete)

            val context = holder.itemView.context
            firebaseHelper.deleteMedicine(current.medID!!, {
                // Task creation was successful
                Toast.makeText(context, "Medicine deleted successfully", Toast.LENGTH_SHORT).show()

            }, { exception ->
                // Task creation failed, handle the error
                Toast.makeText(context, "Medicine delete failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        }

        holder.btn_edit.setOnClickListener {
            val context = holder.itemView.context
            val i = Intent(context, AdminMedicine::class.java)
            i.putExtra("id", 3)
            i.putExtra("medID", current.medID )
            context.startActivity(i)
        }

        holder.img_view.setOnClickListener {
            val context = holder.itemView.context
            val i = Intent(context, AdminMedicine::class.java)
            i.putExtra("id", 3)
            i.putExtra("medID", current.medID )
            context.startActivity(i)
        }

    }

}