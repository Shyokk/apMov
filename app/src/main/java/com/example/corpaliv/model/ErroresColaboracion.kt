package com.example.corpaliv.model

// si un campo tiene error se guarda el mensaje, si esta bien queda en null
data class ErroresColaboracion(
    val nombre: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val tipo: String? = null,
    val monto: String? = null,
    val fecha: String? = null,
    val aceptaTerminos: String? = null
)
