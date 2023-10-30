package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.medicare.R

class AdminHome : AppCompatActivity() {
    private lateinit var buttonAddDoctor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        buttonAddDoctor = findViewById(R.id.buttonAddDoctor)
//        val buttonArticle: Button = findViewById(R.id.buttonArticle)
        val buttonViewMessage = findViewById<Button>(R.id.ViewMessages)
        val btn_med: Button = findViewById(R.id.buttonMedicine)
        val btnLab: Button = findViewById(R.id.buttonLab)

        btnLab.setOnClickListener {
            val intent = Intent(this, AdminLabTest::class.java)
            startActivity(intent)
            finish()
        }

        buttonAddDoctor.setOnClickListener {
            val intent = Intent(this, AddDoctorActivity::class.java)
            startActivity(intent)
        }

        buttonViewMessage.setOnClickListener{
            val intent = Intent(this, ViewMessagesAdmin::class.java)
            startActivity(intent)
        }

//        buttonArticle.setOnClickListener {
//            val i = Intent(this, MainArticleAdminActivity::class.java)
//            startActivity(i)
//        }

        btn_med.setOnClickListener {
            val i = Intent(this, AdminMedHome::class.java)
            startActivity(i)
        }

        val home: ImageView = findViewById(R.id.img_home)
        val article: ImageView = findViewById(R.id.img_article)
        val lab: ImageView = findViewById(R.id.img_lab)
        val logout: ImageView = findViewById(R.id.img_logout)
        val med: ImageView = findViewById(R.id.img_medicine)

        home.setOnClickListener {
            val i = Intent(this, AdminHome::class.java)
            startActivity(i)
            finish()
        }

        article.setOnClickListener {
            val i = Intent(this, MainArticleAdminActivity::class.java)
            startActivity(i)
            finish()
        }

        lab.setOnClickListener {
            val i = Intent(this, AdminLabTest::class.java)
            startActivity(i)
            finish()
        }

        logout.setOnClickListener {
            val i = Intent(this, SignIn::class.java)
            startActivity(i)
            finish()
        }

        med.setOnClickListener {
            val i = Intent(this, AdminMedHome::class.java)
            startActivity(i)
            finish()
        }

    }
}