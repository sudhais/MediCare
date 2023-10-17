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
import com.example.medicare.models.validations.UserForm
import com.example.medicare.models.validations.validateForm

class SignUp : ComponentActivity() {

    var firebaseHelper = FirebaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var user:EditText = findViewById(R.id.txt_user)
        var email:EditText = findViewById(R.id.txt_email)
        var password:EditText = findViewById(R.id.txt_pass)
        var rePassword:EditText = findViewById(R.id.txt_rePass)
        var signup:Button = findViewById(R.id.btn_signup)
        var signIn:TextView = findViewById(R.id.txt_signin)

        signup.setOnClickListener {

            val userForm = UserForm(
                user.text.toString(),
                email.text.toString(),
                password.text.toString(),
                rePassword.text.toString()
            )

            val userValidation = userForm.validateUser()
            val emailValidation = userForm.validateEmail()
            val passwordValidation = userForm.validatePassword()
            val rePasswordValidation = userForm.validateRePassword()

            var valid = 0

            when(userValidation){
                is validateForm.Valid -> {
                    valid++
                }
                is validateForm.Invalid -> {
                    user.error = userValidation.errorMessage
                }
                is validateForm.Empty -> {
                    user.error = userValidation.errorMessage
                }
            }

            when(emailValidation){
                is validateForm.Valid -> {
                    valid++
                }
                is validateForm.Invalid -> {
                    email.error = emailValidation.errorMessage
                }
                is validateForm.Empty -> {
                    email.error = emailValidation.errorMessage
                }
            }

            when(passwordValidation){
                is validateForm.Valid -> {
                    valid++
                }
                is validateForm.Invalid -> {
                    password.error = passwordValidation.errorMessage
                }
                is validateForm.Empty -> {
                    password.error = passwordValidation.errorMessage
                }
            }


            when(rePasswordValidation){
                is validateForm.Valid -> {
                    valid++
                }
                is validateForm.Invalid -> {
                    rePassword.error = rePasswordValidation.errorMessage
                }
                is validateForm.Empty -> {
                    rePassword.error = rePasswordValidation.errorMessage
                }
            }

            if(valid == 4){
                //displayAlert("success", "successfully registered")

                val user = UserModel(
                    user.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                )
                //add the user into the firebase
                firebaseHelper.createUser(user,this ,{

                },{
                    Toast.makeText(this,"Failed to registered", Toast.LENGTH_LONG).show()
                },{check ->
                    if (check){
                        Toast.makeText(this,"Successfully registered", Toast.LENGTH_LONG).show()
                        intent = Intent(this, SignIn::class.java)
                        startActivity(intent)
                        finish()
                    }


                })
            }

        }

        signIn.setOnClickListener {
            var intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }


}