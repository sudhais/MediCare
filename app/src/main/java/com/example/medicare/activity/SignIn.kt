package com.example.medicare.activity

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

        var sigup:TextView = findViewById(R.id.txt_signup)
        var user:EditText = findViewById(R.id.txt_user)
        var password:EditText = findViewById(R.id.txt_pass)
        var signin:Button = findViewById(R.id.btn_signin)

        sigup.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        signin.setOnClickListener {
            var validUser = UserModel(
                user.text.toString(),
                "",
                password.text.toString()
            )

            if (validUser.user.equals("admin") && validUser.password.equals("admin")){
                Toast.makeText(this,"Successfully loged in to admin page", Toast.LENGTH_LONG).show()
                intent = Intent(this, AdminPage::class.java)
                startActivity(intent)
                finish()

            }else{
                firebaseHelper.validateUser(validUser,this){check ->
                    if (check){
                        Toast.makeText(this,"Successfully loged in", Toast.LENGTH_LONG).show()
                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }




    }
}