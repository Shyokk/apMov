package com.example.corpaliv.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.corpaliv.model.Registro
import kotlinx.coroutines.flow.Flow

// aqui van las consultas a la tabla registros
@Dao
interface RegistroDao {

    // los mas nuevos primero; Flow avisa solo cuando la tabla cambia
    @Query("SELECT * FROM registros ORDER BY creadoEn DESC")
    fun obtenerTodos(): Flow<List<Registro>>

    @Insert
    suspend fun insertar(registro: Registro)

    @Update
    suspend fun actualizar(registro: Registro)

    @Delete
    suspend fun eliminar(registro: Registro)
}
