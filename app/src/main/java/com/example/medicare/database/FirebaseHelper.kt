package com.example.medicare.database

import android.content.Context
import android.widget.Toast
import com.example.medicare.models.CartModel
import com.example.medicare.models.MedicineModel
import com.example.medicare.models.ReminderModel
import com.example.medicare.models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await

class FirebaseHelper {
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    //User Functions
    fun createUser(
        userModel: UserModel,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
        onUserNull: (Boolean) -> Unit
    ) {

        this.getSingleUserData(userModel.user!!) { user ->
            if (user == null) {
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
            } else {
                Toast.makeText(context, "user name already exist", Toast.LENGTH_SHORT).show()
                onUserNull(false)
            }
        }
    }


     fun getSingleUserData(id: String, callback: (UserModel?) -> Unit) {
        val ref = databaseReference.child("User").child(id)
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

    fun validateUser(userModel: UserModel, context: Context, userExist: (Boolean) -> Unit) {

        this.getSingleUserData(userModel.user!!) { exist ->
            if (exist != null) {
                if (exist.password.equals(userModel.password)) {
                    userExist(true)
                } else {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show()
                    userExist(false)
                }
            } else {
                Toast.makeText(context, "User Name does not exist", Toast.LENGTH_SHORT).show()
                userExist(false)
            }
        }
    }


    //User Reminder functions

    fun createUserReminder(
        reminder: ReminderModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val taskRef = databaseReference.child("Reminder").child(reminder.rid!!)
//        task.id = taskRef.key // Assign the generated key as the task ID
        taskRef.setValue(reminder)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun getUsersReminder(userID: String, callback: (MutableList<ReminderModel>?) -> Unit) {
        val ref = databaseReference.child("Reminder")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val reminderList = mutableListOf<ReminderModel>()
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val task = dataSnapshot.getValue(ReminderModel::class.java)
//                            println(task)
                            if (task!!.userID.equals(userID)) {
                                task.let { reminderList.add(it) }
                            }

                        }
                        callback(reminderList)
                    }
                    callback(reminderList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            }
        )
    }

    fun deleteUserReminder(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        databaseReference.child("Reminder").child(id).removeValue()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    //Medicine Fuctions
    fun createMedicine(
        medicine:MedicineModel,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val taskRef = databaseReference.child("Medicine").child(medicine.medID!!)
        //        task.id = taskRef.key // Assign the generated key as the task ID
        taskRef.setValue(medicine)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

//    suspend fun createMedicine(medicine:MedicineModel):Boolean {
//       return try {
//            val taskRef = databaseReference.child("Medicine").child(medicine.medID!!)
//            taskRef.setValue(medicine).await()
//           true
//        }catch (e:Exception){
//            false
//        }
//
//    }

    fun getAllMedicine(callback: (MutableList<MedicineModel>?) -> Unit) {
        val ref = databaseReference.child("Medicine")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val medicineList = mutableListOf<MedicineModel>()
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val task = dataSnapshot.getValue(MedicineModel::class.java)
//                            println(task)

                            task?.let { medicineList.add(it) }
                        }
                        callback(medicineList)
                    }
                    callback(medicineList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            }
        )
    }

    fun getSingleMedicine(id:String, callback: (MedicineModel?) -> Unit){
        val ref = databaseReference.child("Medicine").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val medicine = dataSnapshot.getValue(MedicineModel::class.java)
                    callback(medicine)
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

    fun deleteMedicine(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        databaseReference.child("Medicine").child(id).removeValue()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateMedicine(medicine: MedicineModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        databaseReference.child("Medicine").child(medicine.medID!!).setValue(medicine)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


                    //Cart functions
    fun createCart(medID:String,
                   userID: String,
                   context: Context
                    ){
                        this.getSingleUserCart(medID){
                            if(it == null){
                                this.getSingleMedicine(medID){medicine ->
                                    val cart = CartModel(
                                        medicine!!.medID,
                                        userID,
                                        medicine.name,
                                        medicine.price,
                                        medicine.price,
                                        1,
                                        medicine.image
                                    )
                                    val cartRef = databaseReference.child("Cart").child("items").child(medID)
                                    cartRef.setValue(cart)
                                        .addOnSuccessListener {
                                            Toast.makeText(context,"Successfully added to the cart", Toast.LENGTH_LONG).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context,"Failed to add cart", Toast.LENGTH_LONG).show()
                                        }
                                }
                            }else{
                                Toast.makeText(context,"medicine already added to the cart", Toast.LENGTH_LONG).show()
                            }
                        }
                    }


    fun getAllUserCart(userID: String, callback: (MutableList<CartModel>?) -> Unit) {
        val ref = databaseReference.child("Cart").child("items")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val cartList = mutableListOf<CartModel>()
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val cart = dataSnapshot.getValue(CartModel::class.java)
//                            println(task)
                            if (cart!!.userID.equals(userID)) {
                                cart.let { cartList.add(it) }
                            }

                        }
                        callback(cartList)
                    }
                    callback(cartList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            }
        )
    }

    fun getSingleUserCart(id:String, callback: (CartModel?) -> Unit){
        val ref = databaseReference.child("Cart").child("items").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val cart = dataSnapshot.getValue(CartModel::class.java)
                    callback(cart)
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the data retrieval
                callback(null)
            }
        })
    }

    fun updateUserCart(cartModel: CartModel) {
        databaseReference.child("Cart").child("items").child(cartModel.medID!!).setValue(cartModel)
            .addOnSuccessListener {
            }
    }

    fun deleteUserCart(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        databaseReference.child("Cart").child("items").child(id).removeValue()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }


    fun getUserTotal(userID: String, callback: (Double?) -> Unit) {
        val ref = databaseReference.child("Cart").child("items")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var total = 0.0
                        for (dataSnapshot in snapshot.children) {
                            val cart = dataSnapshot.getValue(CartModel::class.java)
//                            println(task)
                            if (cart!!.userID.equals(userID)) {
                                total += cart.unitPrice!! * cart.qty!!
                            }

                        }
                        callback(total)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            }
        )
    }



}