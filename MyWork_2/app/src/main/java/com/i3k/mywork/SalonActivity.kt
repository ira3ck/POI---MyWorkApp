package com.i3k.mywork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.i3k.mywork.fragments.*
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.activity_salon.*

class SalonActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var userID: String

    private var database : FirebaseDatabase? = null
    private var usersRef : DatabaseReference? = null
    private lateinit var listaContactos : MutableList<Contact>

    fun cambiarFragmento(fragmentoNuevo: Fragment, tag: String){
        val fragmentoAnterior = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentoAnterior==null){
            supportFragmentManager.beginTransaction().replace(R.id.salon_container, fragmentoNuevo).commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salon)

        username = intent.getStringExtra("username") ?: "AAAAAAAAAAAAAAAAAAAA"
        userID = intent.getStringExtra("userID") ?: "AAAAAAAAAAAAAAAAAAAA"

        nombreSalonTV.text = username

        val chatBTN = findViewById<ImageButton>(R.id.chatBTN)
        val postsBTN = findViewById<ImageButton>(R.id.postsBTN)

        cambiarFragmento(fragment_posts(), "fragment_posts")


        /*usersRef = FirebaseDatabase.getInstance().getReference("users")

        usersRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(Contact::class.java)

                for (sp in dataSnapshot.children){
                    val username = sp.child("username").getValue()
                    val ayDi = sp.child("id").getValue()
                    val text1 = username.toString()
                    val text2 = ayDi.toString()
                    listaContactos.add(Contact(text2, text1))
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        )*/


        /*chatBTN.setOnClickListener{
            val intent = Intent(this, HubActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }*/

        /*bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.postsBTN->cambiarFragmento(fragment_posts(), "fragment_posts")
                R.id.filesBTN->cambiarFragmento(fragment_files(), "fragment_files")
                R.id.chatBTN->cambiarFragmento(fragment_contacts(), "fragment_contacts")
                R.id.tasksBTN->cambiarFragmento(fragment_tasks(), "fragment_contacts")
            }
            true
        }*/

        postsBTN.setOnClickListener {
            cambiarFragmento(fragment_posts(), "fragment_posts")
        }

        filesBTN.setOnClickListener {
            cambiarFragmento(fragment_files(), "fragment_files")
        }

        chatBTN.setOnClickListener {
            cambiarFragmento(contactos_fragment(), "contact_layout")
        }

        tasksBTN.setOnClickListener {
            cambiarFragmento(fragment_tasks(), "fragment_contacts")
        }


    }
}