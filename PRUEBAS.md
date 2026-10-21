# Verificación de la fase 1

Fecha: 21 de octubre de 2026.
Entorno: Windows, JDK de Android Studio, SDK 35, emulador Pixel 8 Pro con Android 17 (API 37).

## Resultados ejecutados

- Compilación individual de las entregas 01 a 12: aprobada.
- Compilación APK de la aplicación final: aprobada.
- Prueba unitaria incluida en la plantilla inicial: aprobada.
- Pruebas instrumentadas: 10 ejecutadas, 0 fallidas (8 de datos, 1 recorrido de interfaz y 1 comprobación del paquete).
- Lint Android: 0 errores. Advierte sobre versiones antiguas exigidas por la guía, migración de verificadores de Navigation, catálogo de versiones sin uso y etiqueta redundante de la plantilla.

## Aceptación del laboratorio

| Prueba | Resultado observado |
| --- | --- |
| Clave incorrecta | Muestra Correo o contraseña incorrectos y permanece en login |
| Dos productos y confirmar | Pedido Recibido, 2 cafés + 1 chicha, total S/ 11.50 |
| Producto agotado | Se deshabilita Agregar al pedido y se rechaza también desde Repository |
| Cambiar a Preparando | Al regresar al estudiante, el seguimiento muestra Preparando |
| Cancelar | El pedido pasa al Historial con estado Cancelado |
| Cambiar precio | Los detalles históricos conservan nombre y precio anteriores, incluso al eliminar el producto |
| Ventas del día | Excluye cancelados; sin pedidos devuelve 0.0 |
| Cerrar por completo y abrir | Perfil CLIENTE restaurado sin login; pedido cancelado S/ 11.50, café actual S/ 7.00 y su precio histórico S/ 4.00 conservados |
| Texto al 130 % | Menú usable, categorías y productos se desplazan; fuente del emulador restaurada al 100 % |
| Rotar o recrear Activity | El carrito conserva cantidades y productos; ajustes conservan tema y apertura |
| Crear producto | El producto nuevo aparece en la categoría Snacks del estudiante |
| Cafetería cerrada | Se muestra el aviso y se bloquea la confirmación |
| Transacción fallida | Un conflicto de líneas revierte también el pedido |
| Relaciones e índices | Room KSP compila las relaciones y claves; borrar un pedido elimina sus detalles |

Las pruebas no simulan llamadas de red ni pagos reales. Cada perfil trabaja con la misma base local. Las capturas provienen del recorrido de interfaz, con cuentas y pedidos ficticios.

Para repetir el recorrido de interfaz, empieza con una instalación de prueba limpia: espera el Splash sin perfil recordado y sin pedidos previos. La prueba crea y modifica sus propios datos.

## Correspondencia visual

La aplicación toma colores, estructura, componentes y representaciones de productos de los mockups. Se usan controles nativos Material 3 y desplazamiento para mantener objetivos táctiles accesibles. Se omitieron acciones sin contrato de fase 1, documentadas en el README; no se presentan funciones de búsqueda, registro o gráfico como implementadas.