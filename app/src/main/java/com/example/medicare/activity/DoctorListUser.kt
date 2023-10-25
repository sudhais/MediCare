package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.medicare.Adapters.DoctorAdapter
import com.example.medicare.R
import com.example.medicare.models.Doctor
import com.google.firebase.database.*

class DoctorListUser : AppCompatActivity() {
    private lateinit var recyclerViewDoctors: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var doctorAdapter: DoctorAdapter

    private lateinit var databaseReference: DatabaseReference
    private lateinit var doctorList: ArrayList<Doctor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list_user)

        val toolbar = findViewById<Toolbar>(R.id.toolbarDoctor)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewDoctors = findViewById(R.id.recyclerViewDoctors)
        searchView = findViewById(R.id.searchViewDoctor)

        // Initialize RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerViewDoctors.layoutManager = layoutManager
        doctorList = ArrayList()
        doctorAdapter = DoctorAdapter(doctorList)
        recyclerViewDoctors.adapter = doctorAdapter

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("doctors")

        // Add a ValueEventListener to retrieve doctors
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorList.clear()
                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctor::class.java)
                    if (doctor != null) {
                        doctorList.add(doctor)
                    }
                }
                doctorAdapter.submitList(doctorList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })

        // Implement item click listeners for viewing doctor details
        doctorAdapter.setOnItemClickListener(object : DoctorAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, doctor: Doctor) {
                // Handle item click
                val intent = Intent(this@DoctorListUser, DoctorDetailActivity::class.java)
                intent.putExtra("selectedDoctor", doctor)
                startActivity(intent)
            }
        })

        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                doctorAdapter.filter.filter(newText)
                return true
            }
        })
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
