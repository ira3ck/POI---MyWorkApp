package com.i3k.mywork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_salon.*

class SalonActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salon)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        nombreSalonTV.text = username

        val chatBTN = findViewById<ImageButton>(R.id.chatBTN)

        chatBTN.setOnClickListener{
            val intent = Intent(this, HubActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }
    }
}