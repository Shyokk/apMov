package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.MensajeError
import com.example.corpaliv.viewmodel.LoginViewModel

// acceso del equipo de vinculacion: sirve para iniciar sesion o crear cuenta
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel
) {
    val datos by viewModel.datos.collectAsState()
    val errores by viewModel.errores.collectAsState()
    val esRegistro by viewModel.esRegistro.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = if (esRegistro) "Crear cuenta del equipo" else "Acceso equipo de vinculación",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Esta sección es solo para el equipo de CORPALIV que revisa los registros.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (mensaje != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = mensaje ?: "", fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // el nombre solo se pide al crear cuenta
        if (esRegistro) {
            CampoTexto(
                valor = datos.nombre,
                onCambio = { viewModel.actualizarNombre(it) },
                etiqueta = "Nombre",
                error = errores.nombre
            )
        }
        CampoTexto(
            valor = datos.correo,
            onCambio = { viewModel.actualizarCorreo(it) },
            etiqueta = "Correo",
            error = errores.correo,
            tipoTeclado = KeyboardType.Email
        )
        CampoTexto(
            valor = datos.clave,
            onCambio = { viewModel.actualizarClave(it) },
            etiqueta = "Clave",
            error = errores.clave,
            tipoTeclado = KeyboardType.Password,
            ayuda = if (esRegistro) "Mínimo 6 caracteres y un número" else "",
            esClave = true
        )
        if (esRegistro) {
            CampoTexto(
                valor = datos.confirmarClave,
                onCambio = { viewModel.actualizarConfirmarClave(it) },
                etiqueta = "Repetir clave",
                error = errores.confirmarClave,
                tipoTeclado = KeyboardType.Password,
                esClave = true
            )
        }

        MensajeError(errores.general)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.continuar() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(if (esRegistro) "Crear cuenta" else "Iniciar sesión")
        }
        TextButton(onClick = { viewModel.cambiarModo() }) {
            Text(if (esRegistro) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Créala aquí")
        }
    }
}
