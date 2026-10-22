package com.example.corpaliv.viewmodel

import androidx.lifecycle.ViewModel
import com.example.corpaliv.model.Colaboracion
import com.example.corpaliv.model.ErroresColaboracion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ColaborarViewModel : ViewModel() {

    private val _formulario = MutableStateFlow(Colaboracion())
    val formulario: StateFlow<Colaboracion> = _formulario.asStateFlow()

    private val _errores = MutableStateFlow(ErroresColaboracion())
    val errores: StateFlow<ErroresColaboracion> = _errores.asStateFlow()

    // pasa a true cuando el formulario se envia bien
    private val _enviado = MutableStateFlow(false)
    val enviado: StateFlow<Boolean> = _enviado.asStateFlow()

    val tipos = listOf(
        "Donación",
        "Alianza con empresas",
        "Voluntariado",
        "Compra de productos",
        "Capacitaciones"
    )

    // cada vez que se cambia un campo se borra su error, asi el mensaje rojo desaparece al corregir
    fun actualizarNombre(valor: String) {
        _formulario.value = _formulario.value.copy(nombre = valor)
        _errores.value = _errores.value.copy(nombre = null)
    }

    fun actualizarCorreo(valor: String) {
        _formulario.value = _formulario.value.copy(correo = valor)
        _errores.value = _errores.value.copy(correo = null)
    }

    fun actualizarTelefono(valor: String) {
        // solo dejo escribir numeros
        if (valor.all { it.isDigit() }) {
            _formulario.value = _formulario.value.copy(telefono = valor)
            _errores.value = _errores.value.copy(telefono = null)
        }
    }

    fun actualizarTipo(valor: String) {
        _formulario.value = _formulario.value.copy(tipo = valor)
        _errores.value = _errores.value.copy(tipo = null)
    }

    fun actualizarMonto(valor: String) {
        if (valor.all { it.isDigit() }) {
            _formulario.value = _formulario.value.copy(monto = valor)
            _errores.value = _errores.value.copy(monto = null)
        }
    }

    fun actualizarFecha(valor: Long?) {
        _formulario.value = _formulario.value.copy(fecha = valor)
        _errores.value = _errores.value.copy(fecha = null)
    }

    fun actualizarMensaje(valor: String) {
        _formulario.value = _formulario.value.copy(mensaje = valor)
    }

    fun actualizarAceptaTerminos(valor: Boolean) {
        _formulario.value = _formulario.value.copy(aceptaTerminos = valor)
        _errores.value = _errores.value.copy(aceptaTerminos = null)
    }

    fun actualizarRecibirNovedades(valor: Boolean) {
        _formulario.value = _formulario.value.copy(recibirNovedades = valor)
    }

    // el monto solo se pide si la persona va a donar o comprar
    fun pideMonto(): Boolean {
        val tipo = _formulario.value.tipo
        return tipo == "Donación" || tipo == "Compra de productos"
    }

    fun validarFormulario(): Boolean {
        val datos = _formulario.value
        var nombreError: String? = null
        var correoError: String? = null
        var telefonoError: String? = null
        var tipoError: String? = null
        var montoError: String? = null
        var fechaError: String? = null
        var terminosError: String? = null

        if (datos.nombre.isBlank()) {
            nombreError = "El nombre es obligatorio"
        } else if (datos.nombre.trim().length < 3) {
            nombreError = "El nombre debe tener al menos 3 letras"
        }

        if (datos.correo.isBlank()) {
            correoError = "El correo es obligatorio"
        } else if (!datos.correo.contains("@") || !datos.correo.contains(".")) {
            correoError = "El correo no es válido (ej: nombre@correo.cl)"
        }

        if (datos.telefono.isBlank()) {
            telefonoError = "El teléfono es obligatorio"
        } else if (datos.telefono.length != 9) {
            telefonoError = "El teléfono debe tener 9 números"
        }

        if (datos.tipo.isBlank()) {
            tipoError = "Elige cómo quieres colaborar"
        }

        if (pideMonto()) {
            val monto = datos.monto.toIntOrNull()
            if (monto == null) {
                montoError = "Ingresa un monto"
            } else if (monto < 1000) {
                montoError = "El monto mínimo es $1000"
            }
        }

        // un dia en milisegundos, para no reclamar si eligen la fecha de hoy
        val unDia = 24 * 60 * 60 * 1000L
        if (datos.fecha == null) {
            fechaError = "Elige una fecha para contactarte"
        } else if (datos.fecha < System.currentTimeMillis() - unDia) {
            fechaError = "La fecha no puede ser anterior a hoy"
        }

        if (!datos.aceptaTerminos) {
            terminosError = "Debes aceptar que te contactemos"
        }

        val nuevosErrores = ErroresColaboracion(
            nombre = nombreError,
            correo = correoError,
            telefono = telefonoError,
            tipo = tipoError,
            monto = montoError,
            fecha = fechaError,
            aceptaTerminos = terminosError
        )
        _errores.value = nuevosErrores
        // si no hay ningun error es igual a ErroresColaboracion() vacio
        return nuevosErrores == ErroresColaboracion()
    }

    // si todo esta bien devuelve los datos para guardarlos y limpia el formulario
    // si hay errores devuelve null
    fun enviar(): Colaboracion? {
        if (!validarFormulario()) {
            _enviado.value = false
            return null
        }
        val datos = _formulario.value
        _formulario.value = Colaboracion()
        _enviado.value = true
        return datos
    }

    fun ocultarMensajeEnviado() {
        _enviado.value = false
    }
}
