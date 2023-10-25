package com.example.medicare.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.medicare.R
import com.example.medicare.models.MessageModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatPageUser : AppCompatActivity() {
    private lateinit var editTextSubject : EditText
    private lateinit var editTextMessage : EditText
    private lateinit var editTextMessageEmail : EditText
    private lateinit var buttonSubmit : Button


    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page_user)


        val toolbar = findViewById<Toolbar>(R.id.toolbarChat)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextSubject = findViewById(R.id.editTextSubject)
        editTextMessage = findViewById(R.id.editTextMessage)
        editTextMessageEmail =  findViewById(R.id.editTextMessageEmail)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        dbRef = FirebaseDatabase.getInstance().getReference("messages")

        buttonSubmit.setOnClickListener {
            saveMessageData()
        }

    }

    private fun saveMessageData() {
        val subject = editTextSubject.text.toString()
        val messageM = editTextMessage.text.toString()
        val messageEmail = editTextMessageEmail.text.toString()

        if (subject.isEmpty()) {
            editTextSubject.error = "Fill subject field"
        } else if (messageM.isEmpty()) {
            editTextMessage.error = "Fill message field"
        } else if (messageEmail.isEmpty()) {
            editTextMessageEmail.error = "Fill Email field"
        } else {
            // Encode the email address to create a valid Firebase path
            val encodedEmail = messageEmail.replace(".", "-")

            val emailMessageCountsRef = FirebaseDatabase.getInstance().getReference("emailMessageCounts").child(encodedEmail)

            emailMessageCountsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageCount = snapshot.getValue(Int::class.java) ?: 0
                    if (messageCount < 2) {
                        val messageId = dbRef.push().key!!

                        val message = MessageModel(messageId, subject, messageM, messageEmail)

                        dbRef.child(messageId).setValue(message).addOnCompleteListener {
                            // Update the message count
                            emailMessageCountsRef.setValue(messageCount + 1)

                            Toast.makeText(this@ChatPageUser, "Your Message Send Successfully", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@ChatPageUser, MainActivity::class.java)
                            startActivity(intent)
                        }.addOnFailureListener { err ->
                            Toast.makeText(this@ChatPageUser, "Error ${err.message}  ", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@ChatPageUser, "You have exceeded the limit. Go to subscription", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}