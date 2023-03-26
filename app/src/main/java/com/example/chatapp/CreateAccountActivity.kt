package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity(){
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        auth = Firebase.auth
        buttonSignUp=findViewById(R.id.buttonSignUp)
        buttonSignUp.setOnClickListener{
            signUp()
        }

    }

    private fun signUp() {
        //edtUserName=findViewById(R.id.editTextUserName)
        edtPassword=findViewById(R.id.editTextPassword)
        edtEmail=findViewById(R.id.editTextEmail)

        //val username=edtUserName.text.toString()
        val email=edtEmail.text.toString()
        val password=edtPassword.text.toString()
//        if(username.isEmpty())
//        {
//            edtUserName.error="Please enter your name"
//        }
        if(email.isEmpty())
        {
            edtEmail.error="Please enter your email"
        }
        if(password.isEmpty())
        {
            edtPassword.error="Please enter your password"
        }else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(
                            baseContext, "Authentication success.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
