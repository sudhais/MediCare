package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.medicare.R

class SignIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        var sigup:TextView = findViewById(R.id.txt_signIn)

        sigup.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}