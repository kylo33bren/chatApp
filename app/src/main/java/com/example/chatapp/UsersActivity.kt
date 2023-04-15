package com.example.chatapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adapter.UsersAdapter
import com.example.chatapp.model.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UsersActivity : AppCompatActivity() {

    private lateinit var rvUsers : RecyclerView
    private lateinit var tvLogout : TextView
    private lateinit var progressDialog: ProgressDialog
    private var adapter: UsersAdapter? = null
    private var usersList: ArrayList<RegisterModel?>? = null
    private var dbReference: DatabaseReference? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        rvUsers = findViewById(R.id.rvUsers);
        tvLogout = findViewById(R.id.tvLogout);
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Going Good...")
        progressDialog.setMessage("It takes Just a few Seconds... ")
        progressDialog.setIcon(R.drawable.happy)
        progressDialog.setCancelable(false)

        rvUsers.setHasFixedSize(true)
        rvUsers.layoutManager = LinearLayoutManager(this)

        usersList = ArrayList()
        adapter = UsersAdapter(this, usersList)
        rvUsers.adapter = adapter

        tvLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@UsersActivity, MainActivity::class.java))
            finish()
        }

        dbReference = FirebaseDatabase.getInstance("https://chatapp-5dc5c-default-rtdb.firebaseio.com").getReference("UserRegistrationData")
        gettingUsersList()

    }

    private fun gettingUsersList(){
        progressDialog.show()
        dbReference?.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss()
                    usersList?.clear()
                    for (snapshot in dataSnapshot.children) {
                        val list: RegisterModel? = snapshot.getValue(RegisterModel::class.java)
                        if (!list?.userID?.contains(FirebaseAuth.getInstance().currentUser!!.uid)!!) {
                            usersList?.add(list)
                        }
                    }
                    if (usersList!!.isEmpty()){
                        Toast.makeText(this@UsersActivity, "No User Available for Chat", Toast.LENGTH_SHORT)
                            .show()
                    }
                    adapter!!.notifyDataSetChanged()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@UsersActivity, "No User Available", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog.dismiss()
            }
        })
    }

}