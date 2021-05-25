package com.i3k.mywork.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.i3k.mywork.ChatActivity
import com.i3k.mywork.R
import com.i3k.mywork.adaptadores.ContactAdapter
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.contact_layout.*
import kotlinx.android.synthetic.main.contact_layout.view.*

class contactos_fragment(username : String, userID : String, groupID : String, groupName : String) : Fragment() {
    private lateinit var adaptador: ContactAdapter
    private val database = FirebaseDatabase.getInstance()
    private lateinit var usersRef : DatabaseReference
    var listaContactos = mutableListOf<Contact>()
    private val usuario = username
    private val ayDi = userID
    private val grupoID = groupID
    private val grupoName = groupName


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var fragmentView =  inflater.inflate(R.layout.contact_layout, container, false)

        val recyclerView :RecyclerView = fragmentView.findViewById(R.id.contactosRV)

        adaptador = ContactAdapter(listaContactos, usuario, ayDi)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador

        usersRef = database.getReference("users")

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                for (sp in dataSnapshot.children){
                    val username = sp.child("username").getValue()
                    val ayDi = sp.child("id").getValue()
                    var estatus = sp.child("status").getValue().toString()
                    if(estatus.equals("null"))
                        estatus ="Invisible"
                    val text1 = username.toString()
                    val text2 = ayDi.toString()
                    listaContactos.add(Contact(text2, text1, estatus))
                }

                if (listaContactos.size > 0){
                    adaptador.notifyDataSetChanged()
                    //recyclerView.smoothScrollToPosition(listaContactos.size - 1)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        fragmentView.salonChatBTN.setOnClickListener { view->
            val intent = Intent(activity, ChatActivity::class.java)

            intent.putExtra("username", usuario)
            intent.putExtra("userID", ayDi)
            intent.putExtra("groupID", grupoID)
            intent.putExtra("groupName", grupoName)
            intent.putExtra("modo", "todo")

            startActivity(intent)
        }

        return fragmentView

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        /*contactosRV.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layout = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adaptador = ContactAdapter(listaContactos)
        }*/
        /*layout = LinearLayoutManager(activity)
        adaptador = ContactAdapter(listaContactos)
        contactosRV.layoutManager = layout
        contactosRV.adapter = adaptador*/
    }
}