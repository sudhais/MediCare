package com.example.medicare.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.adapters.ReminderAdapter
import com.example.medicare.database.FirebaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserReminder : AppCompatActivity() {
    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_reminder)

        var add:FloatingActionButton = findViewById(R.id.float_add)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ReminderAdapter(mutableListOf())
        recyclerView.adapter = adapter

        var userID = intent.getStringExtra("userID")
        firebaseHelper.getUsersReminder(userID!!){reminderList ->
            adapter = ReminderAdapter(reminderList!!)
            recyclerView.adapter = adapter
        }
    }
}