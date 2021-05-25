package com.i3k.mywork

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.i3k.mywork.fragments.contactos_config_fragment
import com.i3k.mywork.fragments.contactos_fragment
import com.i3k.mywork.fragments.post_fragment
import com.i3k.mywork.modelos.Grupo
import com.i3k.mywork.modelos.Post
import kotlinx.android.synthetic.main.activity_salon.*
import kotlinx.android.synthetic.main.principal_activity.*
import java.time.LocalDateTime
import java.util.*

class SalonActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private lateinit var grupoID: String
    private var writeMode = false
    private val database = FirebaseDatabase.getInstance()
    private lateinit var salonRef : DatabaseReference
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
    private fun openSquare(){
        setLYheight(PostLY, 1000)
        addBTN.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
    }

    private fun closeSquare(){
        setLYheight(PostLY, 0)
        addBTN.setImageResource(R.drawable.ic_baseline_add_24)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        salonRef = database.getReference("groups/$grupoID/posts")
        nombreSalonTV.text = groupName


        val chatBTN = findViewById<ImageButton>(R.id.chatBTN)
        val postsBTN = findViewById<ImageButton>(R.id.postsBTN)
        setLYheight(PostLY, 0)

        addBTN.setOnClickListener {
            var tiutlo = tituloPost.text.toString()
            var contenido = contenidoPost.text.toString()
            if(writeMode and tiutlo.isNotEmpty() and contenido.isNotEmpty()){
                crearPost(Post(username, LocalDateTime.now().toString(),tiutlo, contenido, "", userID))
                Toast.makeText(this, "Post creado con éxito", Toast.LENGTH_SHORT).show()
                tituloPost.text.clear()
                contenidoPost.text.clear()
                closeSquare()
            }
            else{
                writeMode = true;
                openSquare()
            }
            openSquare()
            //setLYheight(PostLY, 1000)
            //addBTN.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
        }
        closeBTN.setOnClickListener {
            writeMode = false
            closeSquare()
            //setLYheight(PostLY, 0)
            //addBTN.setImageResource(R.drawable.ic_baseline_add_24)
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
    private fun crearPost(post : Post){
        val regisUser = salonRef.push()
        post.postId = regisUser.key ?: ""
        regisUser.setValue(post)
    }

    fun transparentar(){
        postsBTN.setBackgroundColor(0x00FFFFFF.toInt())
        filesBTN.setBackgroundColor(0x00FFFFFF.toInt())
        chatBTN.setBackgroundColor(0x00FFFFFF.toInt())
        tasksBTN.setBackgroundColor(0x00FFFFFF.toInt())
    }


}