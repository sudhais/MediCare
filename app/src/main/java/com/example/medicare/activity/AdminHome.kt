package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.medicare.R

class AdminHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val btn_med: Button = findViewById(R.id.buttonMedicine)

        btn_med.setOnClickListener {
            val i = Intent(this, AdminMedHome::class.java)
            startActivity(i)
        }

    }
}

//testing