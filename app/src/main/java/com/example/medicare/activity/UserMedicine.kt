package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.adapters.UserMedAdapter
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.MedicineModel
import java.util.Locale

class UserMedicine : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserMedAdapter
    private val medicines  = mutableListOf<MedicineModel>()
    private val originalList = mutableListOf<MedicineModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_medicine)

        val search: TextView = findViewById(R.id.txt_search)

        recyclerView = findViewById(R.id.rv2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserMedAdapter(medicines)
        recyclerView.adapter = adapter

        val btn_cart:ImageView = findViewById(R.id.btn_cart)

        firebaseHelper.getAllMedicine(){medicineList ->
            medicines.clear()
            originalList.clear()
            medicines.addAll(medicineList!!)
            originalList.addAll(medicineList!!)
            adapter.notifyDataSetChanged()
//            adapter = UserMedAdapter(medicineList!!)
//            recyclerView.adapter = adapter

        }

        btn_cart.setOnClickListener {
            val i  = Intent(this, UserCart::class.java)
            startActivity(i)
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()){
                    println(originalList)
                    medicines.clear()
                    medicines.addAll(originalList)
                }else{
                    val newList = medicines.filter { medicine ->
                        medicine.name!!.contains(s.toString(), true)
                    || medicine.company!!.contains(s.toString(), true)
                        //                    || medicine.description!!.contains(query, true)
                    }
                    println(newList)
                    medicines.clear()
                    medicines.addAll(newList)

                }
//                adapter.filter(s.toString())
                adapter.notifyDataSetChanged()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used
            }
        })
    }
}