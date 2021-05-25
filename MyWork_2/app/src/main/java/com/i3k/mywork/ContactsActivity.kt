package com.i3k.mywork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.i3k.mywork.adaptadores.ContactAdapter
import com.i3k.mywork.modelos.Contact


class ContactsActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String

    private val listaContactos = mutableListOf<Contact>()
    private lateinit var adaptador : ContactAdapter

    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_layout)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        val recyclerView : RecyclerView = findViewById(R.id.contactosRV)

        //adaptador = ContactAdapter(listaContactos)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador


        listaContactos.add(Contact("sdbjasbdfk", "sdufwidy"))
        listaContactos.add(Contact("sdbjasbdfk", "sdufwidy"))
        listaContactos.add(Contact("sdbjasbdfk", "sdufwidy"))
        listaContactos.add(Contact("sdbjasbdfk", "sdufwidy"))


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(Contact::class.java)

                //listaContactos.clear()

                for (sp in dataSnapshot.children){
                    val username = sp.child("username").getValue()
                    val ayDi = sp.child("id").getValue()
                    val text1 = username.toString()
                    val text2 = ayDi.toString()
                    listaContactos.add(Contact(text2, text1))
                }
                if(listaContactos.size>0){
                    adaptador.notifyDataSetChanged()
                    //ContactsRV.smoothScrollToPosition(listaContactos.size-1)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        usersRef.addValueEventListener(postListener)

        llenarContactos()

    }

    private fun llenarContactos(){




    }
}