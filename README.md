# CafeCampus · Cafetería blanca y cristal

App Android local de pedidos para cafetería. Daniella elige productos y sigue sus pedidos; el administrador gestiona pedidos, disponibilidad y precios. Ambos perfiles comparten Room en el mismo dispositivo.

La fase2 conserva búsqueda sin tildes ni mayúsculas, repetir con precios y disponibilidad actuales, y gráfico por hora con datos Room. Esta ampliación añade favoritos persistentes por usuario y reemplaza la identidad anterior por una cafetería luminosa: café, cerámica y flores blancas, fondo neutro y rosa ligero en acabados. El administrador tiene ambiente de gestión propio en azul.

Las tarjetas uniformes muestran foto, nombre y precio; corazón y flecha son acciones reales. Las categorías son desplegables y tienen accesos horizontales. La lupa se transforma en buscador. No hay barra lateral fija ni tarjetas anidadas. El avatar usa D porque no se adjuntó aquí una foto de Daniella.

El cristal captura el fondo real, con reflejos curvos y sombras de interior transparente. Responde a presión; la gota inferior admite arrastre y cambia de destino. La refracción AGSL requiere API33; API24–32 conserva respuesta geométrica. Es una implementación propia para Android, sin afirmar equivalencia con el material nativo de Apple.

Hay35 SVG originales en `design/icons-svg`, exportados también como ImageVector. Se integraron11 imágenes nuevas seleccionadas: seis productos, dos fondos y tres escenas de administración. Los recursos anteriores necesarios siguen incluidos. Las imágenes optimizadas y sus prompts incorporados funcionan sin conexión.

![Carta de Daniella](docs/capturas/02_menu_cliente.png)

![Administrador azul](docs/capturas/07_panel_admin.png)

[Prompts cortos por funcionalidad, diferencias y reflexiones](PROMPTS_REDISENIO.md). Son propuestas para Gemini; el código entregado se preparó con Codex. [Verificación y límites](VERIFICACION_REDISENIO.md).

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


## Evidencia

Las capturas de docs/capturas corresponden al emulador de pruebas y a datos ficticios. No acreditan rendimiento en un teléfono físico. Consulta VERIFICACION_REDISENIO.md para el resultado final.
