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
import com.i3k.mywork.adaptadores.ChatAdapter
import com.i3k.mywork.adaptadores.ContactAdapter
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.Mensaje
import kotlinx.android.synthetic.main.contacto_individuo.*
import kotlinx.android.synthetic.main.fragment_contacts.*

class fragment_posts : Fragment(R.layout.fragment_posts){

}

class fragment_contacts () : Fragment(R.layout.fragment_contacts){

}

class fragment_files : Fragment(R.layout.fragment_posts){

}

class fragment_tasks : Fragment(R.layout.fragment_tasks){

}