package com.i3k.mywork

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.in_ses.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader


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

        if(fileExists(this@MainActivity, "alwaysRember")){

            var username : String
            var userID : String

            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput("alwaysRember")
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            var bil = stringBuilder.toString()
            username = bil.substring(0, bil.length - 20)
            userID = bil.substring(username.length)

            val intent = Intent(this@MainActivity, HomeActivity::class.java)

            intent.putExtra("username", username)
            intent.putExtra("userID", userID)

            startActivity(intent)
        }

        var correcto = true
        val listaErrores = arrayOf(
            "Introduzca un nombre de usuario. ",
            "Introduzca una contraseña. ",
            "Usuario o Contraseña incorrecta. ",
            "Estamos experimentando con problemas en la Base de Datos. "
        )
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

                            updateUser("Conectado", database.getReference("users/$userID"))

                            if (rememberBTN.isChecked){
                                val file:String = "alwaysRember"
                                val uswarovski:String = userAdecuado.toString()
                                val fileOutputStream: FileOutputStream
                                try {
                                    fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                                    fileOutputStream.write(uswarovski.toByteArray())
                                    fileOutputStream.write(userID.toByteArray())
                                }catch (e: Exception){
                                    e.printStackTrace()
                                }
                            }
                            else{
                                deleteFile("alwaysRember")
                            }

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

    fun toMap(texto: String): Map<String, Any> {
        val result: HashMap<String, Any> = HashMap()
        result["status"] = texto
        return result
    }

    private fun updateUser(texto: String, usRef: DatabaseReference) {
        val postValues: Map<String, Any> = toMap(texto)
        usRef.updateChildren(postValues)
    }

    fun fileExists(context: Context, filename: String?): Boolean {
        val file = context.getFileStreamPath(filename)
        return if (file == null || !file.exists()) {
            false
        } else true
    }

    fun deleteFile(context: Context, filename: String?) {
        val file = context.getFileStreamPath(filename)
        if(file.exists()){
            file.delete()
        }
    }

}