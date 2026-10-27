# Verificación de la fase 2

Fecha: 21 de octubre de 2026. JDK de Android Studio, SDK35, emulador Pixel8Pro Android17/API37. El destino visual revisado es teléfono; no se afirma prueba en tableta o teléfono físico.

## Resultados ejecutados

- 14 pruebas instrumentadas aprobadas: 12 de datos/ViewModel, un recorrido de interfaz y una comprobación de paquete.
- El recorrido completo valida acceso y volver, compra, recreación de Activity, cambio de perfil, administración, cancelación, repetición, filtros y búsqueda.
- La prueba de Liquid Glass arrastra la barra desde Perfil hasta Menú y comprueba el destino real; abre y cierra el buscador transformable y verifica limpieza. Comprueba la categoría mediante una acción accesible única, sin confundirla con el texto de los productos.
- Lint: cero errores y 60 advertencias. Se conserva el entorno del laboratorio; hay avisos de dependencias, verificadores de versiones anteriores, recursos y convenciones. No se actualizaron dependencias para ocultarlos.
- La verificación final por carpeta consta en VERIFICACION.md, fuera del proyecto. Compilar carpetas no equivale a ejecutar cada una en todos los dispositivos.
- Los 20 recursos de arte pasan el escaneo de procedencia incorporada. Las capturas muestran la app real y datos ficticios.

## Cobertura de datos

Room en memoria verifica transacciones y rollback, relación pedido/detalles, eliminación en cascada, precio/nombre históricos, disponibilidad al confirmar, orden temporal, semilla idempotente y login. MenuViewModel verifica tildes/mayúsculas/categoría, carga sin falso vacío y repetición con precios actuales, omisiones y rechazo si no queda ningún producto. El gráfico comprueba hora local, inicio inclusivo y fin exclusivo del día y exclusión de cancelados.

La prueba de interfaz requiere una instalación de prueba sin pedidos anteriores y modifica únicamente los datos ficticios de esa app. No se borra la base de la usuaria al aplicar estas carpetas. No se ha medido rendimiento de las animaciones en hardware físico; una captura estática no lo acredita. La óptica AGSL se verificó en API37, con alternativa compilada para API24–32.

Después del recorrido aprobado se ajustó únicamente la selección de tintas de sombra del selector y avatar en tema oscuro. Se recompiló y se volvió a capturar el catálogo oscuro para confirmar el ajuste; no se modificó la lógica funcional.
