package com.i3k.mywork.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.MostrarActivity
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.contacto_individuo.view.*


class ContactAdapter(val listaContactos : MutableList<Contact>) : RecyclerView.Adapter<ContactAdapter.ContactoViewHolder>() {

    class ContactoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun asignarInformacion(ContactTemp: Contact){
            var contactName: TextView
            var contactID: TextView
            contactName = itemView.findViewById<TextView>(R.id.contactNameTV)
            contactID = itemView.findViewById<TextView>(R.id.contactID)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, MostrarActivity::class.java)
                intent.putExtra("username2", contactName.text)
                intent.putExtra("userID2", contactID.text)

                context.startActivity(intent)
            }
            itemView.contactNameTV.text = ContactTemp.username
            itemView.contactID.text = ContactTemp.id
        }
    }
    override fun getItemCount(): Int {
        return listaContactos.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ContactoViewHolder {
        return ContactAdapter.ContactoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacto_individuo, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ContactAdapter.ContactoViewHolder, position: Int) {
        holder.asignarInformacion(listaContactos[position])
    }

}