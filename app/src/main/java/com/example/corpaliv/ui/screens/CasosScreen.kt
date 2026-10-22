package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpaliv.data.DatosCorpaliv
import com.example.corpaliv.ui.components.BotonColaborar
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun CasosScreen(
    modifier: Modifier = Modifier,
    onColaborar: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Historias de lo que se logra cuando colaboras.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        items(DatosCorpaliv.casos) { caso ->
            TarjetaInfo(
                titulo = caso.titulo,
                descripcion = caso.historia,
                detalle = "Resultado: ${caso.resultado}"
            )
        }
        item {
            Text(
                text = "* Casos de ejemplo, sin nombres reales",
                style = MaterialTheme.typography.bodySmall
            )
            BotonColaborar(onClick = onColaborar)
        }
    }
}
