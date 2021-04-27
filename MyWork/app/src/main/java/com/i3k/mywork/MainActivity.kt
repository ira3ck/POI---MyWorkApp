package com.i3k.mywork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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