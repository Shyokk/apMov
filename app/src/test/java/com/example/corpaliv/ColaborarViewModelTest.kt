package com.example.corpaliv

import com.example.corpaliv.viewmodel.ColaborarViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

// pruebas de las validaciones del formulario (se corren con el boton verde al lado de la clase)
class ColaborarViewModelTest {

    // llena el formulario con datos validos de ejemplo
    private fun formularioValido(): ColaborarViewModel {
        val viewModel = ColaborarViewModel()
        viewModel.actualizarNombre("Empresa Ejemplo")
        viewModel.actualizarCorreo("contacto@ejemplo.cl")
        viewModel.actualizarTelefono("912345678")
        viewModel.actualizarTipo("Voluntariado")
        viewModel.actualizarFecha(System.currentTimeMillis())
        viewModel.actualizarAceptaTerminos(true)
        return viewModel
    }

    @Test
    fun formularioVacioMuestraErrores() {
        val viewModel = ColaborarViewModel()
        assertNull(viewModel.enviar())
        val errores = viewModel.errores.value
        assertNotNull(errores.nombre)
        assertNotNull(errores.correo)
        assertNotNull(errores.telefono)
        assertNotNull(errores.tipo)
        assertNotNull(errores.fecha)
        assertNotNull(errores.aceptaTerminos)
    }

    @Test
    fun formularioValidoSeEnviaYSeLimpia() {
        val viewModel = formularioValido()
        val datos = viewModel.enviar()
        assertEquals("Empresa Ejemplo", datos?.nombre)
        assertTrue(viewModel.enviado.value)
        assertEquals("", viewModel.formulario.value.nombre)
    }

    @Test
    fun correoSinArrobaEsInvalido() {
        val viewModel = formularioValido()
        viewModel.actualizarCorreo("contacto.ejemplo.cl")
        assertNull(viewModel.enviar())
        assertNotNull(viewModel.errores.value.correo)
    }

    @Test
    fun telefonoNoAceptaLetrasNiMenosDe9Numeros() {
        val viewModel = formularioValido()
        viewModel.actualizarTelefono("91234abc")
        assertEquals("912345678", viewModel.formulario.value.telefono)
        viewModel.actualizarTelefono("91234")
        assertNull(viewModel.enviar())
        assertNotNull(viewModel.errores.value.telefono)
    }

    @Test
    fun donacionPideMontoMinimo() {
        val viewModel = formularioValido()
        viewModel.actualizarTipo("Donación")
        viewModel.actualizarMonto("500")
        assertNull(viewModel.enviar())
        assertNotNull(viewModel.errores.value.monto)
        viewModel.actualizarMonto("5000")
        assertNotNull(viewModel.enviar())
    }

    @Test
    fun voluntariadoNoPideMonto() {
        val viewModel = formularioValido()
        assertNotNull(viewModel.enviar())
        assertNull(viewModel.errores.value.monto)
    }

    @Test
    fun fechaPasadaEsInvalida() {
        val viewModel = formularioValido()
        val haceTresDias = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L
        viewModel.actualizarFecha(haceTresDias)
        assertNull(viewModel.enviar())
        assertNotNull(viewModel.errores.value.fecha)
    }

    @Test
    fun corregirUnCampoBorraSuError() {
        val viewModel = ColaborarViewModel()
        viewModel.enviar()
        assertNotNull(viewModel.errores.value.nombre)
        viewModel.actualizarNombre("Empresa Ejemplo")
        assertNull(viewModel.errores.value.nombre)
    }

    @Test
    fun laImagenEsOpcionalYSeEnviaConLosDatos() {
        val viewModel = formularioValido()
        viewModel.actualizarImagen("content://ejemplo/logo.jpg")
        val datos = viewModel.enviar()
        assertEquals("content://ejemplo/logo.jpg", datos?.imagenUri)
    }
}
