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
import com.i3k.mywork.HomeActivity
import com.i3k.mywork.R
import com.i3k.mywork.adaptadores.ContactAdapter
import com.i3k.mywork.adaptadores.SalonesAdapter
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Grupo
import java.security.acl.Group

class grupos_fragment(username : String, userID : String) : Fragment() {
    private lateinit var adaptador: SalonesAdapter
    private val database = FirebaseDatabase.getInstance()
    private var groupRef = database.getReference("groups")
    var listaGrupo = mutableListOf<Grupo>()
    private val usuario = username
    private val ayDi = userID


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var fragmentView =  inflater.inflate(R.layout.grupo_layout, container, false)

        val recyclerView :RecyclerView = fragmentView.findViewById(R.id.grupoRV)

        adaptador = SalonesAdapter(listaGrupo, usuario, ayDi)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador

        val grupos2 = groupRef
        val valueEventListener2 = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    var procede = false
                    var miembros = ds.child("members").children
                    for (nds in miembros){
                        if(nds.child("username").getValue().toString() == usuario){
                            procede = true
                            break
                        }
                    }
                    val nomb = ds.child("nombre").getValue().toString()
                    val maestro = ds.child("maestro").getValue().toString()
                    val maestroID = ds.child("maestroID").getValue().toString()
                    val ayDi = ds.child("id").getValue().toString()

                    if(procede){
                        listaGrupo.add(Grupo(ayDi, nomb, maestro, maestroID))
                    }
                }

                if (listaGrupo.size > 0){
                    adaptador.notifyDataSetChanged()
                    //recyclerView.smoothScrollToPosition(listaContactos.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //correcto = false
            }
        }
        grupos2.addListenerForSingleValueEvent(valueEventListener2)

        val grupos = groupRef.orderByChild("maestroID").equalTo(ayDi)
        val valueEventListener = object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){
                    val nomb = ds.child("nombre").getValue().toString()
                    val maestro = ds.child("maestro").getValue().toString()
                    val maestroID = ds.child("maestroID").getValue().toString()
                    val ayDi = ds.child("id").getValue().toString()
                    listaGrupo.add(Grupo(ayDi, nomb, maestro, maestroID))
                }

                if (listaGrupo.size > 0){
                    adaptador.notifyDataSetChanged()
                    //recyclerView.smoothScrollToPosition(listaContactos.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //correcto = false
            }
        }
        grupos.addListenerForSingleValueEvent(valueEventListener)

        return fragmentView

    }

}