package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.Adapters.AdminLabAdapter
import com.example.medicare.R
import com.example.medicare.adapters.AdminMedAdapter
import com.example.medicare.models.LabModel
import com.example.medicare.models.MedicineModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminLabTest : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminLabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_lab_test)

        val add: FloatingActionButton = findViewById(R.id.float_add)
        add.setOnClickListener {
            val i = Intent(this, AddLabActivity::class.java)
            startActivity(i)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminLabAdapter(mutableListOf())
        recyclerView.adapter = adapter

        getAllTest {
            adapter = AdminLabAdapter(it!!)
            recyclerView.adapter = adapter
        }

    }

    fun getAllTest(callback: (MutableList<LabModel>?) -> Unit) {
        var ref: DatabaseReference = FirebaseDatabase.getInstance().reference.child("LabTest")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val labList = mutableListOf<LabModel>()
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val task = dataSnapshot.getValue(LabModel::class.java)
//                            println(task)

                            task?.let { labList.add(it) }
                        }
                        callback(labList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                    callback(null)
                }
            }
        )
    }
}