package com.example.medicare.database

import com.example.medicare.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun createUser(userModel: UserModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val taskRef = databaseReference.child("User").child(userModel.user!!)
        taskRef.setValue(userModel)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}