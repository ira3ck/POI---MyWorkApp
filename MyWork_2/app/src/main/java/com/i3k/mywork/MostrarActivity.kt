package com.i3k.mywork

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MostrarActivity : AppCompatActivity() {

    private lateinit var username2: String
    private lateinit var userID2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrar)

        username2 = intent.getStringExtra("username2") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID2 = intent.getStringExtra("userID2") ?: "AAAAAAAAAAAAAAAAAAAA"

        var textito = findViewById<TextView>(R.id.mostrarTV)

        textito.text = username2 + ", " + userID2;
    }
}