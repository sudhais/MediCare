package com.example.medicare.models

data class MedicineModel(
    var medID:String? = null,
    var name:String? = null,
    var company:String? = null,
    var description:String? = null,
    var price:Double? = null,
    var stock:Int? = null,
    var date:String? = null,
    var image:String? = null
)
