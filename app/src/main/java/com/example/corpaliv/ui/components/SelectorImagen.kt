package com.example.corpaliv.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.corpaliv.data.CamaraUriFactory

// botones para tomar una foto o elegir una imagen de la galeria (como PerfilScreen de MiAppCamara)
@Composable
fun SelectorImagen(
    imagenUri: String?,
    onImagenElegida: (String?) -> Unit
) {
    val context = LocalContext.current

    // uri del archivo donde la camara va a guardar la foto
    var uriPendienteCamara by rememberSaveable { mutableStateOf<String?>(null) }

    // galeria: abre el selector de imagenes del telefono
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uriElegida ->
        if (uriElegida != null) {
            onImagenElegida(uriElegida.toString())
        }
    }

    // camara: toma la foto y la guarda en el archivo que le pasamos
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito) {
            onImagenElegida(uriPendienteCamara)
        }
    }

    // abre la camara (se usa cuando ya tenemos el permiso)
    fun abrirCamara() {
        try {
            val uri: Uri = CamaraUriFactory.crearUri(context)
            uriPendienteCamara = uri.toString()
            camaraLauncher.launch(uri)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir la cámara: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // pide el permiso de camara si todavia no esta dado
    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            abrirCamara()
        } else {
            Toast.makeText(context, "Sin permiso de cámara no se puede tomar la foto.", Toast.LENGTH_LONG).show()
        }
    }

    fun tomarFoto() {
        val tienePermiso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            abrirCamara()
        } else {
            permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Logo o foto (opcional)",
            style = MaterialTheme.typography.titleSmall
        )
        ImagenLogo(imagen = imagenUri)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { tomarFoto() }) {
                Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Cámara")
            }
            OutlinedButton(onClick = { galeriaLauncher.launch("image/*") }) {
                Icon(imageVector = Icons.Filled.PhotoLibrary, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Galería")
            }
        }
        if (imagenUri != null) {
            TextButton(onClick = { onImagenElegida(null) }) {
                Text("Quitar imagen")
            }
        }
    }
}
