package com.example.chatapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var textViewCreateAccount:TextView
    private lateinit var buttonLongin:Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        edtPassword=findViewById(R.id.editTextPassword)
        edtEmail=findViewById(R.id.editTextEmail)
        buttonLongin=findViewById(R.id.buttonLongin)
        textViewCreateAccount=findViewById(R.id.textViewCreateAccount)

        buttonLongin.setOnClickListener {

            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email.isEmpty()) {
                edtEmail.error="Please enter your email"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.error="Please enter email in correct format"
            } else if (password.isEmpty()) {
                edtPassword.error="Please enter your password"
            }else{

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent=Intent(this,UsersActivity::class.java)
                            startActivity(intent)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }

        textViewCreateAccount.setOnClickListener{
            val intent= Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload()
        }
    }

}
