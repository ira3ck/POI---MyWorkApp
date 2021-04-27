package com.i3k.mywork.modelos

import com.google.firebase.database.Exclude

class Mensaje(
    var id: String = "",
    var contenido: String = "",
    var de: String = "",
    val timestamp: Any? = null
) {
   @Exclude
   var esMio: Boolean = false

}