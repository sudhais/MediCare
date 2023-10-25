package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.medicare.R

class AdminHome : AppCompatActivity() {
    private lateinit var buttonAddDoctor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        buttonAddDoctor = findViewById(R.id.buttonAddDoctor)
        val buttonViewMessage = findViewById<Button>(R.id.ViewMessages)
        val btn_med: Button = findViewById(R.id.buttonMedicine)

        buttonAddDoctor.setOnClickListener {
            val intent = Intent(this, AddDoctorActivity::class.java)
            startActivity(intent)
        }

        buttonViewMessage.setOnClickListener{
            val intent = Intent(this, ViewMessagesAdmin::class.java)
            startActivity(intent)
        }



        btn_med.setOnClickListener {
            val i = Intent(this, AdminMedHome::class.java)
            startActivity(i)
        }

    }
}