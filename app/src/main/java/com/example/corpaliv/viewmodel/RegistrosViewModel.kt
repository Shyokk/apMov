package com.example.corpaliv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.AppDatabase
import com.example.corpaliv.model.Colaboracion
import com.example.corpaliv.model.Registro
import com.example.corpaliv.repository.RegistroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// AndroidViewModel porque necesita el contexto de la app para abrir la base de datos (como en MiAppEstado)
class RegistrosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegistroRepository(
        AppDatabase.getInstance(application).registroDao()
    )

    val registros: Flow<List<Registro>> = repository.registros

    // pasa los datos del formulario a un Registro y lo guarda
    fun guardar(datos: Colaboracion) {
        val registro = Registro(
            nombre = datos.nombre.trim(),
            correo = datos.correo.trim(),
            telefono = datos.telefono,
            tipo = datos.tipo,
            monto = datos.monto.toIntOrNull() ?: 0,
            fechaContacto = datos.fecha ?: System.currentTimeMillis(),
            mensaje = datos.mensaje.trim(),
            recibirNovedades = datos.recibirNovedades
        )
        viewModelScope.launch {
            repository.guardar(registro)
        }
    }

    // cambia entre pendiente y contactado
    fun cambiarContactado(registro: Registro) {
        viewModelScope.launch {
            repository.actualizar(registro.copy(contactado = !registro.contactado))
        }
    }

    fun eliminar(registro: Registro) {
        viewModelScope.launch {
            repository.eliminar(registro)
        }
    }
}
