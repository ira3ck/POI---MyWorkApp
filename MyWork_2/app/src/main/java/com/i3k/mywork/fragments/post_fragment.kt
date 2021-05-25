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

class post_fragment(grupoID : String) : Fragment() {
    private lateinit var adaptador: PostAdapter
    private val database = FirebaseDatabase.getInstance()
    private lateinit var postRef : DatabaseReference
    var listaPost = mutableListOf<Post>()
    private val grupo = grupoID


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

        postRef = database.getReference("groups/$grupo/posts")
        var userNom : String

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listaPost.clear()
                for (sp in dataSnapshot.children){
                    val autor = sp.child("postUser").getValue()
                    val nivel = sp.child("postTime").getValue()
                    val titulo = sp.child("postTitulo").getValue()
                    val contenido = sp.child("postContenido").getValue()
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