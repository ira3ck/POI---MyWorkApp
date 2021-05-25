package com.i3k.mywork

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.i3k.mywork.adaptadores.ChatAdapter
import com.i3k.mywork.modelos.Mensaje


class ProfileConfigActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private lateinit var username2: String
    private lateinit var userID2: String
    private lateinit var modo: String
    private lateinit var groupID: String
    private lateinit var groupName: String

    private val listaMensaje = mutableListOf<Mensaje>()
    private lateinit var adaptador : ChatAdapter

    private val database = FirebaseDatabase.getInstance()
    private lateinit var chatsRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_config)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        /*var idText = findViewById<TextView>(R.id.idUserText)

        if (modo.equals("solo")){
            var uniqueChatRef = mutableListOf(userID, userID2)
            uniqueChatRef.sort()
            chatsRef = database.getReference("chats/"+uniqueChatRef[0]+uniqueChatRef[1])

            idText.text = username2
        }
        else {
            chatsRef = database.getReference("chats/$groupID")

            idText.text = "Chat $groupName"
        }*/

    }

    private fun sendMessage(mensaje: Mensaje){
        val mensajeFirebase = chatsRef.push()
        mensaje.id = mensajeFirebase.key ?: ""
        mensajeFirebase.setValue(mensaje)
    }

}