package com.example.medicare.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.medicare.R
import com.example.medicare.database.FirebaseHelper
import com.example.medicare.models.MedicineModel
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.lang.Exception

class adminMedicine : ComponentActivity() {

    var firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_medicine)

        val medName: EditText = findViewById(R.id.edt_medName)
        val company: EditText = findViewById(R.id.edt_company)
        val description: EditText = findViewById(R.id.edt_description)
        val stock: EditText = findViewById(R.id.edt_stock)
        val price: EditText = findViewById(R.id.edt_price)
        val date: EditText = findViewById(R.id.edt_date)
        val imgview: ImageView = findViewById(R.id.img_view)
        val select : Button = findViewById(R.id.btn_selectImg)
        val save: Button = findViewById(R.id.btn_save)
        var image:String? = ""

        val id:Int = intent.getIntExtra("id",0)

        if(id != 0) {
            val medicine = intent.getSerializableExtra("medicine") as MedicineModel
//            println("${medicine}")
            medName.setText(medicine.name)
            company.setText(medicine.company)
            description.setText(medicine.description)
            stock.setText(medicine.stock.toString())
            price.setText(medicine.price.toString())
            date.setText(medicine.date)
            image = medicine.image

            val bytes = Base64.decode(medicine.image,Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imgview.setImageBitmap(bitmap)
        }




        val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ){result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                val uri = result.data!!.data
                try {
                    val inputStream = contentResolver.openInputStream(uri!!)
                    val myBitmap = BitmapFactory.decodeStream(inputStream)
                    val stream = ByteArrayOutputStream()
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val bytes = stream.toByteArray()
                    image = Base64.encodeToString(bytes, Base64.DEFAULT)
                    imgview.setImageBitmap(myBitmap)
                    inputStream!!.close()
                    Toast.makeText(this,"image selected", Toast.LENGTH_LONG).show()

                }catch (ex: Exception){
                    Toast.makeText(this,ex.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        select.setOnClickListener {

            val myfileintent = Intent(Intent.ACTION_GET_CONTENT)
            myfileintent.setType("image/*")
            ActivityResultLauncher.launch(myfileintent)

        }

        save.setOnClickListener {
            if(id == 0){
                val mid:String = FirebaseDatabase.getInstance().reference.child("Medicine").push().key!!
                val item = MedicineModel(
                    mid,
                    name = medName.text.toString(),
                    company = company.text.toString(),
                    description = description.text.toString(),
                    price = price.text.toString().toDouble(),
                    stock = stock.text.toString().toInt(),
                    date = date.text.toString(),
                    image
                )

                //adding to the fire database
                firebaseHelper.createMedicine(item, {
                    Toast.makeText(this,"Successfully added", Toast.LENGTH_LONG).show()
                    val i = Intent(this, AdminMedHome::class.java)
                    startActivity(i)
                    finish()
                },{
                    Toast.makeText(this,"Failed to add medicine", Toast.LENGTH_LONG).show()
                })

            }else{
                val medicine = intent.getSerializableExtra("medicine") as MedicineModel
                val item = MedicineModel(
                    medicine.medID,
                    name = medName.text.toString(),
                    company = company.text.toString(),
                    description = description.text.toString(),
                    price = price.text.toString().toDouble(),
                    stock = stock.text.toString().toInt(),
                    date = date.text.toString(),
                    image
                )

                firebaseHelper.updateMedicine(item, {
                    Toast.makeText(this,"Successfully updated", Toast.LENGTH_LONG).show()
                    val i = Intent(this, AdminMedHome::class.java)
                    startActivity(i)
                    finish()
                },{
                    Toast.makeText(this,"Failed to update medicine", Toast.LENGTH_LONG).show()
                })

            }



        }


    }
}