package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.adapters.AdminMedAdapter
import com.example.medicare.adapters.ReminderAdapter
import com.example.medicare.database.FirebaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminMedHome : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminMedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_med_home)

        var add:FloatingActionButton = findViewById(R.id.float_add)

        recyclerView = findViewById(R.id.rv1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminMedAdapter(mutableListOf())
        recyclerView.adapter = adapter


        firebaseHelper.getAllMedicine(){medicineList ->
            adapter = AdminMedAdapter(medicineList!!)
            recyclerView.adapter = adapter
        }

        add.setOnClickListener {
            var i = Intent(this, adminMedicine::class.java)
            startActivity(i)
        }


    }
}