package com.example.medicare.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.medicare.Adapters.MessageAdapter
import com.example.medicare.R
import com.example.medicare.models.MessageModel // Import your Message class
import com.google.firebase.database.*

class ViewMessagesAdmin : AppCompatActivity() {
    private lateinit var recyclerViewMessages: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var messageAdapter: MessageAdapter

    private lateinit var databaseReference: DatabaseReference
    private lateinit var messageList: ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_messages_admin)

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages)
        searchView = findViewById(R.id.searchView)

        // Initialize RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerViewMessages.layoutManager = layoutManager
        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList)
        recyclerViewMessages.adapter = messageAdapter

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("messages")

        // Add a ValueEventListener to retrieve messages
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(MessageModel::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }
                messageAdapter.submitList(messageList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })

        // Implement item click listeners for editing and deleting items
        messageAdapter.setOnItemClickListener(object : MessageAdapter.OnItemClickListener {
            override fun onEditClick(position: Int, message: MessageModel) {
                // Handle edit button click
                // You can start a new activity to edit the selected trip
                val intent = Intent(this@ViewMessagesAdmin, MessageDetailActivity::class.java)
                intent.putExtra("messageId", message.messageId)
                intent.putExtra("subject", message.subject)
                intent.putExtra("messageM", message.messageM)
                intent.putExtra("messageEmail", message.messageEmail)
                intent.putExtra("messageStatus", message.messageStatus)
                startActivity(intent)
            }

            override fun onDeleteClick(position: Int, message: MessageModel) {
                // Handle delete button click
                val messageId = message.messageId

                // Get a reference to the specific trip to be deleted in the Firebase database
                val messageRef = databaseReference.child(messageId.toString())

                // Remove the trip data from the database
                messageRef.removeValue()
            }
        })
        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                messageAdapter.filter.filter(newText)
                return true
            }
        })

    }
}
