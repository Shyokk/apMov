package com.example.corpaliv.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpaliv.ui.components.AppDrawer
import com.example.corpaliv.ui.components.AppTopBar
import com.example.corpaliv.ui.screens.CasosScreen
import com.example.corpaliv.ui.screens.ColaborarScreen
import com.example.corpaliv.ui.screens.InicioScreen
import com.example.corpaliv.ui.screens.LoginScreen
import com.example.corpaliv.ui.screens.ProductosScreen
import com.example.corpaliv.ui.screens.ProgramasScreen
import com.example.corpaliv.ui.screens.RegistrosScreen
import com.example.corpaliv.viewmodel.LoginViewModel

@Composable
fun App(
    loginViewModel: LoginViewModel = viewModel()
) {
    // nombre de quien inicio sesion, null si nadie ha iniciado sesion
    val usuarioActual by loginViewModel.usuarioActual.collectAsState()

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
            // al cambiar de pantalla la nueva aparece con un fundido (fade)
            AnimatedContent(
                targetState = pantallaActual,
                transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
                label = "Cambio de pantalla"
            ) { pantalla ->
                when (pantalla) {
                    "Inicio" -> InicioScreen(modifier, irAColaborar)
                    "Programas" -> ProgramasScreen(modifier, irAColaborar)
                    "Productos" -> ProductosScreen(modifier, irAColaborar)
                    "Casos de éxito" -> CasosScreen(modifier, irAColaborar)
                    "Quiero colaborar" -> ColaborarScreen(modifier)
                    // los registros solo los ve el equipo: si no hay sesion se muestra el login
                    "Registros" -> {
                        if (usuarioActual == null) {
                            LoginScreen(modifier, loginViewModel)
                        } else {
                            RegistrosScreen(
                                modifier = modifier,
                                nombreUsuario = usuarioActual ?: "",
                                onCerrarSesion = { loginViewModel.cerrarSesion() }
                            )
                        }
                    }
                }
            }
        }
    }
}
