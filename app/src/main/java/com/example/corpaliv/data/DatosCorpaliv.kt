package com.example.corpaliv.data

import com.example.corpaliv.model.CasoExito
import com.example.corpaliv.model.Indicador
import com.example.corpaliv.model.Modalidad
import com.example.corpaliv.model.Producto
import com.example.corpaliv.model.Programa

// todos estos datos son de ejemplo (ficticios), no son datos reales
object DatosCorpaliv {

    val proposito = "Trabajamos para que personas con discapacidad múltiple tengan más " +
            "autonomía y puedan participar en la sociedad y en el trabajo. Lo hacemos " +
            "con la Escuela Especial Jan Van Dijk y la red sociolaboral Alba Lab."

    val indicadores = listOf(
        Indicador(valor = "45", descripcion = "jóvenes en formación"),
        Indicador(valor = "12", descripcion = "empresas aliadas"),
        Indicador(valor = "8", descripcion = "personas con trabajo este año")
    )

    val modalidades = listOf(
        Modalidad(
            nombre = "Donación",
            destino = "Se usa en materiales para los talleres y en apoyo a las familias."
        ),
        Modalidad(
            nombre = "Alianza con empresas",
            destino = "Abre puestos de práctica y de trabajo para los jóvenes de Alba Lab."
        ),
        Modalidad(
            nombre = "Voluntariado",
            destino = "Ayudas en los talleres, en eventos y acompañando a los jóvenes."
        ),
        Modalidad(
            nombre = "Compra de productos",
            destino = "Lo que compras paga insumos y sueldos de los talleres."
        ),
        Modalidad(
            nombre = "Capacitaciones",
            destino = "Tu empresa aprende sobre inclusión laboral con nuestro equipo."
        )
    )

    val programas = listOf(
        Programa(
            nombre = "Escuela Especial Jan Van Dijk",
            descripcion = "Educación para niños, niñas y jóvenes con discapacidad múltiple.",
            dirigidoA = "Niños y jóvenes"
        ),
        Programa(
            nombre = "Formación laboral",
            descripcion = "Talleres para aprender un oficio y hábitos de trabajo.",
            dirigidoA = "Jóvenes desde los 18 años"
        ),
        Programa(
            nombre = "Preparación para el empleo",
            descripcion = "Apoyo para postular, ir a entrevistas y adaptarse a un trabajo.",
            dirigidoA = "Jóvenes que terminaron los talleres"
        ),
        Programa(
            nombre = "Red Sociolaboral Alba Lab",
            descripcion = "Producción y venta de los productos hechos en los talleres.",
            dirigidoA = "Jóvenes y adultos"
        ),
        Programa(
            nombre = "RSE con empresas",
            descripcion = "Proyectos de responsabilidad social junto a empresas.",
            dirigidoA = "Empresas e instituciones"
        )
    )

    val productos = listOf(
        Producto(
            nombre = "Velas artesanales",
            descripcion = "Velas de soya hechas a mano en el taller.",
            precio = 4500,
            categoria = "Productos"
        ),
        Producto(
            nombre = "Bolsas de tela",
            descripcion = "Bolsas reutilizables estampadas por los jóvenes.",
            precio = 3500,
            categoria = "Productos"
        ),
        Producto(
            nombre = "Jabones naturales",
            descripcion = "Jabones hechos con aceites esenciales.",
            precio = 2500,
            categoria = "Productos"
        ),
        Producto(
            nombre = "Coffee break para eventos",
            descripcion = "Servicio de cafetería para reuniones de empresas.",
            precio = 60000,
            categoria = "Servicios"
        ),
        Producto(
            nombre = "Charla de inclusión laboral",
            descripcion = "Charla para equipos de empresas sobre cómo incluir.",
            precio = 120000,
            categoria = "Servicios"
        )
    )

    // los casos no usan nombres reales
    val casos = listOf(
        CasoExito(
            titulo = "Primer trabajo en una cafetería",
            historia = "Joven de 23 años (nombre reservado) que hizo el taller de cocina.",
            resultado = "Hoy trabaja media jornada en una cafetería aliada."
        ),
        CasoExito(
            titulo = "Alianza con una empresa",
            historia = "La empresa ficticia \"Tiendas Sur\" se sumó como aliada.",
            resultado = "Contrató a 3 jóvenes y compra bolsas de tela para sus tiendas."
        ),
        CasoExito(
            titulo = "Venta en feria navideña",
            historia = "Los jóvenes de Alba Lab vendieron sus productos en una feria.",
            resultado = "Se vendieron 300 velas y se juntó dinero para insumos."
        )
    )
}
