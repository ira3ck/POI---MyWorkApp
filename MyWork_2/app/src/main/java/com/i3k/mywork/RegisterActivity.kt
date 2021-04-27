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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.i3k.mywork.modelos.User


class RegisterActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regis)

        val registrarseBTN = findViewById<Button>(R.id.regisBTN2)

        val usuarioText = findViewById<TextView>(R.id.usernameTB2)
        val contraText = findViewById<TextView>(R.id.passwordTB2)
        val confContraText = findViewById<TextView>(R.id.passwordTB3)

        val contexto = this

        var correcto = true
        val listaErrores = arrayOf("Introduzca un nombre de usuario. ", "Introduzca una contraseña. ", "Este nombre de usuario ya está en uso. ", "Estamos experimentando con problemas en la Base de Datos. ", "Las contraseñas no coinciden")
        var errorTxt = ""

        //Esto es para revisar si está en modo nocturno
        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES){
            usuarioText.setBackgroundResource(R.drawable.message_container_dark)
            contraText.setBackgroundResource(R.drawable.message_container_dark)
            confContraText.setBackgroundResource(R.drawable.message_container_dark)
        }

        registrarseBTN.setOnClickListener {
            val text1 = usuarioText.text.toString()
            val text2 = contraText.text.toString()
            val text3 = confContraText.text.toString()
            correcto = true

            val usuarios = usersRef.orderByChild("username").equalTo(text1)
            val valueEventListener = object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children){
                        val repetido = ds.child("username").getValue(String::class.java)
                        if (repetido.equals(text1)){
                            errorTxt += listaErrores[2]
                            correcto = false
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    errorTxt += listaErrores[3]
                    correcto = false
                }
            }
            usuarios.addListenerForSingleValueEvent(valueEventListener)


            if (text1.isEmpty()){
                errorTxt += listaErrores[0];
                correcto = false;
            }

            if(text2.isEmpty()){
                errorTxt += listaErrores[1]
                correcto = false;
            }

            if(!text2.equals(text3)){
                errorTxt += listaErrores[4]
                correcto = false;
            }

            registrarUsuario(User("", text1, text2))

            /*if(!correcto){
                Toast.makeText(this, errorTxt, Toast.LENGTH_SHORT).show()
            }
            else{
                registrarUsuario(User("", text1, text2))
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
            }*/

            errorTxt = ""

        }

    }

    private fun registrarUsuario(usuario : User){
        val regisUser = usersRef.push()
        usuario.id = regisUser.key ?: ""
        regisUser.setValue(usuario)
    }
}