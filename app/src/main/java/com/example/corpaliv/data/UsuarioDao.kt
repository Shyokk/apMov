package com.example.corpaliv.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.corpaliv.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    // devuelve null si no hay nadie con ese correo
    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): Usuario?
}
