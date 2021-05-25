package com.i3k.mywork.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.MostrarActivity
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Post
import kotlinx.android.synthetic.main.contacto_individuo.view.*
import kotlinx.android.synthetic.main.post_preview.view.*


class PostAdapter(val listaPost: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.ContactoViewHolder>() {

    class ContactoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun asignarInformacion(postTemp: Post){

            itemView.postUsuario.text = postTemp.postUser
            itemView.postNivel.text = postTemp.postNivel
            itemView.postTitulo.text = postTemp.postTitulo
            itemView.postContenido.text = postTemp.postContenido
        }
    }
    override fun getItemCount(): Int {
        return listaPost.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ContactoViewHolder {
        return PostAdapter.ContactoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_preview, parent, false)
        )
    }
    override fun onBindViewHolder(holder: PostAdapter.ContactoViewHolder, position: Int) {
        holder.asignarInformacion(listaPost[position])
    }

}