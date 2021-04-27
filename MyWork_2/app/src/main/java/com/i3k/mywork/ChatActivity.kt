package com.i3k.mywork

import android.os.Bundle
import android.text.Layout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.i3k.mywork.adaptadores.ChatAdapter
import com.i3k.mywork.modelos.Mensaje


class ChatActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private lateinit var username2: String
    private lateinit var userID2: String

    private val listaMensaje = mutableListOf<Mensaje>()
    private lateinit var adaptador : ChatAdapter

    private val database = FirebaseDatabase.getInstance()
    private lateinit var chatsRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"
        username2 = intent.getStringExtra("username2") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID2 = intent.getStringExtra("userID2") ?: "AAAAAAAAAAAAAAAAAAAA"

        var uniqueChatRef = mutableListOf(userID, userID2)
        uniqueChatRef.sort()
        chatsRef = database.getReference("chats/"+uniqueChatRef[0]+uniqueChatRef[1])

        var idText = findViewById<TextView>(R.id.idUserText)
        idText.text = username2


        val recyclerView :RecyclerView = findViewById(R.id.chatRV)

        adaptador = ChatAdapter(listaMensaje)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador

        val sendBtn = findViewById<ImageButton>(R.id.sendBTN)
        val textoMensaje = findViewById<TextView>(R.id.typeMesTV)

        sendBtn.setOnClickListener{
            val mensaje = textoMensaje.text.toString()
            if (mensaje.isNotEmpty()){
                textoMensaje.text = ""
                sendMessage(Mensaje("0", mensaje, username, ServerValue.TIMESTAMP))
            }
        }


            val postListener = object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Mensaje::class.java)

                listaMensaje.clear()

                for (sp in snapshot.children){
                    var de = sp.child("de").getValue()
                    var contenido = sp.child("contenido").getValue()
                    var fecha = sp.child("timestamp").getValue()
                    var id = sp.child("id").getValue()
                    listaMensaje.add(Mensaje(id.toString(), contenido.toString(), de.toString(), fecha))
                }

                if (listaMensaje.size > 0){
                    adaptador.notifyDataSetChanged()
                    recyclerView.smoothScrollToPosition(listaMensaje.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        chatsRef.addValueEventListener(postListener)
    }

    private fun sendMessage(mensaje: Mensaje){
        val mensajeFirebase = chatsRef.push()
        mensaje.id = mensajeFirebase.key ?: ""
        mensajeFirebase.setValue(mensaje)
    }

}