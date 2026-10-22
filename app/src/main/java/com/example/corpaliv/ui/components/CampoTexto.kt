package com.example.corpaliv.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

// campo de texto del formulario: si hay error se pone rojo, muestra el icono y el mensaje
@Composable
fun CampoTexto(
    valor: String,
    onCambio: (String) -> Unit,
    etiqueta: String,
    error: String?,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    ayuda: String = ""
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
        isError = error != null,
        trailingIcon = {
            if (error != null) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        supportingText = {
            if (error != null) {
                Text(error)
            } else if (ayuda.isNotEmpty()) {
                Text(ayuda)
            }
        }
    )
}
