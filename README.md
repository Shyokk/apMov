# CorpalivApp

App móvil para **CORPALIV / Alba Lab**, hecha para la asignatura **DSY1105 - Desarrollo de Aplicaciones Móviles** (Duoc UC).

La idea es juntar en un solo lugar todo lo que una empresa o persona necesita para colaborar con CORPALIV: qué hacen, sus programas, los productos de los talleres, casos de éxito y un formulario para dejar sus datos. El equipo de vinculación de CORPALIV puede entrar con su cuenta y ver los registros que llegan.

**Estudiante:** Nicolás Riquelme

> Todos los datos de la app son ficticios o de ejemplo (indicadores, precios, casos y nombres). No se usan datos personales reales.

## Funcionalidades

- **Inicio:** propósito de CORPALIV, indicadores de impacto y las formas de colaborar con a dónde va cada aporte.
- **Programas, Productos y Casos de éxito:** listas con tarjetas. En Productos se puede filtrar por "Productos" o "Servicios".
- **Botón "Quiero colaborar"** en todas las pantallas informativas, que lleva directo al formulario.
- **Formulario "Quiero colaborar"** (formulario 1):
  - nombre, correo, teléfono (texto)
  - monto aproximado (número, solo si es donación o compra)
  - fecha de contacto (calendario)
  - tipo de colaboración (lista)
  - mensaje opcional
  - checkbox de aceptar contacto y switch de novedades
  - logo o foto con **cámara** o **galería**
  - cada campo se valida y muestra su error con un ícono y un mensaje
- **Registros guardados en el teléfono con Room:** cada envío del formulario queda guardado. Se pueden marcar como contactados o eliminar.
- **Acceso del equipo** (formulario 2): crear cuenta e iniciar sesión. La clave se guarda **cifrada con SHA-256**. La pantalla Registros solo se ve con la sesión iniciada.
- **Animaciones:**
  - cambio de pantalla con fundido
  - errores y mensajes que aparecen y desaparecen suave
  - color animado al marcar un registro como contactado
  - ruedita de carga al iniciar sesión
  - cambio suave de imagen
- **Funciona en vertical y horizontal** sin perder la pantalla ni lo escrito.

## Recursos nativos

1. **Cámara:** pide el permiso `CAMERA`, abre la cámara y guarda la foto usando un `FileProvider`.
2. **Galería:** abre el selector de imágenes del teléfono.

La imagen se copia a una carpeta propia de la app y se muestra con esquinas redondeadas (Coil).

## Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Shyokk/apMov.git
   ```
2. Abrir la carpeta en **Android Studio**, o en **IntelliJ IDEA** con el plugin **Android** instalado.
3. Esperar a que termine la sincronización de Gradle (la primera vez descarga las librerías).
4. Tener un emulador creado en *Device Manager* (yo usé un Pixel 9a con API 37) o un celular conectado.
5. Elegir la configuración **app** y presionar ▶ **Run**.

Para ver la pantalla **Registros**, primero hay que crear una cuenta en "¿No tienes cuenta? Créala aquí" y después iniciar sesión.

### Pruebas

- **Pruebas unitarias** (`app/src/test`): validaciones del formulario (`ColaborarViewModelTest`) y cifrado de la clave (`CifradoTest`). Se corren con el ▶ verde al lado de cada clase.
- **Pruebas en el emulador** (`app/src/androidTest`): guardar, leer, actualizar y borrar en Room (`RegistroDaoTest` y `UsuarioDaoTest`). Usan una base de datos en memoria, así que no tocan los datos de la app.

## Estructura del proyecto (MVVM)

```
app/src/main/java/com/example/corpaliv/
├── MainActivity.kt          -> abre la app con el tema
├── model/                   -> clases de datos (Registro, Usuario, Colaboracion, Producto, etc.)
├── data/                    -> Room (AppDatabase, RegistroDao, UsuarioDao), datos de ejemplo, cifrado y cámara
├── repository/              -> RegistroRepository y UsuarioRepository (hablan con los Dao)
├── viewmodel/               -> ColaborarViewModel (valida), RegistrosViewModel (guarda) y LoginViewModel (acceso)
└── ui/
    ├── App.kt               -> menú lateral y cambio de pantallas
    ├── components/          -> piezas que se reutilizan (CampoTexto, MensajeError, TarjetaInfo, SelectorImagen, etc.)
    ├── screens/             -> una pantalla por archivo
    └── theme/               -> colores y tema Material 3
```

- **Model:** cómo son los datos.
- **ViewModel:** la lógica y las validaciones, con `StateFlow`.
- **Screens / Composables:** solo muestran los datos y avisan cuando el usuario hace algo.
- **Repository:** el intermediario entre el ViewModel y Room.

## Tecnologías

- Kotlin + Jetpack Compose + Material 3
- ViewModel + StateFlow
- Room (con KSP)
- Coil (para cargar imágenes)
- Activity Result API (cámara, galería y permisos)
