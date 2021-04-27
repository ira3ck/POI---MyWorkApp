package com.i3k.mywork.adaptadores

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.style.BackgroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.message_chat.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter (private val listaMensajes : MutableList<Mensaje>) : RecyclerView.Adapter<ChatAdapter.messageViewHolder>() {

    class messageViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun assign(mensaje: Mensaje){
            itemView.mesContent.text = mensaje.contenido
            itemView.mesSender.text = mensaje.de

            val dateFormater = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault())
            val fecha = dateFormater.format(Date(mensaje.timestamp as Long))

            itemView.mesTime.text = fecha

            val params = itemView.mesContainer.layoutParams

            if (mensaje.mine) {

                val newParams = FrameLayout.LayoutParams(
                        params.width,
                        params.height,
                        Gravity.END
                )
                itemView.mesContainer.setBackgroundResource(R.drawable.outcomming_message)
                itemView.mesContainer.setPadding(12,12,65,12)
                itemView.mesContainer.layoutParams = newParams

            } else {

                val newParams = FrameLayout.LayoutParams(
                        params.width,
                        params.height,
                        Gravity.START
                )
                itemView.mesContainer.setBackgroundResource(R.drawable.incomming_message)
                itemView.mesContainer.setPadding(65,12,12,12)
                itemView.mesContainer.layoutParams = newParams

            }
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