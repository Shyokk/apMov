package com.example.corpaliv.ui.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// pasa la fecha del calendario (milisegundos en UTC) a texto dd/MM/yyyy
fun formatearFecha(fecha: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    formato.timeZone = TimeZone.getTimeZone("UTC")
    return formato.format(Date(fecha))
}
