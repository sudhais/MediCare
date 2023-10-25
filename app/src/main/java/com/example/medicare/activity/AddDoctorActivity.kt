package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicare.models.Doctor
import com.example.medicare.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddDoctorActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextSpecialization: EditText
    private lateinit var editTextHospitals: EditText
    private lateinit var editTextAvailabilityTime: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSave: Button

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_doctor)

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName)
        editTextSpecialization = findViewById(R.id.editTextSpecialization)
        editTextHospitals = findViewById(R.id.editTextHospitals)
        editTextAvailabilityTime = findViewById(R.id.editTextAvailabilityTime)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSave = findViewById(R.id.buttonSave)

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("doctors")
        // Set a click listener for the "Save" button
        buttonSave.setOnClickListener {
            saveDoctorToDatabase()
        }
    }

    private fun saveDoctorToDatabase() {
        // Get doctor information from EditText fields
        val name = editTextName.text.toString().trim()
        val specialization = editTextSpecialization.text.toString().trim()
        val hospitals = editTextHospitals.text.toString().trim()
        val availabilityTime = editTextAvailabilityTime.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        // Check if any field is empty
        if (name.isEmpty() || specialization.isEmpty() || hospitals.isEmpty() || availabilityTime.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val docId = databaseReference.push().key
        // Create a Doctor object
        val doctor = Doctor(docId.toString(), name, specialization, hospitals, availabilityTime, email)

        databaseReference.child(docId.toString()).setValue(doctor)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Doctor added to database.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, AdminHome::class.java)
                    // Start the AddDoctorActivity
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Failed to add doctor to database.", Toast.LENGTH_SHORT).show()
                    Log.e("Firebase", "Error adding doctor: ${task.exception}")
                }
            }
    }

}
