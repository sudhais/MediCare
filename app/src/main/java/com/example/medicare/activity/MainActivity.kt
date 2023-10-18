package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.medicare.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var userID = intent.getStringExtra("userID")

        var btn_reminder:Button = findViewById(R.id.buttonReminder)

        btn_reminder.setOnClickListener {
            val intent = Intent(this, UserReminder::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }
    }
}
