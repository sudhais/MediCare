package com.example.medicare.testFunc

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChatPageUserCheckTest{

    @Test
    fun whenMessageInputIsValid() {
        // Arrange
        val subject = "Valid Subject"
        val message = "Valid Message"
        val email = "user@example.com"
        val result = ChatPageUserCheck.saveMessageData(subject,message,email)
        Assert.assertTrue(result)
    }

    @Test
    fun whenMessageInputIsInValid() {
        // Arrange
        val subject = ""
        val message = "Valid Message"
        val email = "user@example.com"
        val result = ChatPageUserCheck.saveMessageData(subject,message,email)
        Assert.assertTrue(result)
    }

    @Test
    fun whenMessageInputIsInValid2() {
        // Arrange
        val subject = "Valid Subject"
        val message = ""
        val email = "user@example.com"
        val result = ChatPageUserCheck.saveMessageData(subject,message,email)
        Assert.assertTrue(result)
    }

    @Test
    fun whenMessageLimitCheckFaile() {
        // Arrange
        val limit = 2
        val result = ChatPageUserCheck.saveMessageDataWithLimit(limit)
        Assert.assertTrue(result)
    }

    @Test
    fun whenMessageLimitCheckPass() {
        // Arrange
        val limit = 1
        val result = ChatPageUserCheck.saveMessageDataWithLimit(limit)
        Assert.assertTrue(result)
    }

    @Test
    fun whenMessageLimitCheckPass2() {
        // Arrange
        val limit = 0
        val result = ChatPageUserCheck.saveMessageDataWithLimit(limit)
        Assert.assertTrue(result)
    }


}
