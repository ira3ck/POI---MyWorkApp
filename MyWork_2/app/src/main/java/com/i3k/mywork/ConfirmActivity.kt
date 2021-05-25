package com.i3k.mywork

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.media.VolumeShaper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.i3k.mywork.modelos.Contact
import com.i3k.mywork.modelos.User
import kotlinx.android.synthetic.main.confirm_activity.*


class ConfirmActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String
    private lateinit var username2: String
    private lateinit var userID2: String
    private lateinit var groupID: String

    val database = FirebaseDatabase.getInstance()
    lateinit var membersRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_activity)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"
        username2 = intent.getStringExtra("username2") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID2 = intent.getStringExtra("userID2") ?: "AAAAAAAAAAAAAAAAAAAA"
        groupID = intent.getStringExtra("group") ?: "AAAAAAAAAAAAAAAAAAAA"

        membersRef = database.getReference("groups/$groupID/members")

        studentNameTV.text = username2

        //Esto es para revisar si está en modo nocturno
        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES){
        }

        confirmBTN.setOnClickListener{
            registrarAlumno(Contact(userID2, username2))
            Toast.makeText(this, "Añadido a $username2 al salón", Toast.LENGTH_SHORT).show()
            finish()
        }

        cancelBTN.setOnClickListener {
            finish()
        }

    }

    private fun registrarAlumno(alumno : Contact){
        val regisUser = membersRef.push()
        alumno.id = regisUser.key ?: ""
        regisUser.setValue(alumno)
    }
}