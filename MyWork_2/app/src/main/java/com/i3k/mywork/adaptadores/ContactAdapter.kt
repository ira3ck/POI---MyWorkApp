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


class ContactAdapter(val listaContactos : MutableList<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    ///////-- DATOS (INICIO) --/////////

    private val kode = arrayOf("d116df5",
            "36ffc75", "f5cfe78", "5b87628",
            "db8d14e", "9913dc4", "e120f96",
            "466251b")

    private val kategori = arrayOf("Kekayaan", "Teknologi",
            "Keluarga", "Bisnis",
            "Keluarga", "Hutang",
            "Teknologi", "Pidana")

    private val isi = arrayOf("pertanyaan 9",
            "pertanyaan 11", "pertanyaan 17", "test forum",
            "pertanyaan 12", "pertanyaan 18", "pertanyaan 20",
            "pertanyaan 21")

    ///////-- DATOS (FIN) --/////////


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var contactName: TextView
        var contactID: TextView

        init {
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
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.contacto_individuo, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        //viewHolder.contactName.text = kode[i]
        viewHolder.contactName.text = listaContactos[i].username
        viewHolder.contactID.text = listaContactos[i].id
    }

    override fun getItemCount(): Int {
        return kode.size
    }

}