package com.i3k.mywork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.i3k.mywork.R
import com.i3k.mywork.adaptadores.ContactAdapter
import com.i3k.mywork.adaptadores.ContactFoundAdapter
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.fragment_salon_config.*

class contactos_config_fragment(username : String, userID : String, grupoID : String) : Fragment() {
    private lateinit var adaptador: ContactFoundAdapter
    private val database = FirebaseDatabase.getInstance()
    private lateinit var usersRef : DatabaseReference
    var listaContactos = mutableListOf<Contact>()
    private val usuario = username
    private val ayDi = userID
    private val grup = grupoID


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var fragmentView =  inflater.inflate(R.layout.fragment_salon_config, container, false)

        val recyclerView :RecyclerView = fragmentView.findViewById(R.id.foundRV)

        adaptador = ContactFoundAdapter(listaContactos, usuario, ayDi, grup)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador


        var query = database.getReference("users").orderByChild("username")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (sp in dataSnapshot.children){
                    var procede = true
                    val username = sp.child("username").getValue()
                    if(username == usuario){
                        procede = false
                    }
                    val ayDi = sp.child("id").getValue()
                    var estatus = sp.child("status").getValue().toString()
                    if(estatus.equals("null"))
                        estatus ="Invisible"
                    val text1 = username.toString()
                    val text2 = ayDi.toString()
                    if(procede){
                        listaContactos.add(Contact(text2, text1, estatus))
                    }
                }

                if (listaContactos.size > 0){
                    adaptador.notifyDataSetChanged()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

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