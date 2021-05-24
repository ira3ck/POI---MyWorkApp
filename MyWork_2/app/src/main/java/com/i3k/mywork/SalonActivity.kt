package com.i3k.mywork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.i3k.mywork.fragments.*
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.activity_salon.*

class SalonActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String

    fun cambiarFragmento(fragmentoNuevo: Fragment, tag: String){
        val fragmentoAnterior = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentoAnterior==null){
            supportFragmentManager.beginTransaction().replace(R.id.salon_container, fragmentoNuevo).commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salon)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        nombreSalonTV.text = username

        val chatBTN = findViewById<ImageButton>(R.id.chatBTN)
        val postsBTN = findViewById<ImageButton>(R.id.postsBTN)

        cambiarFragmento(fragment_posts(), "fragment_posts")

        postsBTN.setOnClickListener {
            cambiarFragmento(fragment_posts(), "fragment_posts")
        }

        filesBTN.setOnClickListener {
            cambiarFragmento(fragment_files(), "fragment_files")
        }

        chatBTN.setOnClickListener {
            cambiarFragmento(contactos_fragment(), "contact_layout")
        }

        tasksBTN.setOnClickListener {
            cambiarFragmento(fragment_tasks(), "fragment_contacts")
        }


    }
}