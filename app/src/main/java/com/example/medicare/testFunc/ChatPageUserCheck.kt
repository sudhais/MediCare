package com.example.medicare.testFunc

object ChatPageUserCheck {

    fun saveMessageData(subject: String, messageM: String, messageEmail: String): Boolean {
        return !(subject.isEmpty() || messageM.isEmpty() || messageEmail.isEmpty())
    }

    fun saveMessageDataWithLimit(limitCheck: Int): Boolean {
        return (limitCheck < 2)
    }

    // Add other member functions and properties as needed
}