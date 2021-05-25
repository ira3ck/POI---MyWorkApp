package com.i3k.mywork

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.i3k.mywork.adaptadores.ChatAdapter
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private lateinit var username2: String
    private lateinit var userID2: String
    private lateinit var modo: String
    private lateinit var groupID: String
    private lateinit var groupName: String
    private var cifrado = true

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
        modo = intent.getStringExtra("modo") ?: "AAAAAAAAAAAAAAAAAAAA"
        groupID = intent.getStringExtra("groupID") ?: "AAAAAAAAAAAAAAAAAAAA"
        groupName = intent.getStringExtra("groupName") ?: "AAAAAAAAAAAAAAAAAAAA"

        var idText = findViewById<TextView>(R.id.idUserText)

        if (modo.equals("solo")){
            var uniqueChatRef = mutableListOf(userID, userID2)
            uniqueChatRef.sort()
            chatsRef = database.getReference("chats/"+uniqueChatRef[0]+uniqueChatRef[1])

            idText.text = username2
        }
        else {
            chatsRef = database.getReference("chats/$groupID")

            idText.text = "Chat $groupName"
        }

        val recyclerView :RecyclerView = findViewById(R.id.chatRV)


        adaptador = ChatAdapter(listaMensaje)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador

        val sendBtn = findViewById<ImageButton>(R.id.sendBTN)
        val textoMensaje = findViewById<TextView>(R.id.typeMesTV)

        sendBtn.setOnClickListener{
            var mensaje = textoMensaje.text.toString()
            if (cifrado){
                mensaje = Encriptado.cifrar(mensaje,"lallave333")
            }
            if (mensaje.isNotEmpty()){
                textoMensaje.text = ""
                sendMessage(Mensaje("0", mensaje, username, ServerValue.TIMESTAMP, cifrado.toString()))
            }
        }

        cifradoBtn.setOnClickListener {
            if (cifrado){
                cifradoBtn.setImageResource(R.drawable.ic_baseline_lock_open_24)
                Toast.makeText(this, "Cifrado de mensajes Desactivado", Toast.LENGTH_SHORT).show()
                cifrado = false
            }
            else{
                cifradoBtn.setImageResource(R.drawable.ic_baseline_lock_24)
                Toast.makeText(this, "Cifrado de mensajes Activado", Toast.LENGTH_SHORT).show()
                cifrado = true
            }
        }


        val postListener = object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Mensaje::class.java)

                listaMensaje.clear()

                for (sp in snapshot.children){
                    var de = sp.child("de").getValue()
                    var contenido = sp.child("contenido").getValue().toString()
                    var fecha = sp.child("timestamp").getValue()
                    var id = sp.child("id").getValue()
                    if (sp.child("cifrado").getValue().toString() == "true"){
                       contenido = Encriptado.descifrar(contenido,"lallave333")
                    }
                    var Mes = Mensaje(id.toString(), contenido, de.toString(), fecha, cifrado.toString())
                    Mes.mine = Mes.de == username
                    listaMensaje.add(Mes)
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