package com.i3k.mywork

import android.content.Intent
import android.content.res.Configuration
import android.media.VolumeShaper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val registrarseBTN = findViewById<Button>(R.id.regisBTN2)

        val usuarioText = findViewById<TextView>(R.id.usernameTB2)
        val contraText = findViewById<TextView>(R.id.passwordTB2)

        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        var correcto = true
        val listaErrores = arrayOf("Introduzca un nombre de usuario. ", "Introduzca una contraseña. ", "Este nombre de usuario ya está en uso ")
        var errorTxt = ""



        registrarseBTN.setOnClickListener {
            val text1 = usuarioText.text.toString()
            val text2 = contraText.text.toString()

            if (text1.isEmpty()){
                errorTxt += listaErrores[0];
                correcto = false;
            }

            if(text2.isEmpty()){
                errorTxt += listaErrores[1]
                correcto = false;
            }

            if(!correcto){
                Toast.makeText(this, errorTxt, Toast.LENGTH_SHORT).show()
            }
            else{
                val regisUser = usersRef.push()
                regisUser.child("username").setValue(text1)
                regisUser.child("password").setValue(text2)
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
            }

        }
        */

        findViewById<Button>(R.id.btnEntrar).setOnClickListener {

            val nombreUsuario = findViewById<TextView>(R.id.txtUsuario).text.toString()

            if(nombreUsuario.isEmpty()){
                Toast.makeText(this,"Falta usuario", Toast.LENGTH_SHORT).show()
            } else{
                val intentChat = Intent(this,ChatActivity::class.java)
                intentChat.putExtra("nombreUsuario", nombreUsuario)

                startActivity(intentChat)
            }
        }
        
    }
}