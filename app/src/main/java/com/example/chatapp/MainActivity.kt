package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var buttonCreateAccount:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonLongin=findViewById<View>(R.id.buttonLongin)
        buttonLongin.setOnClickListener {
            //val intent=Intent(this,ChatActivity::class.java)
        }
        buttonCreateAccount=findViewById<View>(R.id.buttonCreateAccount) as Button
        buttonCreateAccount.setOnClickListener{

            val intent= Intent(this,CreateAccountActivity::class.java)
            startService(intent)
        }


    }
}