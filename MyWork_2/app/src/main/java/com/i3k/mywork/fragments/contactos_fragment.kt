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
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.contact_layout.*

class contactos_fragment() : Fragment() {
    private var layout: RecyclerView.LayoutManager? = null
    private var adaptador: RecyclerView.Adapter<ContactAdapter.ViewHolder>? = null
    var fragmentView : View? = null
    private var database : FirebaseDatabase? = null
    private var usersRef : DatabaseReference? = null
    var listaContactos = mutableListOf<Contact>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        fragmentView =  inflater.inflate(R.layout.contact_layout, container, false)

        database = FirebaseDatabase.getInstance()
        usersRef = FirebaseDatabase.getInstance().getReference("users")

        usersRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(Contact::class.java)

                for (sp in dataSnapshot.children){
                    val username = sp.child("username").getValue()
                    val ayDi = sp.child("id").getValue()
                    val text1 = username.toString()
                    val text2 = ayDi.toString()
                    listaContactos.add(Contact(text2, text1))
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        )

        return fragmentView

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        contactosRV.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layout = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adaptador = ContactAdapter(listaContactos)
        }
    }
}