package com.example.corpaliv.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// boton que se repite en varias pantallas y lleva al formulario
@Composable
fun BotonColaborar(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(52.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Quiero colaborar")
    }
}
