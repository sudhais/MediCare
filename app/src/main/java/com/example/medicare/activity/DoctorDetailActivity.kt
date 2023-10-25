package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.medicare.R
import com.example.medicare.models.Doctor

@Suppress("DEPRECATION")
class DoctorDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_detail)
        val goBack =  findViewById<TextView>(R.id.Go_back)
        // Get the selected doctor's details from the intent
        val doctor = intent.getParcelableExtra<Doctor>("selectedDoctor")

        val toolbar = findViewById<Toolbar>(R.id.toolbarDocDetails)
        toolbar.subtitle = "${doctor?.name}"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Display the doctor's details in TextViews
        val textViewDoctorName = findViewById<TextView>(R.id.textViewDoctorName)
        textViewDoctorName.text = "Doctor's Name: ${doctor?.name}"

        val textViewSpecialization = findViewById<TextView>(R.id.textViewSpecialization)
        textViewSpecialization.text = "Specialization: ${doctor?.specialization}"

        val textViewHospitals = findViewById<TextView>(R.id.textViewHospitals)
        textViewHospitals.text = "Hospitals: ${doctor?.hospitals}"

        val textViewAvailability = findViewById<TextView>(R.id.textViewAvailability)
        textViewAvailability.text = "Availability: ${doctor?.availabilityTime}"

        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        textViewEmail.text = "Email: ${doctor?.email}"

        goBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // Close the current activity and navigate back
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
