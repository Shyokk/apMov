package com.example.corpaliv.repository

import com.example.corpaliv.data.UsuarioDao
import com.example.corpaliv.model.Usuario

class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun registrar(usuario: Usuario) {
        dao.insertar(usuario)
    }

    suspend fun buscarPorCorreo(correo: String): Usuario? {
        return dao.buscarPorCorreo(correo)
    }
}
