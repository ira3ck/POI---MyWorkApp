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


        usersRef = database.getReference("users")

        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                for (sp in dataSnapshot.children){
                    val username = sp.child("username").getValue()
                    val ayDi = sp.child("id").getValue()
                    val text1 = username.toString()
                    val text2 = ayDi.toString()
                    listaContactos.add(Contact(text2, text1))
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