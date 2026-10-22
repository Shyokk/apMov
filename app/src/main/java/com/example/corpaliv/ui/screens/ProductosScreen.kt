package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpaliv.data.DatosCorpaliv
import com.example.corpaliv.ui.components.BotonColaborar
import com.example.corpaliv.ui.components.TarjetaInfo

@Composable
fun ProductosScreen(
    modifier: Modifier = Modifier,
    onColaborar: () -> Unit
) {
    // categoria elegida en los botones de filtro
    var categoria by rememberSaveable { mutableStateOf("Todos") }
    val categorias = listOf("Todos", "Productos", "Servicios")

    // si es "Todos" muestro la lista completa, si no filtro por categoria
    val lista = if (categoria == "Todos") {
        DatosCorpaliv.productos
    } else {
        DatosCorpaliv.productos.filter { it.categoria == categoria }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Productos y servicios hechos en los talleres de Alba Lab.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categorias.forEach { opcion ->
                    FilterChip(
                        selected = categoria == opcion,
                        onClick = { categoria = opcion },
                        label = { Text(opcion) }
                    )
                }
            }
        }
        items(lista) { producto ->
            TarjetaInfo(
                titulo = producto.nombre,
                descripcion = producto.descripcion,
                detalle = "Precio: $${producto.precio}"
            )
        }
        item {
            Text(
                text = "* Precios de ejemplo",
                style = MaterialTheme.typography.bodySmall
            )
            BotonColaborar(onClick = onColaborar)
        }
    }
}
