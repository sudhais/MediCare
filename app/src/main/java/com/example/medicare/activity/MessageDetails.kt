package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.medicare.R
import com.example.medicare.models.MessageModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MessageDetailActivity : AppCompatActivity() {
    private lateinit var editTextSubjectDetail: EditText
    private lateinit var editTextMessageContent: EditText
    private lateinit var editTextEmailDetail: EditText
    private lateinit var updateButtonMsg: Button
    private lateinit var message: MessageModel // Store the selected message
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_details)

        val spinnerMessageStatus = findViewById<Spinner>(R.id.spinnerMessageStatus)
        val statusOptions = arrayOf("Pending", "Completed")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        // Initialize views
        editTextSubjectDetail = findViewById(R.id.editTextSubjectDetail)
        editTextMessageContent = findViewById(R.id.editTextMessageContent)
        editTextEmailDetail = findViewById(R.id.editTextEmailDetail)
        updateButtonMsg = findViewById(R.id.updateButtonMsg)
        spinnerMessageStatus.adapter = adapter

        // Set an OnItemSelectedListener to handle the selected option
        spinnerMessageStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedStatus = statusOptions[position]
                // Handle the selected status (e.g., update the message status in the database)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        // Retrieve the selected message from the intent
        val subject = intent.getStringExtra("subject")
        val messageM = intent.getStringExtra("messageM")
        val messageEmail = intent.getStringExtra("messageEmail")
        val messageStatus = intent.getStringExtra("messageStatus")

        // Set the message details in the layout
        editTextSubjectDetail.setText(subject)
        editTextEmailDetail.setText(messageM)
        editTextEmailDetail.setText(messageEmail)

        val position = statusOptions.indexOf(messageStatus)
        if (position != -1) {
            spinnerMessageStatus.setSelection(position)
        }

        // Initialize Firebase reference
        dbRef = FirebaseDatabase.getInstance().getReference("messages")

//        val messageId = intent.getStringExtra("tripId") ?: ""

        updateButtonMsg.setOnClickListener {
            val selectedStatus = spinnerMessageStatus.selectedItem.toString()
            val subject = editTextSubjectDetail.text.toString()
            val messageContent = editTextEmailDetail.text.toString()
            val email = editTextEmailDetail.text.toString()

            updateTripDetails(subject, messageContent, email, selectedStatus) { success ->
                if (success) {
                    val intent = Intent(this, AdminHome::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Handle the error, e.g., display a message to the user
                }
            }
        }
    }

    private fun updateTripDetails(
        subject: String,
        messageContent: String,
        email: String,
        selectedStatus: String,
        completion: (Boolean) -> Unit
    ) {
        // Assuming you have the messageId for the specific message
        val messageId = intent.getStringExtra("messageId")

        if (messageId != null) {
            val messageRef = dbRef.child(messageId)

            val updatedMessage = MessageModel(
                messageId = messageId,
                subject = subject,
                messageM = messageContent,
                messageEmail = email,
                messageStatus = selectedStatus
            )

            messageRef.setValue(updatedMessage)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        completion(true) // Success
                    } else {
                        completion(false) // Error
                    }
                }
        }
    }
}
