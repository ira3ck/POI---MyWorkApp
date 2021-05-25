package com.i3k.mywork

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.i3k.mywork.fragments.contactos_config_fragment
import com.i3k.mywork.fragments.contactos_fragment
import com.i3k.mywork.fragments.post_fragment
import kotlinx.android.synthetic.main.activity_salon.*

class SalonActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private lateinit var grupoID: String

    fun cambiarFragmento(fragmentoNuevo: Fragment, tag: String){
        val fragmentoAnterior = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentoAnterior==null){
            supportFragmentManager.beginTransaction().replace(R.id.salon_container, fragmentoNuevo).commit()
        }
    }

    private fun setLYheight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.layoutParams = params
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salon)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"
        grupoID = intent.getStringExtra("groupID") ?: "AAAAAAAAAAAAAAAAAAAA"

        nombreSalonTV.text = username


        val chatBTN = findViewById<ImageButton>(R.id.chatBTN)
        val postsBTN = findViewById<ImageButton>(R.id.postsBTN)
        setLYheight(PostLY, 0)

        addBTN.setOnClickListener {
            setLYheight(PostLY, 1000)
            addBTN.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
        }
        closeBTN.setOnClickListener {
            setLYheight(PostLY, 0)
            addBTN.setImageResource(R.drawable.ic_baseline_add_24)
        }

        cambiarFragmento(post_fragment(), "fragment_posts")

        postsBTN.setOnClickListener {
            cambiarFragmento(post_fragment(), "fragment_posts")
        }

        filesBTN.setOnClickListener {
            //cambiarFragmento(fragment_files(), "fragment_files")
        }

        chatBTN.setOnClickListener {
            cambiarFragmento(contactos_fragment(username, userID), "contact_layout")
        }

        tasksBTN.setOnClickListener {
            cambiarFragmento(contactos_config_fragment(username, userID, grupoID), "fragment_salon_config")
        }


    }


}