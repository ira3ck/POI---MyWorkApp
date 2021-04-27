package com.i3k.mywork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.i3k.mywork.adaptadores.ChatAdapter
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    private val listaMensajes = mutableListOf<Mensaje>()
    private val adaptador = ChatAdapter(listaMensajes)
    private lateinit var nombreUsuario: String

    // creamos una variable con acceso a nuestra base de datos de firebase
    private val database = FirebaseDatabase.getInstance()
    private val chatsRef = database.getReference("chats")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        nombreUsuario = intent.getStringExtra("nombreUsuario") ?: "sin_Nombre"

        recyclerView.adapter = adaptador

        imageButton2.setOnClickListener{
            val mensaje = editTextTextPersonName.text.toString()
            if(mensaje.isNotEmpty()){
                editTextTextPersonName.text.clear()

                enviarMensaje(Mensaje("", mensaje, nombreUsuario, ServerValue.TIMESTAMP))
            }
        }

        recibirMensajes()
    }

    private fun enviarMensaje(mensaje: Mensaje) {
        val mensajeFirebase = chatsRef.push()
        mensaje.id = mensajeFirebase.key ?: ""

        mensajeFirebase.setValue(mensaje)
    }

    private fun recibirMensajes(){
        chatsRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listaMensajes.clear()

                for(snap in snapshot.children){
                    val mensaje: Mensaje = snap.getValue(Mensaje::class.java) as Mensaje
                    mensaje.esMio = mensaje.de == nombreUsuario
                    listaMensajes.add(mensaje)
                }

                if(listaMensajes.size > 0){
                    adaptador.notifyDataSetChanged()
                    recyclerView.smoothScrollToPosition(listaMensajes.size)
                }
            }
        })
    }

}
