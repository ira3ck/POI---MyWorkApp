package com.i3k.mywork.modelos

import com.google.firebase.database.Exclude
import java.sql.Timestamp

class Mensaje (
        var id: String = "",
        var contenido: String = "",
        var de: String = "",
        val timestamp: Any? = null
) {
    @Exclude
    var mine: Boolean = false
}