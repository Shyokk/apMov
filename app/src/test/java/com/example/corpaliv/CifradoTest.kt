package com.example.corpaliv

import com.example.corpaliv.data.Cifrado
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CifradoTest {

    @Test
    fun abcDaElSha256Conocido() {
        // valor oficial de SHA-256 para "abc"
        assertEquals(
            "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
            Cifrado.cifrarClave("abc")
        )
    }

    @Test
    fun mismaClaveDaMismoResultado() {
        assertEquals(Cifrado.cifrarClave("clave123"), Cifrado.cifrarClave("clave123"))
    }

    @Test
    fun laClaveNoSeGuardaEnTexto() {
        assertNotEquals("clave123", Cifrado.cifrarClave("clave123"))
        assertNotEquals(Cifrado.cifrarClave("clave123"), Cifrado.cifrarClave("clave124"))
    }
}
