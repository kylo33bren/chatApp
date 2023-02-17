package com.example.chatapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity(){
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        edtUserName=findViewById(R.id.editTextUserName)
        edtPassword=findViewById(R.id.editTextPassword)
        edtEmail=findViewById(R.id.editTextEmail)
        buttonSignUp=findViewById(R.id.buttonSignUp)
        buttonSignUp.setOnClickListener{
            val username=edtUserName.text.toString()
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            signUp(email,password)
        }

    }

    private fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }
}