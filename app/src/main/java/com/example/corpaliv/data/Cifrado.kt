package com.example.corpaliv.data

import java.security.MessageDigest

// cifra la clave con SHA-256 (viene incluido en Java, no hay que agregar librerias)
// la misma clave siempre da el mismo resultado, pero desde el resultado no se puede saber la clave
object Cifrado {

    fun cifrarClave(clave: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(clave.toByteArray())
        // paso cada byte a 2 letras hexadecimales (ej: 0a, ff)
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
