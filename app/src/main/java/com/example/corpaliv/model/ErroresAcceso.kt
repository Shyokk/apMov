package com.example.corpaliv.model

data class ErroresAcceso(
    val nombre: String? = null,
    val correo: String? = null,
    val clave: String? = null,
    val confirmarClave: String? = null,
    // error que no es de un campo, ej: "correo o clave incorrectos"
    val general: String? = null
)
