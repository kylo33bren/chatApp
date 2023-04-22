package com.example.chatapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.StateSet.TAG
import com.bumptech.glide.Glide
import com.example.chatapp.model.RegisterModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var txtLogin: TextView
    private lateinit var edtUserName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var buttonSignUp: AppCompatButton
    private lateinit var imgProfile: CircleImageView
    private var imgUri : String = ""
    private lateinit var uriImage : Uri

    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dbReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Going Good...")
        progressDialog.setMessage("It takes Just a few Seconds... ")
        progressDialog.setIcon(R.drawable.happy)
        progressDialog.setCancelable(false)

        auth = FirebaseAuth.getInstance()
        dbReference = FirebaseDatabase.getInstance("https://chatapp-5dc5c-default-rtdb.firebaseio.com").getReference("UserRegistrationData")
        storageReference = FirebaseStorage.getInstance().getReference("UserRegistrationImages")


        buttonSignUp = findViewById(R.id.buttonSignUp)
        edtUserName = findViewById(R.id.editTextUserName)
        edtPassword = findViewById(R.id.editTextPassword)
        edtEmail = findViewById(R.id.editTextEmail)
        imgProfile = findViewById(R.id.profile_image)
        txtLogin = findViewById(R.id.textViewLogin)

        buttonSignUp.setOnClickListener {
            signUp()
        }

        txtLogin.setOnClickListener {
            startActivity(Intent(this@CreateAccountActivity, MainActivity::class.java))
            finish()
        }

        imgProfile.setOnClickListener {
            ImagePicker.with(this@CreateAccountActivity)
                .maxResultSize(512, 512)
                .start();

        }

    }

    private fun signUp() {

        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        val username = edtUserName.text.toString()
        if (username.isEmpty()) {
            edtUserName.error = "Please enter your name"
        }else if (password.isEmpty()) {
            edtPassword.error = "Please enter your password"
        }else if (email.isEmpty()) {
            edtEmail.error = "Please enter your email"
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.error="Please enter email in correct format"
        }else if (imgUri.isEmpty()) {
            Toast.makeText(this@CreateAccountActivity, "Please choose image", Toast.LENGTH_SHORT).show()
        }else {
            progressDialog.show()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Setting Image Path
                        val imageReference = uriImage.lastPathSegment?.let {
                            storageReference.child(it)
                        }
                        //Putting image to Firebase Storage
                        imageReference?.putFile(uriImage)?.addOnSuccessListener {
                            //Download url of Upload image
                            imageReference.downloadUrl.addOnSuccessListener {
                                //Storing the image uri to String variable
                                val uploadedImgURL: String = it.toString()
                                //Creating model variable and passing data
                                val userData = RegisterModel(username, email, auth.currentUser?.uid, uploadedImgURL)
                                //Storing User Data to Firebase Database
                                dbReference.child(auth.currentUser!!.uid).setValue(userData).addOnSuccessListener {
                                    progressDialog.dismiss()
                                    Toast.makeText(this@CreateAccountActivity, "Successfully Registered", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                            }
                        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            //Getting Gallery & CameraImage uri
            uriImage = data!!.data!!
            Glide.with(imgProfile).load(uriImage)
                .into(imgProfile)
            try {
                imgUri = uriImage.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
