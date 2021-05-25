package com.i3k.mywork.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.ChatActivity
import com.i3k.mywork.ConfirmActivity
import com.i3k.mywork.MostrarActivity
import com.i3k.mywork.R
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.contacto_individuo.view.*


class ContactFoundAdapter(val listaContactos : MutableList<Contact>, username : String, userID : String, groupID: String) : RecyclerView.Adapter<ContactFoundAdapter.ContactoViewHolder>() {

    var usuario =username
    var ayDi = userID
    var grup = groupID

    class ContactoViewHolder(itemView: View, username : String, userID: String, groupID : String): RecyclerView.ViewHolder(itemView){
        var usuario = username
        var ayDi = userID
        var grup = groupID
        fun asignarInformacion(ContactTemp: Contact){
            var contactName: TextView
            var contactID: TextView
            var contactStatus: TextView
            contactName = itemView.findViewById<TextView>(R.id.contactNameTV)
            contactID = itemView.findViewById<TextView>(R.id.contactID)
            contactStatus = itemView.findViewById<TextView>(R.id.contactStatus)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, ConfirmActivity::class.java)
                intent.putExtra("username", usuario)
                intent.putExtra("userID", ayDi)
                intent.putExtra("username2", contactName.text)
                intent.putExtra("userID2", contactID.text)
                intent.putExtra("group", grup)

                context.startActivity(intent)
            }
            itemView.contactNameTV.text = ContactTemp.username
            itemView.contactID.text = ContactTemp.id
            itemView.contactStatus.text = ContactTemp.status
        }
    }
    override fun getItemCount(): Int {
        return listaContactos.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactFoundAdapter.ContactoViewHolder {
        return ContactFoundAdapter.ContactoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacto_individuo, parent, false),
            usuario,
            ayDi,
            grup
        )
    }
    override fun onBindViewHolder(holder: ContactFoundAdapter.ContactoViewHolder, position: Int) {
        holder.asignarInformacion(listaContactos[position])
    }

}