package com.i3k.mywork.adaptadores

<<<<<<< HEAD
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
=======
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Mensaje


class ChatAdapter (private val listaMensajes: MutableList<Mensaje>) :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun asignarInformacion(mensaje: Mensaje){
            itemView.findViewById<TextView>(R.id.mesSender).text = mensaje.de
            itemView.findViewById<TextView>(R.id.mesContent).text = mensaje.contenido

            val params = itemView.findViewById<LinearLayout>(R.id.contenedorMensaje).layoutParams

            if(mensaje.esMio){
                val newParams = FrameLayout.LayoutParams(
                        params.width,
                        params.height,
                        Gravity.START
                )
                itemView.findViewById<LinearLayout>(R.id.contenedorMensaje).layoutParams = newParams
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.message_chat,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.asignarInformacion(listaMensajes[position])
>>>>>>> main
    }

    override fun getItemCount(): Int = listaMensajes.size
}