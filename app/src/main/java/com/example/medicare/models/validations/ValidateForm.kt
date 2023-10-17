package com.example.medicare.models.validations

sealed class validateForm {

    //if empty rows
    data class Empty(val errorMessage:String): validateForm()

    //if invalid rows
    data class Invalid(val errorMessage: String): validateForm()

    //if validate success
    object Valid: validateForm()
}
