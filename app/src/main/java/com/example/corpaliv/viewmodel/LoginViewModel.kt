package com.example.corpaliv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.AppDatabase
import com.example.corpaliv.data.Cifrado
import com.example.corpaliv.model.DatosAcceso
import com.example.corpaliv.model.ErroresAcceso
import com.example.corpaliv.model.Usuario
import com.example.corpaliv.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsuarioRepository(
        AppDatabase.getInstance(application).usuarioDao()
    )

    private val _datos = MutableStateFlow(DatosAcceso())
    val datos: StateFlow<DatosAcceso> = _datos.asStateFlow()

    private val _errores = MutableStateFlow(ErroresAcceso())
    val errores: StateFlow<ErroresAcceso> = _errores.asStateFlow()

    // false = iniciar sesion, true = crear cuenta
    private val _esRegistro = MutableStateFlow(false)
    val esRegistro: StateFlow<Boolean> = _esRegistro.asStateFlow()

    // nombre de quien inicio sesion; si es null no hay sesion
    private val _usuarioActual = MutableStateFlow<String?>(null)
    val usuarioActual: StateFlow<String?> = _usuarioActual.asStateFlow()

    // mensaje verde, ej: cuenta creada
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun actualizarNombre(valor: String) {
        _datos.value = _datos.value.copy(nombre = valor)
        _errores.value = _errores.value.copy(nombre = null, general = null)
    }

    fun actualizarCorreo(valor: String) {
        _datos.value = _datos.value.copy(correo = valor)
        _errores.value = _errores.value.copy(correo = null, general = null)
    }

    fun actualizarClave(valor: String) {
        _datos.value = _datos.value.copy(clave = valor)
        _errores.value = _errores.value.copy(clave = null, general = null)
    }

    fun actualizarConfirmarClave(valor: String) {
        _datos.value = _datos.value.copy(confirmarClave = valor)
        _errores.value = _errores.value.copy(confirmarClave = null, general = null)
    }

    // cambia entre "iniciar sesion" y "crear cuenta"
    fun cambiarModo() {
        _esRegistro.value = !_esRegistro.value
        _errores.value = ErroresAcceso()
        _mensaje.value = null
    }

    fun validar(): Boolean {
        val datos = _datos.value
        val registro = _esRegistro.value
        var nombreError: String? = null
        var correoError: String? = null
        var claveError: String? = null
        var confirmarError: String? = null

        // el nombre y confirmar clave solo se piden al crear cuenta
        if (registro) {
            if (datos.nombre.isBlank()) {
                nombreError = "El nombre es obligatorio"
            } else if (datos.nombre.trim().length < 3) {
                nombreError = "El nombre debe tener al menos 3 letras"
            }
        }

        if (datos.correo.isBlank()) {
            correoError = "El correo es obligatorio"
        } else if (!datos.correo.contains("@") || !datos.correo.contains(".")) {
            correoError = "El correo no es válido (ej: nombre@corpaliv.cl)"
        }

        if (datos.clave.isBlank()) {
            claveError = "La clave es obligatoria"
        } else if (registro && datos.clave.length < 6) {
            claveError = "La clave debe tener al menos 6 caracteres"
        } else if (registro && !datos.clave.any { it.isDigit() }) {
            claveError = "La clave debe tener al menos un número"
        }

        if (registro && datos.confirmarClave != datos.clave) {
            confirmarError = "Las claves no coinciden"
        }

        val nuevosErrores = ErroresAcceso(
            nombre = nombreError,
            correo = correoError,
            clave = claveError,
            confirmarClave = confirmarError
        )
        _errores.value = nuevosErrores
        return nuevosErrores == ErroresAcceso()
    }

    // boton principal: crea la cuenta o inicia sesion segun el modo
    fun continuar() {
        if (!validar()) {
            return
        }
        val datos = _datos.value
        val correo = datos.correo.trim().lowercase()
        val claveCifrada = Cifrado.cifrarClave(datos.clave)

        // la base de datos se usa dentro de una corrutina para no trabar la pantalla
        viewModelScope.launch {
            val usuario = repository.buscarPorCorreo(correo)
            if (_esRegistro.value) {
                if (usuario != null) {
                    _errores.value = _errores.value.copy(correo = "Ya existe una cuenta con ese correo")
                } else {
                    repository.registrar(
                        Usuario(
                            nombre = datos.nombre.trim(),
                            correo = correo,
                            claveCifrada = claveCifrada
                        )
                    )
                    _esRegistro.value = false
                    _datos.value = DatosAcceso(correo = correo)
                    _mensaje.value = "Cuenta creada. Ahora inicia sesión."
                }
            } else {
                // se compara la clave cifrada, nunca la clave en texto
                if (usuario == null || usuario.claveCifrada != claveCifrada) {
                    _errores.value = _errores.value.copy(general = "Correo o clave incorrectos")
                } else {
                    _usuarioActual.value = usuario.nombre
                    _datos.value = DatosAcceso()
                    _mensaje.value = null
                }
            }
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}
