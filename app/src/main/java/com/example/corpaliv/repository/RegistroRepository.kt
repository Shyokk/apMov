package com.example.corpaliv.repository

import com.example.corpaliv.data.RegistroDao
import com.example.corpaliv.model.Registro
import kotlinx.coroutines.flow.Flow

// el repository es el que habla con el dao, el viewmodel no toca la base de datos directo
class RegistroRepository(private val dao: RegistroDao) {

    val registros: Flow<List<Registro>> = dao.obtenerTodos()

    suspend fun guardar(registro: Registro) {
        dao.insertar(registro)
    }

    suspend fun actualizar(registro: Registro) {
        dao.actualizar(registro)
    }

    suspend fun eliminar(registro: Registro) {
        dao.eliminar(registro)
    }
}
