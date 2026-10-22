package com.example.corpaliv.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.corpaliv.ui.components.AppDrawer
import com.example.corpaliv.ui.components.AppTopBar
import com.example.corpaliv.ui.screens.CasosScreen
import com.example.corpaliv.ui.screens.ColaborarScreen
import com.example.corpaliv.ui.screens.InicioScreen
import com.example.corpaliv.ui.screens.ProductosScreen
import com.example.corpaliv.ui.screens.ProgramasScreen
import com.example.corpaliv.ui.screens.RegistrosScreen

@Composable
fun App() {
    // Guarda la pantalla que se está mostrando (rememberSaveable la conserva al girar el teléfono)
    var pantallaActual by rememberSaveable {
        mutableStateOf("Inicio")
    }
    AppDrawer(
        pantallaActual = pantallaActual,
        onPantallaSeleccionada = {
            pantallaActual = it
        }
    ) { abrirMenu ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppTopBar(
                    titulo = pantallaActual,
                    onMenuClick = {
                        abrirMenu()
                    }
                )
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            // el boton "Quiero colaborar" de las pantallas lleva al formulario
            val irAColaborar = { pantallaActual = "Quiero colaborar" }
            when (pantallaActual) {
                "Inicio" -> InicioScreen(modifier, irAColaborar)
                "Programas" -> ProgramasScreen(modifier, irAColaborar)
                "Productos" -> ProductosScreen(modifier, irAColaborar)
                "Casos de éxito" -> CasosScreen(modifier, irAColaborar)
                "Quiero colaborar" -> ColaborarScreen(modifier)
                "Registros" -> RegistrosScreen(modifier)
            }
        }
    }
}
