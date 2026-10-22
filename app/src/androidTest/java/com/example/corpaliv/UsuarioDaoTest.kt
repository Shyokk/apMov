package com.example.corpaliv

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.corpaliv.data.AppDatabase
import com.example.corpaliv.data.Cifrado
import com.example.corpaliv.data.UsuarioDao
import com.example.corpaliv.model.Usuario
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// prueba de la tabla usuarios en el emulador, con base en memoria
@RunWith(AndroidJUnit4::class)
class UsuarioDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: UsuarioDao

    @Before
    fun crearBase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.usuarioDao()
    }

    @After
    fun cerrarBase() {
        db.close()
    }

    @Test
    fun registrarYBuscarPorCorreo() = runBlocking {
        dao.insertar(Usuario(nombre = "Equipo", correo = "equipo@corpaliv.cl", claveCifrada = Cifrado.cifrarClave("clave123")))
        val usuario = dao.buscarPorCorreo("equipo@corpaliv.cl")
        assertEquals("Equipo", usuario?.nombre)
        // se guardo cifrada, no en texto
        assertEquals(Cifrado.cifrarClave("clave123"), usuario?.claveCifrada)
    }

    @Test
    fun correoQueNoExisteDevuelveNull() = runBlocking {
        assertNull(dao.buscarPorCorreo("nadie@corpaliv.cl"))
    }

    @Test
    fun noSePuedeRepetirElCorreo() = runBlocking {
        dao.insertar(Usuario(nombre = "Uno", correo = "equipo@corpaliv.cl", claveCifrada = "x"))
        var fallo = false
        try {
            dao.insertar(Usuario(nombre = "Dos", correo = "equipo@corpaliv.cl", claveCifrada = "y"))
        } catch (e: Exception) {
            fallo = true
        }
        assertTrue(fallo)
    }
}
