package com.i3k.mywork.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.contacto_individuo.view.*


class ContactAdapter(private val listaContactos: MutableList<Contact>) :
   RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun assign(contactos: Contact){
            itemView.contactNameTV.text = contactos.username

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacto_individuo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.assign(listaContactos[position])
    }

    override fun getItemCount(): Int = listaContactos.size

}