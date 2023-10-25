//package com.example.medicare.Adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import android.widget.TextView
//import com.example.medicare.R
//import com.example.medicare.models.Doctor
//
//class CustomDoctorAdapter(private val context: Context, private val doctorList: List<Doctor>) : BaseAdapter() {
//
//    override fun getCount(): Int {
//        return doctorList.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return doctorList[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val itemView = inflater.inflate(R.layout.item_doctor, parent, false)
//
//        val currentDoctor = doctorList[position]
//
//        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
//        val textViewSpecialization: TextView = itemView.findViewById(R.id.textViewSpecialization)
//
//        textViewName.text = currentDoctor.name
//        textViewSpecialization.text = currentDoctor.specialization
//
//        // Set other doctor details here
//
//        return itemView
//    }
//}
