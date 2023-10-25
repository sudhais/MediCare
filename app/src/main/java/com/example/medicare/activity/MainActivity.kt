package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.medicare.R

class MainActivity : ComponentActivity() {

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val ivRightCorner = findViewById<ImageView>(R.id.ivRightCorner)
        val buttonChat = findViewById<Button>(R.id.buttonChat)
        val buttonDoctor = findViewById<Button>(R.id.buttonDoctorView)


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
        var userID = intent.getStringExtra("userID")
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
