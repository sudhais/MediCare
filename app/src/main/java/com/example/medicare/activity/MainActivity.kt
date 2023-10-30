package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import com.example.medicare.R

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val ivRightCorner = findViewById<ImageView>(R.id.ivRightCorner)
        val buttonChat = findViewById<Button>(R.id.buttonChat)
        val buttonDoctor = findViewById<Button>(R.id.buttonDoctorView)
        val buttonLab: Button = findViewById(R.id.buttonLab)
        val buttonArticle: Button = findViewById(R.id.buttonArticle)

        buttonLab.setOnClickListener {
            val intent = Intent(this, UserLabTest::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }

        buttonArticle.setOnClickListener{
            val i = Intent(this, ArticleMainActivity::class.java)
            startActivity(i)
        }

        ivRightCorner.setOnClickListener {
            // Handle the click on the ImageView (logout)
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }

        buttonChat.setOnClickListener{
            val intent = Intent(this, ChatPageUser::class.java)
            startActivity(intent)
        }

        buttonDoctor.setOnClickListener {
            val intent = Intent(this, DoctorListUser::class.java)
            startActivity(intent)
        }

        val btn_reminder:Button = findViewById(R.id.buttonReminder)
        val btn_medicine:Button = findViewById(R.id.buttonMedicine)

        btn_reminder.setOnClickListener {
            val intent = Intent(this, UserReminder::class.java)
            startActivity(intent)
        }

        btn_medicine.setOnClickListener {
            val i = Intent(this, UserMedicine::class.java)
            startActivity(i)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this,SignIn::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
