package com.i3k.mywork.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.i3k.mywork.ChatActivity
import com.i3k.mywork.ProfileConfigActivity
import com.i3k.mywork.R
import com.i3k.mywork.adaptadores.ContactFoundAdapter
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class perfil_fragment(username : String, userID : String) : Fragment() {
    private val database = FirebaseDatabase.getInstance()
    private lateinit var usersRef : DatabaseReference
    private val usuario = username
    private val ayDi = userID


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var fragmentView =  inflater.inflate(R.layout.fragment_profile, container, false)

        fragmentView.usernameProfileTV.text = usuario

        fragmentView.configBTN.setOnClickListener { view->
            val intent = Intent(activity, ProfileConfigActivity::class.java)

            intent.putExtra("username", usuario)
            intent.putExtra("userID", ayDi)

            startActivity(intent)
        }



        return fragmentView

    }

}