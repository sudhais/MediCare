package com.example.medicare.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.Adapters.AdminLabAdapter
import com.example.medicare.Adapters.UserLabAdapter
import com.example.medicare.R
import com.example.medicare.models.CartModel
import com.example.medicare.models.LabModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserLabTest : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserLabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_lab_test)

        //getting userid from already stored
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")

        recyclerView = findViewById(R.id.rv4)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserLabAdapter(mutableListOf())
        recyclerView.adapter = adapter

        this.getAllUserLabTest(userId!!){
            adapter = UserLabAdapter(it!!)
            recyclerView.adapter = adapter
        }

        val home: ImageView = findViewById(R.id.img_home)
        val article: ImageView = findViewById(R.id.img_article)
        val cart: ImageView = findViewById(R.id.btn_cart)
        val logout: ImageView = findViewById(R.id.img_logout)
        val med: ImageView = findViewById(R.id.img_medicine)

        home.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        article.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        cart.setOnClickListener {
            val i = Intent(this, UserCart::class.java)
            startActivity(i)
            finish()
        }

        logout.setOnClickListener {
            val i = Intent(this, SignIn::class.java)
            startActivity(i)
            finish()
        }

        med.setOnClickListener {
            val i = Intent(this, UserMedicine::class.java)
            startActivity(i)
            finish()
        }


    }

    fun getAllUserLabTest(userID:String, callback:(MutableList<LabModel>) -> Unit){
        var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val ref = databaseReference.child("LabTest")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val labList = mutableListOf<LabModel>()
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val test = dataSnapshot.getValue(LabModel::class.java)
//                            println(task)
                            if (test!!.userID.equals(userID)) {
                                test.let { labList.add(it) }
                            }

                        }
                    }
                    callback(labList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            }
        )
    }
}