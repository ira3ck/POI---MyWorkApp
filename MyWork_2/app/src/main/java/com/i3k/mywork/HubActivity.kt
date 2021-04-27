package com.i3k.mywork

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.hub.*

class HubActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hub)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        val unoBTN = findViewById<Button>(R.id.unoChat)
        val todosBTN = findViewById<Button>(R.id.todoChat)

        val userText = findViewById<TextView>(R.id.idUserText)

        //Esto es para revisar si está en modo nocturno
        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES){
            personaHablar.setBackgroundResource(R.drawable.message_container_dark)
        }

        unoBTN.setOnClickListener{
            var text1 = personaHablar.text.toString()
            val usuarios = usersRef.orderByChild("username").equalTo(text1)
            val valueEventListener = object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        val userAdecuado = ds.child("username").getValue(String::class.java)
                        val ayDi = ds.child("id").getValue(String::class.java)

                        if (userAdecuado.equals(text1)){

                            var userID2 = ayDi.toString()

                            val intent = Intent(this@HubActivity, ChatActivity::class.java)

                            intent.putExtra("username", username)
                            intent.putExtra("userID", userID)
                            intent.putExtra("username2", userAdecuado.toString())
                            intent.putExtra("userID2", userID2)
                            intent.putExtra("modo", "solo")

                            startActivity(intent)

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO()
                    //correcto = false
                }
            }
            usuarios.addListenerForSingleValueEvent(valueEventListener)
        }

        todosBTN.setOnClickListener {

        }
    }


}