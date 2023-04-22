package com.example.chatapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.adapter.MessagesAdapter
import com.example.chatapp.model.MessagesModel
import com.example.chatapp.model.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity:AppCompatActivity() {

    private lateinit var ivProfile : CircleImageView
    private lateinit var username : TextView
    private lateinit var rvMessages : RecyclerView
    private lateinit var etMessage : AppCompatEditText
    private lateinit var ivSend : AppCompatImageView
    private lateinit var userId: String
    private lateinit var userData: RegisterModel
    private lateinit var messages: ArrayList<MessagesModel>
    private lateinit var messageAdapter: MessagesAdapter
    private var mAuth: FirebaseAuth? = null
    private lateinit var messagesRef: DatabaseReference
    private lateinit var usersRef:DatabaseReference

    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        ivProfile = findViewById(R.id.ivProfile)
        username = findViewById(R.id.username)
        rvMessages = findViewById(R.id.rvMessages)
        etMessage = findViewById(R.id.etMessage)
        ivSend = findViewById(R.id.ivSend)

        if (intent.extras!=null){
            userData = intent.getSerializableExtra("data") as RegisterModel
            userId = userData.userID!!
            username.text = userData.username
            Glide.with(ivProfile).load(userData.image)
                .into(ivProfile)
        }

        messages = ArrayList<MessagesModel>()
        messageAdapter = MessagesAdapter(this, messages)
        rvMessages.layoutManager = LinearLayoutManager(this)
        rvMessages.adapter = messageAdapter
        messageAdapter.notifyDataSetChanged()

        mAuth = FirebaseAuth.getInstance()
        messagesRef = FirebaseDatabase.getInstance().getReference("Chats")
        usersRef = FirebaseDatabase.getInstance().getReference("Users")

        ivSend.setOnClickListener {
            sendMessage()
        }

        loadMessages();

    }

    private fun sendMessage() {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = df.format(c)
        val messageText: String = etMessage.text.toString().trim()
        if (!TextUtils.isEmpty(messageText)) {
            val messageId = messagesRef.push().key
            val message =
                MessagesModel(messageId, mAuth!!.currentUser!!.uid, userId, messageText, formattedDate)
            messagesRef.child(messageId!!).setValue(message)
            etMessage.setText("")
        }
    }

    private fun loadMessages() {
        messagesRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messages.clear()
                for (snapshot in dataSnapshot.children) {
                    val message: MessagesModel? = snapshot.getValue(MessagesModel::class.java)
                    if (message?.senderId
                            .equals(mAuth!!.currentUser!!.uid) && message?.receiverId
                            .equals(userId)
                    ) {
                        messages.add(message!!)
                    } else if (message?.senderId.equals(userId) && message?.receiverId
                            .equals(
                                mAuth!!.currentUser!!.uid
                            )
                    ) {
                        messages.add(message!!)
                    }
                }
                messageAdapter.notifyDataSetChanged()
                rvMessages.scrollToPosition(messages.size - 1)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

}