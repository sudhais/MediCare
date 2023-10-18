package com.example.medicare.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.adapters.AdminMedAdapter
import com.example.medicare.adapters.UserMedAdapter
import com.example.medicare.database.FirebaseHelper

class UserMedicine : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserMedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_medicine)

        recyclerView = findViewById(R.id.rv2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserMedAdapter(mutableListOf())
        recyclerView.adapter = adapter

        firebaseHelper.getAllMedicine(){medicineList ->
            adapter = UserMedAdapter(medicineList!!)
            recyclerView.adapter = adapter
        }
    }
}