package com.example.medicare.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.medicare.R
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.UserModel

class SignIn : ComponentActivity() {

    var firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val sigup:TextView = findViewById(R.id.txt_signup)
        val user:EditText = findViewById(R.id.txt_user)
        val password:EditText = findViewById(R.id.txt_pass)
        val signin:Button = findViewById(R.id.btn_signin)

        sigup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        signin.setOnClickListener {
            val validUser = UserModel(
                user.text.toString(),
                "",
                password.text.toString()
            )

            if (validUser.user.equals("admin") && validUser.password.equals("admin")){
                Toast.makeText(this,"Successfully loged in to admin page", Toast.LENGTH_LONG).show()
                intent = Intent(this, AdminHome::class.java)
                startActivity(intent)
                finish()

            }else{
                firebaseHelper.validateUser(validUser,this){check ->
                    if (check){
                        Toast.makeText(this,"Successfully loged in", Toast.LENGTH_LONG).show()

                        //store the user id
                        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("userId", validUser.user)
                        editor.apply()

                        intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userID", validUser.user)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}