package com.i3k.mywork.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.message_chat.view.*

class ChatAdapter (private val listaMensajes : MutableList<Mensaje>) : RecyclerView.Adapter<ChatAdapter.messageViewHolder>() {

    class messageViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun assign(mensaje: Mensaje){
            itemView.mesContent.text = mensaje.contenido
            itemView.mesSender.text = mensaje.de
            itemView.mesTime.text = mensaje.timestamp.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.message_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        holder.assign(listaMensajes[position])
    }

    override fun getItemCount(): Int = listaMensajes.size
}