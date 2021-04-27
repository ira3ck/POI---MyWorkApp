package com.i3k.mywork.modelos

import com.google.firebase.database.Exclude
<<<<<<< HEAD
import java.sql.Timestamp

class Mensaje(
=======

class Mensaje (
>>>>>>> main
        var id: String = "",
        var contenido: String = "",
        var de: String = "",
        val timestamp: Any? = null
) {
    @Exclude
<<<<<<< HEAD
    var mine: Boolean = false
=======
    var esMio: Boolean = false

>>>>>>> main
}