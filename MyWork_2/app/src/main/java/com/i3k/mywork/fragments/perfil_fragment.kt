package com.i3k.mywork.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.i3k.mywork.ChatActivity
import com.i3k.mywork.ProfileConfigActivity
import com.i3k.mywork.R
import com.i3k.mywork.adaptadores.ContactFoundAdapter
import com.i3k.mywork.modelos.Contact
import kotlinx.android.synthetic.main.activity_profile_config.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class perfil_fragment(username : String, userID : String) : Fragment() {
    private val database = FirebaseDatabase.getInstance()
    private lateinit var usersRef : DatabaseReference
    private val usuario = username
    private val ayDi = userID


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        var fragmentView =  inflater.inflate(R.layout.fragment_profile, container, false)

        fragmentView.usernameProfileTV.text = usuario

        usersRef = database.getReference("users/$ayDi")

        fragmentView.configBTN.setOnClickListener { view->
            val intent = Intent(activity, ProfileConfigActivity::class.java)

            intent.putExtra("username", usuario)
            intent.putExtra("userID", ayDi)

            startActivity(intent)
        }

        fragmentView.cierraSesBTN.setOnClickListener { view->

            deleteFile( activity!!.applicationContext,"alwaysRember")
            Toast.makeText(activity, "Sesión cerrada, puede cerrar la app.", Toast.LENGTH_SHORT).show()
        }

        var estatus = fragmentView.findViewById<RadioButton>(fragmentView.radioGroup!!.checkedRadioButtonId)

        fragmentView.radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
            var radio: RadioButton = fragmentView.findViewById(checkedId)
                updateUser(radio.text.toString())
        })

        return fragmentView

    }

    fun toMap(texto : String): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["status"] = texto
        return result
    }

    private fun updateUser(texto : String) {
        val postValues: Map<String, Any> = toMap(texto)
        usersRef.updateChildren(postValues)
    }

    fun deleteFile(context: Context, filename: String?) {
        val file = context.getFileStreamPath(filename)
        if(file.exists()){
            file.delete()
        }
    }

}