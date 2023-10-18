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
import com.example.medicare.models.MedicineModel

class MedicineDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_details)

        var medicine = intent.getSerializableExtra("data") as MedicineModel

        var name:TextView = findViewById(R.id.txt_name)
        var company:TextView = findViewById(R.id.txt_company)
        var price:TextView = findViewById(R.id.txt_price)
        var description:TextView = findViewById(R.id.txt_desc)
        var date:TextView = findViewById(R.id.txt_date)
        var img: ImageView = findViewById(R.id.img_avatar)
        var cart:ImageView = findViewById(R.id.img_cart)
        var back : Button = findViewById(R.id.btn_back)

        name.text = medicine.name
        company.text = medicine.company
        price.text = medicine.price.toString()
        description.text = medicine.description
        date.text =medicine.date
        val bytes = android.util.Base64.decode(medicine.image,android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        img.setImageBitmap(bitmap)

        //getting userid from already stored
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")

        println(userId)

        cart.setOnClickListener {
            cart.setImageResource(R.drawable.selected_addcart)
        }

        back.setOnClickListener {
            var i = Intent(this, UserMedicine::class.java)
            startActivity(i)
        }
    }
}