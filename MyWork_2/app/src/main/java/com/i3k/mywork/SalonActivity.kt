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
    private lateinit var groupName: String
    private lateinit var maestro: String

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
        groupName = intent.getStringExtra("groupName") ?: "AAAAAAAAAAAAAAAAAAAA"
        maestro = intent.getStringExtra("maestro") ?: "AAAAAAAAAAAAAAAAAAAA"

        if(maestro == username){
            tasksBTN.setImageResource(R.drawable.ic_baseline_edit_note_24)
        }

        nombreSalonTV.text = groupName


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
        postsBTN.setBackgroundColor(0x10FFFFFF.toInt())

        postsBTN.setOnClickListener {
            cambiarFragmento(post_fragment(), "fragment_posts")
            transparentar()
            postsBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }

        filesBTN.setOnClickListener {
            //cambiarFragmento(fragment_files(), "fragment_files")
            transparentar()
            filesBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }

        chatBTN.setOnClickListener {
            cambiarFragmento(contactos_fragment(username, userID, grupoID, groupName), "contact_layout")
            transparentar()
            chatBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }

        tasksBTN.setOnClickListener {
            cambiarFragmento(contactos_config_fragment(username, userID, grupoID), "fragment_salon_config")
            transparentar()
            tasksBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }


    }

    fun transparentar(){
        postsBTN.setBackgroundColor(0x00FFFFFF.toInt())
        filesBTN.setBackgroundColor(0x00FFFFFF.toInt())
        chatBTN.setBackgroundColor(0x00FFFFFF.toInt())
        tasksBTN.setBackgroundColor(0x00FFFFFF.toInt())
    }


}