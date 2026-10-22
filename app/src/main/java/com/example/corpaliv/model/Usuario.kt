package com.example.corpaliv.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// tabla "usuarios": las personas del equipo de vinculacion que pueden ver los registros
// el correo no se puede repetir (unique) y la clave se guarda cifrada, nunca en texto normal
@Entity(tableName = "usuarios", indices = [Index(value = ["correo"], unique = true)])
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val claveCifrada: String
)
