package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.model.Registro
import com.example.corpaliv.ui.components.formatearFecha
import com.example.corpaliv.viewmodel.RegistrosViewModel

// pantalla para el equipo de vinculacion: ve, marca y borra los registros guardados en Room
@Composable
fun RegistrosScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrosViewModel = viewModel()
) {
    val registros by viewModel.registros.collectAsState(initial = emptyList())
    // cuantos faltan por contactar (filter + size, como en colecciones)
    val pendientes = registros.filter { !it.contactado }.size

    if (registros.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Todavía no hay registros",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Cuando alguien envíe el formulario \"Quiero colaborar\" va a aparecer aquí.",
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "${registros.size} registros · $pendientes por contactar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            items(registros, key = { it.id }) { registro ->
                TarjetaRegistro(
                    registro = registro,
                    onCambiarContactado = { viewModel.cambiarContactado(registro) },
                    onEliminar = { viewModel.eliminar(registro) }
                )
            }
        }
    }
}

@Composable
fun TarjetaRegistro(
    registro: Registro,
    onCambiarContactado: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (registro.contactado) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = registro.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text("Quiere colaborar con: ${registro.tipo}")
            if (registro.monto > 0) {
                Text("Monto aproximado: $${registro.monto}")
            }
            Text("Correo: ${registro.correo}")
            Text("Teléfono: ${registro.telefono}")
            Text("Contactar el: ${formatearFecha(registro.fechaContacto)}")
            if (registro.mensaje.isNotEmpty()) {
                Text("Mensaje: ${registro.mensaje}")
            }
            if (registro.recibirNovedades) {
                Text("Quiere recibir novedades")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (registro.contactado) "Estado: contactado" else "Estado: pendiente",
                fontWeight = FontWeight.SemiBold,
                color = if (registro.contactado) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.tertiary
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onCambiarContactado) {
                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (registro.contactado) "Marcar pendiente" else "Marcar contactado")
                }
                TextButton(onClick = onEliminar) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar registro",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
