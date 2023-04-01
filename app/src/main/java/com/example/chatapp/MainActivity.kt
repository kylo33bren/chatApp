package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.ui.ChatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var buttonCreateAccount:Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        edtPassword=findViewById(R.id.editTextPassword)
        edtEmail=findViewById(R.id.editTextEmail)
        val email=edtEmail.text.toString()
        val password=edtPassword.text.toString()
        val buttonLongin=findViewById<View>(R.id.buttonLongin)
        buttonLongin.setOnClickListener {

            //val intent=Intent(this,ChatActivity::class.java)
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        val user = auth.currentUser
//                        val intent=Intent(this,ChatActivity::class.java)
//                        startActivity(intent)
//
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//
//                    }
//                }
            buttonCreateAccount=findViewById<View>(R.id.buttonLongin) as Button
            this.buttonCreateAccount.setOnClickListener{
                val intent= Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }

        }
        buttonCreateAccount=findViewById<View>(R.id.buttonCreateAccount) as Button
        this.buttonCreateAccount.setOnClickListener{
            val intent= Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }


    }


    private fun login(user: FirebaseUser?) {
        val email=edtEmail.text.toString()
        val password=edtPassword.text.toString()
        if(email.isEmpty())
        {
            edtEmail.error="Please enter your email"
        }
        if(password.isEmpty())
        {
            edtPassword.error="Please enter your password"
        }
        if (user != null) {
            if (user.email==email) {


            }
        }else
        {
            edtEmail.error="Please make sure you input correctly"
        }
    }

}
