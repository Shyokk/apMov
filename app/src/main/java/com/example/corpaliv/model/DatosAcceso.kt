package com.example.corpaliv.model

// lo que se escribe en el formulario de acceso del equipo
data class DatosAcceso(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val confirmarClave: String = ""
)
