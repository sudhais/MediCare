package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicare.R
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.LabModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddLabActivity : AppCompatActivity() {

    val firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lab)

        var hospital : EditText = findViewById(R.id.edt_HospitalName)
        var userID : EditText = findViewById(R.id.edt_user)
        var doctor : EditText = findViewById(R.id.edt_doctor)
        var result : EditText = findViewById(R.id.edt_result)
        var date : EditText = findViewById(R.id.edt_date)
        var save: Button = findViewById(R.id.btn_save)

        val id:Int = intent.getIntExtra("id",0)

        if(id != 0) {
            hospital.setText(intent.getStringExtra("hospital"))
            userID.setText(intent.getStringExtra("userID"))
            doctor.setText(intent.getStringExtra("doctor"))
            result.setText(intent.getStringExtra("result"))
            date.setText(intent.getStringExtra("date"))
        }

        save.setOnClickListener {

            if(id != 0) {
                val id = intent.getStringExtra("labID")
                val test = LabModel(
                    id,
                    userID.text.toString(),
                    hospital.text.toString(),
                    doctor.text.toString(),
                    result.text.toString(),
                    date.text.toString()
                )

                var ref = FirebaseDatabase.getInstance().reference.child("LabTest").child(id!!)
                ref.setValue(test)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Succesfully updated", Toast.LENGTH_LONG).show()
                        val i = Intent(this, AdminLabTest::class.java)
                        startActivity(i)
                        finish()
                    }


            }else{
                val user = userID.text.toString()

                firebaseHelper.getSingleUserData(user){
                    if (it != null){
                        var key = FirebaseDatabase.getInstance().reference.child("LabTest").push().key
                        val test = LabModel(
                            key,
                            userID.text.toString(),
                            hospital.text.toString(),
                            doctor.text.toString(),
                            result.text.toString(),
                            date.text.toString()
                        )

                        var ref = FirebaseDatabase.getInstance().reference.child("LabTest").child(key!!)
                        ref.setValue(test)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Succesfully Added", Toast.LENGTH_LONG).show()
                                val i = Intent(this, AdminLabTest::class.java)
                                startActivity(i)
                                finish()
                            }
                    }else{
                        Toast.makeText(this, "Invalid UserID", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}