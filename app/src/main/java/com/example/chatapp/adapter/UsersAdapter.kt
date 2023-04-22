package com.example.chatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.ChatActivity
import com.example.chatapp.R
import com.example.chatapp.model.RegisterModel

class UsersAdapter (
    private var context: Context? = null,
    private var list: ArrayList<RegisterModel?>? = null) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userImage: ImageView
        var username: TextView

        init {
            userImage = itemView.findViewById(R.id.user_image)
            username = itemView.findViewById(R.id.user_username)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.users_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: RegisterModel = list!![position]!!
        holder.username.text = data.username
        Glide.with(holder.userImage).load(data.image).centerCrop()
            .into(holder.userImage)

        holder.itemView.setOnClickListener { v: View? ->
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("data", data)
            context!!.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}