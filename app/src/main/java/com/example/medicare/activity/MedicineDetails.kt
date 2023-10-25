package com.example.medicare.activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.medicare.R
import com.example.medicare.database.FirebaseHelper

class MedicineDetails : AppCompatActivity() {

    var firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)

        val medID = intent.getStringExtra("medID")

        val name:TextView = findViewById(R.id.txt_name)
        val company:TextView = findViewById(R.id.txt_company)
        val price:TextView = findViewById(R.id.txt_price)
        val description:TextView = findViewById(R.id.txt_desc)
        val date:TextView = findViewById(R.id.txt_date)
        val img: ImageView = findViewById(R.id.img_avatar)
        val cart:ImageView = findViewById(R.id.img_cart)
        val back : Button = findViewById(R.id.btn_back)


        firebaseHelper.getSingleMedicine(medID!!){medicine ->
            name.text = medicine!!.name
            company.text = medicine.company
            price.text = medicine.price.toString()
            description.text = medicine.description
            date.text =medicine.date
            val bytes = android.util.Base64.decode(medicine.image,android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            img.setImageBitmap(bitmap)

        }

        //getting userid from already stored
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")

        firebaseHelper.getSingleUserCart(medID){
            if (it != null){
                cart.setImageResource(R.drawable.baseline_medical_services_24)
            }
        }

        cart.setOnClickListener {
            cart.setImageResource(R.drawable.selected_addcart)
            firebaseHelper.createCart(medID, userId!!, this)

        }

        back.setOnClickListener {
            val i = Intent(this, UserMedicine::class.java)
            startActivity(i)
        }
    }
}