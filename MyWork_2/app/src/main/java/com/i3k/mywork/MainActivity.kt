package com.i3k.mywork

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.media.VolumeShaper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.i3k.mywork.modelos.User


class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_ses)

        val registrarseBTN = findViewById<Button>(R.id.regisBTN)
        val iniciarBTN = findViewById<Button>(R.id.inSesBTN)

        val usuarioText = findViewById<TextView>(R.id.usernameTB)
        val contraText = findViewById<TextView>(R.id.passwordTB)

        val contexto = this
        var correcto = true
        val listaErrores = arrayOf("Introduzca un nombre de usuario. ", "Introduzca una contraseña. ", "Usuario o Contraseña incorrecta. ", "Estamos experimentando con problemas en la Base de Datos. ")
        var errorTxt = ""

        //Esto es para revisar si está en modo nocturno
        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES){
            usuarioText.setBackgroundResource(R.drawable.message_container_dark)
            contraText.setBackgroundResource(R.drawable.message_container_dark)
        }

        registrarseBTN.setOnClickListener {
            val intentChat = Intent(this, RegisterActivity::class.java)
            //intentChat.putExtra("nombreUsuario", nombreUsuario)

            startActivity(intentChat)
        }

        var userID = "";

        iniciarBTN.setOnClickListener {

            errorTxt = listaErrores[2]
            correcto = true
            val text1 = usuarioText.text.toString()
            val text2 = contraText.text.toString()

            val usuarios = usersRef.orderByChild("username").equalTo(text1)
            val valueEventListener = object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        val userAdecuado = ds.child("username").getValue(String::class.java)
                        val passAdecuado = ds.child("password").getValue(String::class.java)
                        val ayDi = ds.child("id").getValue(String::class.java)

                        if (userAdecuado.equals(text1) && passAdecuado.equals(text2)){

                            userID = ayDi.toString()
                            correcto = true
                            errorTxt = ""

                            val intent = Intent(this@MainActivity, HomeActivity::class.java)

                            intent.putExtra("username", userAdecuado.toString())
                            intent.putExtra("userID", userID)

                            startActivity(intent)

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    errorTxt += listaErrores[3]
                    //correcto = false
                }
            }
            usuarios.addListenerForSingleValueEvent(valueEventListener)


            if (text1.isEmpty()){
                errorTxt += listaErrores[0];
                correcto = false
            }

            if(text2.isEmpty()){
                errorTxt += listaErrores[1]
                correcto = false
            }


            /*if(!correcto){
                Toast.makeText(this, errorTxt, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            }*/

        }

    }

}