# CafeCampus · fase2 completa

App Android de cafetería con estudiante y administrador, MVVM + CafeRepository interface + Room. La fase1 queda intacta; esta rama conserva sus contratos y desarrolla las seis mejoras propuestas en el Word.

| Mejora del documento | Implementada en |
| --- | --- |
| Buscar en el menú | commit01: filtro en ViewModel por nombre/tildes/categoría |
| Repetir pedido | commit02: historial, precios actuales y omisión de agotados |
| Gráfico real por hora | commit03: consulta Room del día local |
| Registro de estudiante | commit10: correo normalizado único, validaciones, migración y sesión por ID |
| Animaciones y estados vacíos | commit07 y12: stepper, transiciones, cristal táctil y escenas integradas |
| Reporte diario | commit11: ranking por unidades y totales por pago desde Room, sin cancelados |

El menú conserva el diseño aprobado. Los interiores usan fondos fotográficos completos por tarea, sin recortes decorativos superpuestos. Estudiante con fondo neutro de cafetería y rosa ligero; gestor azul con utilería propia. Detalle, bolsa, seguimiento, historial, perfil, acceso y gestión comparten jerarquía y cuidado. Avatar de Daniella112dp con foto aportada, sin retocar; otras cuentas conservan inicial.

El material transparente de los botones se conserva: capa óptica real, borde brillante y respuesta por resorte. AGSL desde API33; alternativa geométrica en API24–32, sin afirmar material Apple nativo ni fluidez medida en hardware.

[Galería de todas las capturas](GALERIA_CAPTURAS.html) · [Inventario por pantalla](CAPTURAS_POR_PANTALLA.md). Son9 vistas del estudiante y8 del administrador; se separan estados adicionales y el modo crear/editar del mismo formulario.

![Perfil de Daniella](docs/capturas/18_perfil.png)

## Ejecutar

Abre el proyecto en Android Studio, usa su JDK incluido, instala SDK35 y sincroniza Gradle. Ejecuta app en Android7/API24 o superior. La primera sincronización requiere internet; las imágenes y datos de la app funcionan localmente.

| Perfil | Correo | Clave |
| --- | --- | --- |
| Estudiante de práctica | daniella@campus.test |1234 |
| Administrador de práctica | admin@cafe.test |admin |

Desde login de Estudiante puedes crear otra cuenta con correo único. Se recuerda su ID al reabrir. Cambiar de perfil es el modo de práctica del laboratorio y pasa a las cuentas demo del otro rol; no autentica un servicio remoto. La foto corresponde únicamente a la cuenta semilla de Daniella.

## Prompts

[PROMPTS_EXTRAS_INTERIORES.md](PROMPTS_EXTRAS_INTERIORES.md) contiene10 bloques cortos por funcionalidad con diferencia y reflexión para registro, reporte y composición interior. Los bloques anteriores se conservan en PROMPTS.md y PROMPTS_REDISENIO.md. Envía un bloque cada vez. La implementación entregada fue realizada con Codex; los prompts para Gemini son propuestas de reproducción, sin respuestas inventadas.

## Imágenes e iconos

Los fondos, productos y foto están en app/src/main/res/drawable-nodpi y se cargan con painterResource(R.drawable.nombre). nodpi evita el preescalado por densidad; Compose controla encaje y tamaño. Los35 SVG originales están en design/icons-svg, con los mismos paths en IconosCafe.kt. [Explicación y fuente Android](docs/arte/COMO_SE_CARGAN.md). Las siete escenas nuevas conservan sus prompts exactos; la foto aportada tiene registro de origen propio.

## Datos y validación

Room versión3, migraciones explícitas1→2→3. La migración añade correo único sin borrar usuarios ni pedidos. Los detalles guardan precios históricos; repetir usa los actuales. Registro fuerza rol CLIENTE. Reporte usa límites del día local, suma unidades y total por pago, excluye cancelados y cambia el intervalo al llegar medianoche.

Los resultados exactos y límites de prueba se registran en VERIFICACION_FINAL.md. Las capturas proceden del emulador, no de una maqueta. No se acredita rendimiento en hardware físico.

## Estructura de paquetes

```text
com.leon.cafecampus
├── MainActivity.kt
├── CafeApp.kt
├── data
│   ├── local
│   │   ├── entity       Producto, Usuario, Pedido, DetallePedido
│   │   ├── dao          ProductoDao, UsuarioDao, PedidoDao
│   │   ├── relation     PedidoConDetalle
│   │   ├── CafeDatabase.kt
│   │   └── DatosIniciales.kt
│   ├── repository      CafeRepository, CafeRepositoryRoom
│   └── prefs           PreferenciasStore
├── model               Rol, EstadoPedido, ItemCarrito
├── viewmodel
│   ├── CafeViewModelFactory
│   ├── MenuViewModel
│   ├── SesionViewModel
│   ├── PedidosClienteViewModel
│   ├── PanelAdminViewModel
│   ├── PedidosAdminViewModel
│   └── MenuAdminViewModel
└── ui
    ├── theme
    ├── components
    ├── navigation      Rutas, NavCafe
    ├── cliente         Splash, Login, Menú, Detalle, Confirmar, Seguimiento, Mis pedidos
    └── admin           Panel, Pedidos, Detalle de pedido, Menú, Formulario, Ajustes
```

## Arquitectura MVVM

```text
View (Composable) ──eventos──► ViewModel ──acciones──► CafeRepository (interface)
      ▲                          ▲                           │
      │ collectAsState           │ Flow                      ▼
      └──────────────────────────┘                   CafeRepositoryRoom
                                                           │
                                                           ▼
                                                      DAO → Room

DataStore → SesionViewModel / PedidosClienteViewModel
NavCafe → composición e inyección de ViewModels
```

Las pantallas reciben ViewModels. Los ViewModels dependen de la interface CafeRepository; únicamente su implementación conoce los DAOs. Las lecturas observables usan Flow y las escrituras suspend. NavCafe crea e inyecta las dependencias como punto de composición.

## Para pensar

1. El Repository es una interface para poder cambiar Room por otra fuente sin cambiar las pantallas ni los ViewModels.
2. crearPedido es suspend porque guarda una vez; observarPedidosCliente es Flow porque informa cada cambio de los pedidos.
3. Con bases distintas, un perfil no vería los cambios del otro y el seguimiento quedaría desactualizado.
4. Para usar un servidor se implementaría otro Repository y se definirían sus peticiones y modelos de datos.


## Archivos nuevos de fase2

RegistroScreen, ReporteScreen, ReporteViewModel, ReporteDia/ProductoReporte/PagoReporte, HorariosRecojo, FondoCristal e Interiores amplían la estructura anterior. Las consultas nuevas viven en UsuarioDao/PedidoDao y se exponen por CafeRepository antes de implementarse en Room.
