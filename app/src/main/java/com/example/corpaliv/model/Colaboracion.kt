package com.example.corpaliv.model

// datos que llena la persona en el formulario "Quiero colaborar"
data class Colaboracion(
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val tipo: String = "",
    val monto: String = "",
    val fecha: Long? = null,
    val mensaje: String = "",
    val aceptaTerminos: Boolean = false,
    val recibirNovedades: Boolean = false
)
