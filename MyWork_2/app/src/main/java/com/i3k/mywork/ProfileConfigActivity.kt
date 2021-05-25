package com.i3k.mywork

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.i3k.mywork.modelos.User
import kotlinx.android.synthetic.main.activity_profile_config.*


class ProfileConfigActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String

    private val database = FirebaseDatabase.getInstance()
    private lateinit var userRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_config)

        if(resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES){
            profileNameTB.setBackgroundResource(R.drawable.message_container_dark)
        }

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        userRef = database.getReference("users/$userID")

        updateProfileNameBTN.setOnClickListener {
            updateUser()
            Toast.makeText(this, "Nombre Actualizado", Toast.LENGTH_SHORT).show()

        }

    }

    fun toMap(): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["nombre"] = profileNameTB.text.toString()
        return result
    }

    private fun updateUser() {
        val postValues: Map<String, Any> = toMap()
        userRef.updateChildren(postValues)
    }


}