package com.example.corpaliv.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

// muestra la imagen con esquinas redondeadas y borde; si no hay imagen muestra un icono
// "imagen" puede ser el texto de una Uri o un File, Coil sabe cargar los dos
@Composable
fun ImagenLogo(
    imagen: Any?,
    tamano: Dp = 120.dp
) {
    Box(
        modifier = Modifier
            .size(tamano)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Crossfade hace que la imagen nueva aparezca suave sobre la anterior
        Crossfade(targetState = imagen, label = "Cambio de imagen") { imagenActual ->
            if (imagenActual != null) {
                AsyncImage(
                    model = imagenActual,
                    contentDescription = "Imagen adjunta",
                    modifier = Modifier.fillMaxSize(),
                    // Crop recorta la imagen para llenar el cuadro sin deformarla
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = "Sin imagen",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(tamano / 2)
                    )
                }
            }
        }
    }
}
