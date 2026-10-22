package com.example.corpaliv.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.AppDatabase
import com.example.corpaliv.model.Colaboracion
import com.example.corpaliv.model.Registro
import com.example.corpaliv.repository.RegistroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File

// AndroidViewModel porque necesita el contexto de la app para abrir la base de datos (como en MiAppEstado)
class RegistrosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegistroRepository(
        AppDatabase.getInstance(application).registroDao()
    )

    val registros: Flow<List<Registro>> = repository.registros

    // pasa los datos del formulario a un Registro y lo guarda
    fun guardar(datos: Colaboracion) {
        // Dispatchers.IO porque copiar la imagen es trabajo de archivos
        viewModelScope.launch(Dispatchers.IO) {
            var ruta: String? = null
            if (datos.imagenUri != null) {
                ruta = copiarImagen(datos.imagenUri)
            }
            val registro = Registro(
                nombre = datos.nombre.trim(),
                correo = datos.correo.trim(),
                telefono = datos.telefono,
                tipo = datos.tipo,
                monto = datos.monto.toIntOrNull() ?: 0,
                fechaContacto = datos.fecha ?: System.currentTimeMillis(),
                mensaje = datos.mensaje.trim(),
                recibirNovedades = datos.recibirNovedades,
                imagenRuta = ruta
            )
            repository.guardar(registro)
        }
    }

    // copia la imagen a una carpeta propia de la app, asi no se pierde si borran la original
    // devuelve la ruta del archivo nuevo, o null si no se pudo copiar
    private fun copiarImagen(uriTexto: String): String? {
        return try {
            val contexto = getApplication<Application>()
            val archivo = File(contexto.filesDir, "logo_${System.currentTimeMillis()}.jpg")
            contexto.contentResolver.openInputStream(Uri.parse(uriTexto))?.use { entrada ->
                archivo.outputStream().use { salida ->
                    entrada.copyTo(salida)
                }
            }
            archivo.absolutePath
        } catch (e: Exception) {
            null
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
            // si tenia imagen tambien se borra el archivo
            if (registro.imagenRuta != null) {
                File(registro.imagenRuta).delete()
            }
        }
    }
}
