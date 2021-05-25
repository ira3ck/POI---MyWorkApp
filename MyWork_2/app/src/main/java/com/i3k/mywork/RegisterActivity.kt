package com.i3k.mywork

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.VolumeShaper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.i3k.mywork.modelos.User
import com.kbeanie.multipicker.api.CameraImagePicker
import com.kbeanie.multipicker.api.Picker
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback
import com.kbeanie.multipicker.api.entity.ChosenImage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class RegisterActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    private lateinit var camPicker: CameraImagePicker

    private var puedoEscribir = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regis)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val registrarseBTN = findViewById<Button>(R.id.regisBTN2)

        val usuarioText = findViewById<TextView>(R.id.usernameTB2)
        val contraText = findViewById<TextView>(R.id.passwordTB2)
        val confContraText = findViewById<TextView>(R.id.passwordTB3)
        val foto = findViewById<ImageView>(R.id.imageView6)
        val botonFoto = findViewById<Button>(R.id.btnSubir)

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

        //CARGAR IMAGENES DE INTERNET
        findViewById<Button>(R.id.btnSubir).setOnClickListener {
            val imageView = findViewById<ImageView>(R.id.imageView6)

            Picasso.get()
                    .load("https://storage.contextoganadero.com/s3fs-public/styles/noticias_one/public/blog/field_image/2016-09/blog_ganaderia_0.jpg?itok=bPyySxZ1")
                    .into(imageView)
        }

        camPicker = CameraImagePicker(this)
        camPicker.shouldGenerateThumbnails(false)
        camPicker.setImagePickerCallback(object: ImagePickerCallback {
            override fun onImagesChosen(imagenes: MutableList<ChosenImage>?) {
                val pathImagen = imagenes?.get(0)?.originalPath
                val bitmap = BitmapFactory.decodeFile(pathImagen)

                val imageView = findViewById<ImageView>(R.id.imageView6)
                imageView.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

                stream
            }

            override fun onError(p0: String?) {

            }

        })

        findViewById<Button>(R.id.btnSubir).setOnClickListener {
            if(puedoEscribir){
                camPicker.pickImage()
            }
            else{
                Toast.makeText(this, "necesito tu permiso", Toast.LENGTH_SHORT).show()
                solicitarPermiso()
            }

        }

        val estatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(estatus == PackageManager.PERMISSION_GRANTED){
            //el usuario ya nos ha dado permiso antes de escribir en su almacenamiento
            puedoEscribir = true;
        } else{
            //si el usuario no nos ha dado permiso e escribir
            solicitarPermiso()
        }

        //botonFoto.setOnClickListener{
            //cargarImagen()
        //}

    }

    /*private fun cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    }*/

    private fun registrarUsuario(usuario : User){
        val regisUser = usersRef.push()
        usuario.id = regisUser.key ?: ""
        regisUser.setValue(usuario)
    }

    private val requestCodigo = 128
    private fun solicitarPermiso(){
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        ), requestCodigo)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.isNotEmpty()){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    puedoEscribir = true
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Picker.PICK_IMAGE_CAMERA){
            camPicker.submit(data)
        }
    }
}