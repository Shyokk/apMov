package com.example.corpaliv.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

// crea un archivo vacio donde la camara va a guardar la foto (igual que en MiAppCamara)
object CamaraUriFactory {
    fun crearUri(context: Context): Uri {
        val carpeta = File(context.cacheDir, "camera")
        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw IllegalStateException("No se pudo crear la carpeta de la cámara")
        }
        val archivo = File.createTempFile("logo_", ".jpg", carpeta)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            archivo
        )
    }
}
