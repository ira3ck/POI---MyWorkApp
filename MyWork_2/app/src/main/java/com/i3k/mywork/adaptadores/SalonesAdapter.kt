package com.i3k.mywork.adaptadores

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.i3k.mywork.ChatActivity
import com.i3k.mywork.MostrarActivity
import com.i3k.mywork.R
import com.i3k.mywork.SalonActivity
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Grupo
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.contacto_individuo.view.*
import kotlinx.android.synthetic.main.one_group.view.*


class SalonesAdapter(val listaGrupo : MutableList<Grupo>, username : String, userID : String) : RecyclerView.Adapter<SalonesAdapter.ContactoViewHolder>() {

    var usuario = username
    var ayDi = userID

    class ContactoViewHolder(itemView: View, username : String, userID: String): RecyclerView.ViewHolder(itemView){
        var usuario = username
        var ayDi = userID
        fun asignarInformacion(GrupoTemp: Grupo){
            var groupName: TextView
            var maestroName: TextView
            var groupID : TextView
            groupName = itemView.findViewById<TextView>(R.id.groupNameTV)
            maestroName = itemView.findViewById<TextView>(R.id.maestroNameTV)
            groupID = itemView.findViewById<TextView>(R.id.groupIDTV)

            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
                val context = itemView.context
                val intent = Intent(context, SalonActivity::class.java)
                intent.putExtra("username", usuario)
                intent.putExtra("userID", ayDi)
                intent.putExtra("groupID", groupID.text)
                intent.putExtra("groupName", groupName.text)
                intent.putExtra("maestro", maestroName.text)

                context.startActivity(intent)
            }
            itemView.groupNameTV.text = GrupoTemp.nombre
            itemView.maestroNameTV.text = GrupoTemp.maestro
            itemView.groupIDTV.text = GrupoTemp.id
        }
    }
    override fun getItemCount(): Int {
        return listaGrupo.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalonesAdapter.ContactoViewHolder {
        return SalonesAdapter.ContactoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.one_group, parent, false),
            usuario,
            ayDi
        )
    }
    override fun onBindViewHolder(holder: SalonesAdapter.ContactoViewHolder, position: Int) {
        holder.asignarInformacion(listaGrupo[position])
    }

}