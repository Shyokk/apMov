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
    val recibirNovedades: Boolean = false,
    // foto o logo elegido con la camara o la galeria (opcional)
    val imagenUri: String? = null
)
