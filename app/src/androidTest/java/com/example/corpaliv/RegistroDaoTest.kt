package com.example.corpaliv

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.corpaliv.data.AppDatabase
import com.example.corpaliv.data.RegistroDao
import com.example.corpaliv.model.Registro
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// prueba de Room que corre en el emulador; usa una base en memoria para no tocar los datos de la app
@RunWith(AndroidJUnit4::class)
class RegistroDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: RegistroDao

    @Before
    fun crearBase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.registroDao()
    }

    @After
    fun cerrarBase() {
        db.close()
    }

    private fun registroEjemplo(nombre: String) = Registro(
        nombre = nombre,
        correo = "contacto@ejemplo.cl",
        telefono = "912345678",
        tipo = "Voluntariado",
        fechaContacto = System.currentTimeMillis()
    )

    @Test
    fun insertarYLeer() = runBlocking {
        dao.insertar(registroEjemplo("Empresa Ejemplo"))
        val lista = dao.obtenerTodos().first()
        assertEquals(1, lista.size)
        assertEquals("Empresa Ejemplo", lista[0].nombre)
    }

    @Test
    fun actualizarContactado() = runBlocking {
        dao.insertar(registroEjemplo("Empresa Ejemplo"))
        val guardado = dao.obtenerTodos().first()[0]
        dao.actualizar(guardado.copy(contactado = true))
        assertTrue(dao.obtenerTodos().first()[0].contactado)
    }

    @Test
    fun eliminar() = runBlocking {
        dao.insertar(registroEjemplo("Uno"))
        dao.insertar(registroEjemplo("Dos"))
        val primero = dao.obtenerTodos().first()[0]
        dao.eliminar(primero)
        assertEquals(1, dao.obtenerTodos().first().size)
    }
}
