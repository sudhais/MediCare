package com.example.medicare.models

data class CartModel(
    var medID:String? = null,
    var userID:String? = null,
    var name:String? = null,
    var unitPrice:Double? = null,
    var totalPrice:Double? = null,
    var qty:Int? = null,
    var image:String? =null
)