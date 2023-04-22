package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.model.MessagesModel
import com.google.firebase.auth.FirebaseAuth

class MessagesAdapter(
    private var context: Context? = null,
    private var messages: ArrayList<MessagesModel>? = null,
    private var currentUserId: String? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val MSG_TYPE_LEFT = 1
    val MSG_TYPE_RIGHT = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MSG_TYPE_RIGHT) {
          val  view = LayoutInflater.from(context).inflate(R.layout.message_item_right, parent, false)
            return SenderVH(view)
        } else {
           val view = LayoutInflater.from(context).inflate(R.layout.message_item_left, parent, false)
            return ReceiverVH(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message: MessagesModel = messages!![position]
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        when (holder) {
             is SenderVH -> {
                holder.messageText.text = message.text
                holder.dateText.text = message.date
            }
            is ReceiverVH -> {
                holder.messageText.text = message.text
                holder.dateText.text = message.date
            }
        }

    }

    override fun getItemCount(): Int {
        return messages!!.size
    }

    override fun getItemViewType(position: Int): Int {
        currentUserId=FirebaseAuth.getInstance().currentUser?.uid

        return if (messages!![position].senderId.equals(currentUserId)) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFT
        }
    }

    class ReceiverVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: AppCompatTextView = itemView.findViewById(R.id.message_text)
        var dateText: AppCompatTextView = itemView.findViewById(R.id.dateText)

    }

    class SenderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: AppCompatTextView = itemView.findViewById(R.id.message_text)
        var dateText: AppCompatTextView = itemView.findViewById(R.id.dateText)

    }

}