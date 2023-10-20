package com.example.medicare.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.activity.MedicineDetails
import com.example.medicare.models.MedicineModel

class UserMedAdapter(private var medicineList: MutableList<MedicineModel>) : RecyclerView.Adapter<UserMedAdapter.ViewHolder>() {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.txt_medname)
        var company: TextView = itemView.findViewById(R.id.txt_company)
        var price: TextView = itemView.findViewById(R.id.txt_price)
        var img_view: ImageView = itemView.findViewById(R.id.img_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_med_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = medicineList[position]
        holder.name.text = current.name
        holder.company.text = current.company
        holder.price.text = current.price.toString()

        val bytes = android.util.Base64.decode(current.image,android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.img_view.setImageBitmap(bitmap)

        holder.img_view.setOnClickListener {
            val context = holder.itemView.context
            val i = Intent(context, MedicineDetails::class.java)
            i.putExtra("data", current)
            context.startActivity(i)
        }

    }


}