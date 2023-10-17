package com.example.medicare.models.validations

class UserForm(
    private var user:String,
    private var email:String,
    private var password: String,
    private var rePassword:String
) {

    //validate the user name
    fun validateUser(): validateForm {
        return if(user.isEmpty()){
            validateForm.Empty("Name is empty")
        }else if (user.length <= 6) {
            validateForm.Invalid("Name should minimum 6 letters")
        }else{
            validateForm.Valid
        }
    }

    //validate the email
    fun validateEmail(): validateForm {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return if(email.isEmpty()){
            validateForm.Empty("Email is empty")
        }else if(!email.matches(emailRegex.toRegex())){
            validateForm.Invalid("Invalid email address")
        }else{
            validateForm.Valid
        }
    }

    //validate password
    fun validatePassword(): validateForm {
        return if(password.isEmpty()){
            validateForm.Empty("Username is empty")
        }else if (password.length <= 6) {
            validateForm.Invalid("Name should minimum 8 letters")
        }else{
            validateForm.Valid
        }
    }

    //validate the Re - password
    fun validateRePassword(): validateForm {
        return if(rePassword.isEmpty()){
            validateForm.Empty("password is empty")
        }else if (rePassword.length <= 6) {
            validateForm.Invalid("password should minimum 8 letters")
        }else if (!password.equals(rePassword)) {
            validateForm.Invalid("Password does not match")
        }else{
            validateForm.Valid
        }
    }
}