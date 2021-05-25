package com.i3k.mywork

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.i3k.mywork.adaptadores.SalonesAdapter
import com.i3k.mywork.fragments.contactos_fragment
import com.i3k.mywork.fragments.grupos_fragment
import com.i3k.mywork.fragments.perfil_fragment
import com.i3k.mywork.modelos.Grupo
import kotlinx.android.synthetic.main.activity_salon.*
import kotlinx.android.synthetic.main.principal_activity.*

class HomeActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private val database = FirebaseDatabase.getInstance()
    private val grupoRef = database.getReference("groups")
    private var writeMode = false

    fun cambiarFragmento(fragmentoNuevo: Fragment, tag: String){
        val fragmentoAnterior = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentoAnterior==null){
            supportFragmentManager.beginTransaction().replace(R.id.home_container, fragmentoNuevo).commit()
        }
    }

    private fun setLYheight(view: View, height: Int) {
        val params = view.layoutParams
        params.height = height
        view.layoutParams = params
    }

    private fun openSquare(){
        setLYheight(salonLY, 450)
        newGroup.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
    }

    private fun closeSquare(){
        setLYheight(salonLY, 0)
        newGroup.setImageResource(R.drawable.ic_baseline_supervised_user_circle_24)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal_activity)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"


        closeSquare()

        newGroup.setOnClickListener {
            var nombrecin = salonNameTB.text.toString()
            if(writeMode and nombrecin.isNotEmpty()){
                crearGrupo(Grupo("", nombrecin, username, userID))
                Toast.makeText(this, "Grupo creado con éxito", Toast.LENGTH_SHORT).show()
                closeSquare()
            }
            else{
                writeMode = true;
                openSquare()
            }

        }
        closeBTN2.setOnClickListener {
            writeMode = false
            closeSquare()
        }

        cambiarFragmento(grupos_fragment(username, userID), "principal_activity")
        homeBTN.setBackgroundColor(0x10FFFFFF.toInt())

        homeBTN.setOnClickListener {
            cambiarFragmento(grupos_fragment(username, userID), "principal_activity")
            transparentar()
            homeBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }

        guauBTN.setOnClickListener {
            //cambiarFragmento(fragment_files(), "fragment_files")
            guauBTN.setBackgroundColor(0x10FFFFFF.toInt())
            Toast.makeText(this, "GUAU", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                guauBTN.setBackgroundColor(0x00FFFFFF.toInt())
            }, 600)
        }

        dmBTN.setOnClickListener {
            //cambiarFragmento(contactos_fragment(username, userID), "contact_layout")
            transparentar()
            dmBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }

        profileBTN.setOnClickListener {
            cambiarFragmento(perfil_fragment(username, userID), "fragment_profile")
            transparentar()
            profileBTN.setBackgroundColor(0x10FFFFFF.toInt())
        }


    }

    private fun crearGrupo(grupo : Grupo){
        val regisUser = grupoRef.push()
        grupo.id = regisUser.key ?: ""
        regisUser.setValue(grupo)
    }

    fun transparentar(){
        homeBTN.setBackgroundColor(0x00FFFFFF.toInt())
        guauBTN.setBackgroundColor(0x00FFFFFF.toInt())
        dmBTN.setBackgroundColor(0x00FFFFFF.toInt())
        profileBTN.setBackgroundColor(0x00FFFFFF.toInt())
    }

}