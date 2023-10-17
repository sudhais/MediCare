package com.example.medicare.database

import android.content.Context
import android.widget.Toast
import com.example.medicare.models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelper {
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun createUser(userModel: UserModel, context: Context, onSuccess: () -> Unit, onFailure: (Exception) -> Unit,onUserNull: (Boolean) -> Unit){

        this.getSingleUserData(userModel.user!!){user ->
            if (user == null){
                val taskRef = databaseReference.child("User").child(userModel.user!!)
                taskRef.setValue(userModel)
                    .addOnSuccessListener {
                        onSuccess()
                        onUserNull(true)
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                        onUserNull(false)
                    }
            }else{
                Toast.makeText(context, "user name already exist", Toast.LENGTH_SHORT).show()
                onUserNull(false)
            }
        }
    }


    fun getSingleUserData(id:String, callback: (UserModel?) -> Unit){
        var ref = databaseReference.child("User").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val paycheck = dataSnapshot.getValue(UserModel::class.java)
                    callback(paycheck)
                } else {
                    callback(null) // Teacher not found
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the data retrieval
                callback(null)
            }
        })
    }

    fun validateUser(userModel: UserModel, context: Context, userExist : (Boolean) -> Unit) {

        this.getSingleUserData(userModel.user!!){exist ->
            if(exist != null) {
                if(exist.password.equals(userModel.password)){
                    userExist(true)
                }else {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show()
                    userExist(false)
                }
            }else{
                Toast.makeText(context, "User Name does not exist", Toast.LENGTH_SHORT).show()
                userExist(false)
            }
        }
    }

}