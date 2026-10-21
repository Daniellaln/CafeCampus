# CafeCampus

Aplicación Android de pedidos para una cafetería de campus. El estudiante elige productos, confirma un pedido y sigue su estado; el administrador atiende pedidos y administra el menú. Ambos perfiles comparten Room en un mismo dispositivo y las pantallas se actualizan mediante Flow.

## Ejecutar

1. Abre esta carpeta en Android Studio y selecciona el JDK incluido con Android Studio para Gradle (JDK 21 recomendado).
2. Instala Android SDK 35 y sincroniza Gradle. La primera sincronización requiere Internet.
3. Ejecuta `app` en un emulador o dispositivo con Android 7 o superior (API 24).
4. Pulsa Comenzar y usa una cuenta ficticia de la siguiente tabla.

| Perfil | Correo | Contraseña |
| --- | --- | --- |
| Estudiante | daniella@campus.test | 1234 |
| Administrador | admin@cafe.test | admin |

El perfil y el modo oscuro se recuerdan con DataStore. En Perfil o Ajustes puedes cambiar entre las dos cuentas de práctica sin volver a escribir las credenciales. Cerrar sesión vuelve al login. Este mecanismo es exclusivo de la práctica local y usa únicamente los usuarios ficticios de la semilla.

## Tecnología

Kotlin 2.0.21, Compose BOM 2024.09.00, Material 3, Room 2.6.1, KSP 2.0.21-1.0.28, DataStore 1.1.1 y Navigation Compose 2.8.2. Se utiliza AGP 8.7.3 con Gradle 8.11.1 y SDK 35 para mantener las versiones del laboratorio compatibles. El paquete y applicationId del proyecto creado en Android Studio se conservan: `com.leon.cafecampus`.

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

## Datos y reglas

- Se siembran seis productos y dos usuarios ficticios una sola vez, dentro de una transacción.
- Un pedido y sus líneas se guardan juntos; si falla una inserción se revierte toda la operación.
- Cada detalle conserva el nombre y el precio al confirmar. Editar o eliminar el producto no altera pedidos antiguos.
- Se verifica de nuevo la disponibilidad al confirmar, incluso si cambió después de agregar el producto a la bolsa.
- El carrito acumula cantidades por producto, admite quitar líneas y sobrevive a rotación mediante ViewModel y SavedStateHandle.
- Los estados avanzan Recibido → Preparando → Listo → Entregado. Los pedidos activos también pueden cancelarse.
- Las ventas del día excluyen pedidos cancelados. Sin pedidos, devuelven 0.00.
- Si la cafetería no recibe pedidos, el estudiante ve Cafetería cerrada y no puede confirmar.
- La base pasa de versión 1 (menú base) a 2 (cuatro tablas) con una migración explícita que conserva los productos.

## Capturas de la aplicación

Capturas reales tomadas en un emulador Android Pixel 8 Pro. Los pedidos y las cuentas son ficticios.

| Estudiante | Administrador |
| --- | --- |
| ![Menú del estudiante](docs/capturas/02_menu_cliente.png) | ![Panel de administración](docs/capturas/07_panel_admin.png) |
| ![Confirmación del pedido](docs/capturas/04_confirmar_pedido.png) | ![Pedidos por estado](docs/capturas/08_pedidos_admin.png) |
| ![Seguimiento del pedido](docs/capturas/05_seguimiento.png) | ![Gestión del menú](docs/capturas/10_menu_admin.png) |

## Pruebas de aceptación

Los resultados y el entorno verificado se registran en [PRUEBAS.md](PRUEBAS.md).

```powershell
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest
.\gradlew.bat :app:connectedDebugAndroidTest
```

Para repetir `FlujoAceptacionTest`, usa una instalación de prueba sin pedidos previos. La prueba genera datos ficticios, modifica productos y preferencias y toma capturas en el almacenamiento de la app. `DatosAceptacionTest` trabaja con una base de datos independiente en memoria.

## Para pensar

1. El Repository es una interface para poder cambiar Room por otra fuente sin cambiar las pantallas ni los ViewModels.
2. crearPedido es suspend porque guarda una vez; observarPedidosCliente es Flow porque informa cada cambio de los pedidos.
3. Con bases distintas, un perfil no vería los cambios del otro y el seguimiento quedaría desactualizado.
4. Para usar un servidor se implementaría otro Repository y se definirían sus peticiones y modelos de datos.

## Alcance de la fase 1

Incluye los pasos 0 a 12 de la guía y los dos perfiles locales. Los mockups también muestran búsqueda, registro, recuperación de clave, notificaciones y gráfico por hora: las funciones sin contrato en la fase 1 se reservan para una ampliación. La guía propone búsqueda, registro y gráfico real como mejoras de fase 2. La app no presenta controles decorativos que simulen esas funciones.

No se usan servicios externos, pagos reales ni datos personales reales.
