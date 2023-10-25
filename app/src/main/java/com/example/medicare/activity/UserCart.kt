package com.example.medicare.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.R
import com.example.medicare.adapters.CartAdapter
import com.example.medicare.database.FirebaseHelper

class UserCart : AppCompatActivity() {

    private val firebaseHelper = FirebaseHelper()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)

        recyclerView = findViewById(R.id.rv3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(mutableListOf())
        recyclerView.adapter = adapter

        val btnMedicine: ImageView = findViewById(R.id.btn_medicine)

        val total:TextView = findViewById(R.id.txt_totalPrice)

        val imgHome: ImageView = findViewById(R.id.img_home)

        imgHome.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        //getting userid from already stored
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")

        firebaseHelper.getAllUserCart(userId!!){cartList ->
            adapter = CartAdapter(cartList!!)
            recyclerView.adapter = adapter
        }

        firebaseHelper.getUserTotal(userId) {
            val value = "%.3f".format(it).toDouble()
            total.text = "Total:           ${value}"
            println(it)
        }

        btnMedicine.setOnClickListener {
            val i = Intent(this,UserMedicine::class.java)
            startActivity(i)
        }


    }
}