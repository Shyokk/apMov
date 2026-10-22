package com.example.corpaliv.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// tabla "registros" de la base de datos: cada fila es una persona que quiere colaborar
@Entity(tableName = "registros")
data class Registro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val tipo: String,
    val monto: Int = 0,
    val fechaContacto: Long,
    val mensaje: String = "",
    val recibirNovedades: Boolean = false,
    val contactado: Boolean = false,
    val creadoEn: Long = System.currentTimeMillis()
)
