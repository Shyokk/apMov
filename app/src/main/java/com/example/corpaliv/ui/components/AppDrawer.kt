package com.example.corpaliv.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    pantallaActual: String,
    onPantallaSeleccionada: (String) -> Unit,
    contenido: @Composable (abrirMenu: () -> Unit) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Lista de pantallas que aparecen en el menú
    val opciones = listOf(
        OpcionMenu("Inicio", Icons.Filled.Home),
        OpcionMenu("Programas", Icons.Filled.Info),
        OpcionMenu("Productos", Icons.Filled.ShoppingCart),
        OpcionMenu("Casos de éxito", Icons.Filled.Star),
        OpcionMenu("Quiero colaborar", Icons.Filled.Email),
        OpcionMenu("Registros", Icons.AutoMirrored.Filled.List)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "CORPALIV · Alba Lab",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(24.dp)
                )
                opciones.forEach { opcion ->
                    NavigationDrawerItem(
                        label = { Text(opcion.nombre) },
                        icon = { Icon(opcion.icono, contentDescription = null) },
                        selected = pantallaActual == opcion.nombre,
                        onClick = {
                            onPantallaSeleccionada(opcion.nombre)
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    ) {
        contenido {
            scope.launch {
                drawerState.open()
            }
        }
    }
}
