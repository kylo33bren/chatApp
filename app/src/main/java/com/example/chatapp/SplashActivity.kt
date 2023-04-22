package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var textviewAppName: TextView
    private lateinit var imageLogo: ImageView
    var fromTop: Animation? = null
    var fromBottom: Animation? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        textviewAppName = findViewById(R.id.textviewAppName);
        imageLogo = findViewById(R.id.logo_image);

        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop)
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom)
        //Setting animations on views
        imageLogo.animation = fromTop
        textviewAppName.animation = fromBottom

        //Calling the thread and setting the time 3 seconds so that activity can stay here and then move to next
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                } catch (e: InterruptedException) {
                    Toast.makeText(this@SplashActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        thread.start()

    }
}