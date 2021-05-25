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
import com.i3k.mywork.adaptadores.PostAdapter
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Post

class post_fragment() : Fragment() {
    private lateinit var adaptador: PostAdapter
    private val database = FirebaseDatabase.getInstance()
    private lateinit var postRef : DatabaseReference
    var listaPost = mutableListOf<Post>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var fragmentView =  inflater.inflate(R.layout.fragment_posts, container, false)

        val recyclerView :RecyclerView = fragmentView.findViewById(R.id.postRV)

        adaptador = PostAdapter(listaPost)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptador

        postRef = database.getReference("groups/LMAD")
        var userNom : String

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (sp in dataSnapshot.children){
                    val autor = sp.child("usuario").getValue()
                    val nivel = sp.child("timestamp").getValue()
                    val titulo = sp.child("title").getValue()
                    val contenido = sp.child("content").getValue()
                    val text1 = autor.toString()
                    val text2 = nivel.toString()
                    val text3 = titulo.toString()
                    val text4 = contenido.toString()
                    listaPost.add(Post(text1, text2, text3, text4))
                }

                if (listaPost.size > 0){
                    adaptador.notifyDataSetChanged()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        return fragmentView

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

    }
}