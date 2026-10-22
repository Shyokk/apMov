package com.example.corpaliv.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.ui.components.CampoTexto
import com.example.corpaliv.ui.components.MensajeError
import com.example.corpaliv.ui.components.SelectorImagen
import com.example.corpaliv.ui.components.formatearFecha
import com.example.corpaliv.viewmodel.ColaborarViewModel
import com.example.corpaliv.viewmodel.RegistrosViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColaborarScreen(
    modifier: Modifier = Modifier,
    viewModel: ColaborarViewModel = viewModel(),
    registrosViewModel: RegistrosViewModel = viewModel()
) {
    val formulario by viewModel.formulario.collectAsState()
    val errores by viewModel.errores.collectAsState()
    val enviado by viewModel.enviado.collectAsState()

    // despues de 4 segundos se esconde el mensaje de exito (igual que en MiAppEstado)
    LaunchedEffect(enviado) {
        if (enviado) {
            delay(4000)
            viewModel.ocultarMensajeEnviado()
        }
    }

    var mostrarCalendario by remember { mutableStateOf(false) }
    var tipoAbierto by remember { mutableStateOf(false) }

    // el calendario entrega la fecha en UTC, formatearFecha la pasa a texto
    val fechaTexto = if (formulario.fecha != null) {
        formatearFecha(formulario.fecha!!)
    } else {
        ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Déjanos tus datos y te contactamos para concretar tu ayuda.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        // mensaje cuando se envio bien: entra con animacion
        AnimatedVisibility(
            visible = enviado,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
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
                    Text(
                        text = "¡Gracias! Recibimos tu interés y te contactaremos pronto.",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        CampoTexto(
            valor = formulario.nombre,
            onCambio = { viewModel.actualizarNombre(it) },
            etiqueta = "Nombre o empresa",
            error = errores.nombre
        )
        CampoTexto(
            valor = formulario.correo,
            onCambio = { viewModel.actualizarCorreo(it) },
            etiqueta = "Correo",
            error = errores.correo,
            tipoTeclado = KeyboardType.Email
        )
        CampoTexto(
            valor = formulario.telefono,
            onCambio = { viewModel.actualizarTelefono(it) },
            etiqueta = "Teléfono",
            error = errores.telefono,
            tipoTeclado = KeyboardType.Phone,
            ayuda = "9 números, ej: 912345678"
        )

        // lista para elegir como quiere colaborar
        ExposedDropdownMenuBox(
            expanded = tipoAbierto,
            onExpandedChange = { tipoAbierto = !tipoAbierto }
        ) {
            OutlinedTextField(
                value = formulario.tipo,
                onValueChange = {},
                readOnly = true,
                label = { Text("¿Cómo quieres colaborar?") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoAbierto) },
                isError = errores.tipo != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = tipoAbierto,
                onDismissRequest = { tipoAbierto = false }
            ) {
                viewModel.tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            viewModel.actualizarTipo(tipo)
                            tipoAbierto = false
                        }
                    )
                }
            }
        }
        MensajeError(errores.tipo)
        Spacer(modifier = Modifier.height(8.dp))

        // el monto solo aparece si es donacion o compra
        if (viewModel.pideMonto()) {
            CampoTexto(
                valor = formulario.monto,
                onCambio = { viewModel.actualizarMonto(it) },
                etiqueta = "Monto aproximado ($)",
                error = errores.monto,
                tipoTeclado = KeyboardType.Number,
                ayuda = "Mínimo $1000"
            )
        }

        // fecha para que lo contacten
        OutlinedButton(
            onClick = { mostrarCalendario = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Filled.DateRange, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                if (fechaTexto.isEmpty()) "Elegir fecha de contacto" else "Fecha de contacto: $fechaTexto"
            )
        }
        MensajeError(errores.fecha)

        // recursos nativos: camara y galeria
        SelectorImagen(
            imagenUri = formulario.imagenUri,
            onImagenElegida = { viewModel.actualizarImagen(it) }
        )

        CampoTextoMensaje(
            valor = formulario.mensaje,
            onCambio = { viewModel.actualizarMensaje(it) }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = formulario.aceptaTerminos,
                onCheckedChange = { viewModel.actualizarAceptaTerminos(it) }
            )
            Text("Acepto que CORPALIV me contacte con estos datos")
        }
        MensajeError(errores.aceptaTerminos)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Quiero recibir novedades")
            Switch(
                checked = formulario.recibirNovedades,
                onCheckedChange = { viewModel.actualizarRecibirNovedades(it) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                // uno valida (ColaborarViewModel) y el otro guarda en Room (RegistrosViewModel)
                val datos = viewModel.enviar()
                if (datos != null) {
                    registrosViewModel.guardar(datos)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enviar")
        }
    }

    // calendario para elegir la fecha
    if (mostrarCalendario) {
        val estadoCalendario = rememberDatePickerState(initialSelectedDateMillis = formulario.fecha)
        DatePickerDialog(
            onDismissRequest = { mostrarCalendario = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.actualizarFecha(estadoCalendario.selectedDateMillis)
                    mostrarCalendario = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarCalendario = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = estadoCalendario)
        }
    }
}

// campo de varias lineas para el mensaje (es opcional, no se valida)
@Composable
fun CampoTextoMensaje(
    valor: String,
    onCambio: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text("Mensaje (opcional)") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 3
    )
}
