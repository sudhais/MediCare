package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.adapters.AdminMedAdapter
import com.example.medicare.database.FirebaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminMedHome : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminMedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_med_home)

        val add:FloatingActionButton = findViewById(R.id.float_add)

        recyclerView = findViewById(R.id.rv1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminMedAdapter(mutableListOf())
        recyclerView.adapter = adapter


        firebaseHelper.getAllMedicine { medicineList ->
            adapter = AdminMedAdapter(medicineList!!)
            recyclerView.adapter = adapter
        }

        add.setOnClickListener {
            val i = Intent(this, AdminMedicine::class.java)
            startActivity(i)
        }

        val home: ImageView = findViewById(R.id.img_home)
        val article: ImageView = findViewById(R.id.img_article)
        val lab: ImageView = findViewById(R.id.img_lab)
        val logout: ImageView = findViewById(R.id.img_logout)
        val med: ImageView = findViewById(R.id.img_medicine)

        home.setOnClickListener {
            val i = Intent(this, AdminHome::class.java)
            startActivity(i)
            finish()
        }

        article.setOnClickListener {
            val i = Intent(this, AdminHome::class.java)
            startActivity(i)
            finish()
        }

        lab.setOnClickListener {
            val i = Intent(this, AdminLabTest::class.java)
            startActivity(i)
            finish()
        }

        logout.setOnClickListener {
            val i = Intent(this, SignIn::class.java)
            startActivity(i)
            finish()
        }

        med.setOnClickListener {
            val i = Intent(this, AdminMedHome::class.java)
            startActivity(i)
            finish()
        }
    }
}