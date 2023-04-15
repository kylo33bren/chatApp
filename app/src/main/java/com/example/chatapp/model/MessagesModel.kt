package com.example.chatapp.model

class MessagesModel (
     var id: String? = null,
     var senderId: String? = null,
     var receiverId: String? = null,
     var text: String? = null,
     var date: String? = null
        ) : java.io.Serializable