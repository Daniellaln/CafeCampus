# Gemini · todas las mejoras y composición interior

Envía un bloque cada vez y espera su resultado antes de continuar. Son prompts propuestos para reproducir un trabajo similar: esta implementación se hizo con Codex. No se ejecutaron estos bloques en Gemini ni se atribuyen respuestas inexistentes. Si copias el proyecto entregado, no necesitas pedir que lo rehaga.

## Registro · datos

```text
Añade registrarEstudiante y obtenerUsuario a CafeRepository antes de implementarlos en Room. Valida nombre, correo y contraseña de6caracteres; normaliza el correo con trim/minúsculas. Impón índice único y migra la base sin borrar cuentas ni pedidos. El rol de registro debe ser siempre CLIENTE. Prueba duplicados, formato inválido y migración.
```

**Diferencia frente a la base:** La versión mejorada permite crear cuentas de estudiante y rechaza correos repetidos; la base solo ofrecía las cuentas de demostración.

**Reflexión:** La validación debe sostenerse en datos, no depender del aspecto del formulario.

## Registro · pantalla y sesión

```text
Crea RegistroScreen con nombre, correo, contraseña y confirmación, volver y errores claros. Accede desde login solo en Estudiante. Usa SesionViewModel, sin DAO/Repository en la View. Tras crear, inicia sesión y recuerda ID y rol en DataStore: al recrear no debe sustituir la cuenta por la demo. Conserva botones de cristal existentes.
```

**Diferencia frente a la base:** Existe un flujo real de registro y restauración de la cuenta creada.

**Reflexión:** Recordar solo el rol ya no basta cuando existen varias cuentas del mismo perfil.

## Reporte · consultas

```text
Agrega al contrato CafeRepository dos Flow del día local con intervalo inicio incluido y fin excluido: ranking por suma de unidades y totales/cantidad por forma de pago. Implementa consultas Room con detalles históricos; excluye cancelados. Desempata ranking por importe y nombre. Prueba día vacío, cancelados, límites y cambio posterior de precios.
```

**Diferencia frente a la base:** Producto más vendido y pagos proceden de pedidos reales, no números dibujados.

**Reflexión:** Un reporte debe conservar los importes históricos y delimitar el día correctamente.

## Reporte · pantalla

```text
Crea ReporteViewModel que observa el día local y cambia de intervalo al llegar medianoche. Crea ReporteScreen accesible desde Panel, con volver, total, cantidad de pedidos, producto más vendido y Caja/Yape-Plin. Incluye carga, error y vacío. Usa el tema azul, fondo de gestión y fotos de producto solo como contenido funcional.
```

**Diferencia frente a la base:** El administrador dispone de una pantalla nueva de reporte diario.

**Reflexión:** Las cifras deben poder leerse de un vistazo; evita descripciones que repitan las cifras.

## Fondos compuestos

```text
Mantén Menú como está. En las pantallas interiores usa fotografías locales completas ambiente_perfil, ambiente_bolsa, ambiente_pedidos, ambiente_acceso, ambiente_despacho, ambiente_catalogo y ambiente_gestor según tarea. El libro, taza, bandeja y utilería deben estar integrados en la misma fotografía, con perspectiva y sombras compartidas. Retira los PNG decorativos superpuestos de las cabeceras.
```

**Diferencia frente a la base:** Los elementos forman una escena coherente en vez de una colección de recortes flotantes.

**Reflexión:** La profundidad depende de relaciones espaciales y luz común, no de añadir objetos sin composición.

## Perfil con foto

```text
Recompón perfil y ajustes: título alineado a la izquierda, cabecera abierta, identidad y preferencias en superficies mate con sombra suave. Usa la foto aportada de Daniella en avatar circular de112dp solo para su cuenta semilla; recorta al dibujar, sin retocar el archivo. Otras cuentas conservan inicial. Mantén modo oscuro, cambiar perfil y cerrar sesión.
```

**Diferencia frente a la base:** El perfil tiene jerarquía y una identidad reconocible con foto grande.

**Reflexión:** La personalización debe corresponder a la cuenta correcta, también tras registrar otros estudiantes.

## Detalle del producto

```text
Reorganiza detalle con fotografía a todo el ancho del bloque, nombre/precio, disponibilidad y tiempo, descripción, separador, cantidad con controles circulares y nota con icono. Añade un pie fijo con total claro y acción. Conserva exactamente el material transparente de CafeButton/CristalRosa, no copies el botón fucsia opaco de la referencia. Verifica agotado y cantidades1–99.
```

**Diferencia frente a la base:** El detalle acompaña la calidad del catálogo y separa información de la acción de compra.

**Reflexión:** La referencia guía la composición; los controles conservan el sistema visual ya aprobado.

## Confirmación de pedido

```text
Compón la bolsa con fondo de desayuno integrado, un bloque de productos, sección de hora, sección de pago y nota. Usa superficies mate separadas y controles de cristal, sin tarjetas dentro de tarjetas. Mantén horarios futuros con día, validación al confirmar, subtotal/servicio/total y bloqueo si la cafetería está cerrada.
```

**Diferencia frente a la base:** La confirmación tiene grupos claros con fotos útiles y una acción persistente.

**Reflexión:** Agrupar por decisión ayuda más que encerrar cada texto en una caja.

## Seguimiento e historial

```text
Recompón seguimiento e historial sobre ambiente_pedidos. El estado proviene del Flow de pedidos, no de un temporizador. Conserva stepper animado, Activos/Historial y repetir con precios actuales. Usa una sola superficie por pedido y contexto fotográfico integrado para estados vacíos; no inventes pedidos ni decoraciones recortadas.
```

**Diferencia frente a la base:** Los estados del pedido comparten el cuidado de las pantallas principales.

**Reflexión:** Una animación comunica un cambio confirmado, no sustituye la lógica del estado.

## Capturas y documentación

```text
Ejecuta el recorrido real de ambos perfiles y las nuevas funciones. Exporta al menos las7 vistas del estudiante y7 del administrador del Word, además de perfil del estudiante, registro y reporte; identifica login compartido y formulario crear/editar. Separa estados extra de pantallas nuevas. Añade capturas con texto130% y oscuro. Registra resultados reales y prompts por funcionalidad con diferencia/reflexión; no inventes respuestas de Gemini.
```

**Diferencia frente a la base:** La entrega demuestra el alcance completo y distingue las pantallas de sus estados.

**Reflexión:** La evidencia debe corresponder a la versión entregada, no a una maqueta ni a capturas anteriores.

