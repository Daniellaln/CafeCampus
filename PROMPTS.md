# Prompts por funcionalidad para Gemini en Android Studio

Usa **un solo bloque a la vez**. Espera la respuesta, revisa, sincroniza, compila y ejecuta antes del siguiente. Si Gemini falla, vuelve a enviar únicamente el bloque actual. No pegues todo este documento en una solicitud.

Estos son prompts propuestos para reproducir un trabajo similar con Gemini, organizados por funcionalidad, **no por commit**. La implementación entregada se preparó con Codex y sus imágenes con la herramienta integrada de generación de imágenes de OpenAI. No se ejecutaron estos textos en Gemini; completa el registro con lo que Gemini realmente responda si eliges usarlo.

La ruta de carpetas por commit es una forma de aplicar el proyecto ya resuelto. Usar Gemini es otra forma de reproducirlo. Evita pedirle que implemente otra vez funciones que ya copiaste.

Contexto común, ya incorporado en cada bloque: Kotlin, Compose, Material 3, MVVM, Room y CafeRepository. View sin DAO ni Repository; ViewModel conoce solo la interface. No XML ni actualizaciones de dependencias innecesarias. Las imágenes están en la entrega de recursos: cópialas antes de pedir su integración, porque el prompt no transporta archivos.

## 01. Búsqueda de productos

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Agrega búsqueda por nombre en MenuScreen. Implementa el filtro en MenuViewModel combinando productos, categoría y consulta mediante StateFlow. Ignora mayúsculas y tildes; permite limpiar la búsqueda y muestra un estado sin resultados. Conserva el filtro por categoría. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Antes solo se podía filtrar por categoría; ahora también por nombre y sin depender de tildes.

**Reflexión:** La lógica de búsqueda pertenece al ViewModel; la pantalla solo presenta el resultado.

## 02. Carga y ausencia de resultados

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). En MenuViewModel crea un único estado con productos y cargando. Su primera emisión es carga; solo termina cuando llega la consulta real del Repository. MenuScreen muestra progreso durante carga y vacío únicamente después de recibir resultados vacíos. No uses una lista inicial vacía como prueba de que no existen productos. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Se evita mostrar falsamente que no se encontraron productos durante el arranque.

**Reflexión:** Carga, catálogo vacío y búsqueda sin resultados son estados distintos y deben comunicarse por separado.

## 03. Repetir un pedido

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). En el historial agrega Repetir pedido. MenuViewModel debe consultar los productos actuales mediante CafeRepository y reemplazar la bolsa con las cantidades anteriores. Usa precios actuales, omite agotados y eliminados con un aviso, y rechaza una repetición vacía. Navega a confirmar solo al terminar; no crees el pedido automáticamente. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La versión base obligaba a reconstruir la bolsa producto por producto. Ahora se puede recuperar una compra, revisando precios y disponibilidad actuales.

**Reflexión:** Repetir es una ayuda para preparar la compra; la confirmación del usuario sigue siendo necesaria.

## 04. Consulta del gráfico horario

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Implementa pedidos por hora de hoy con una consulta Room observable. Usa el intervalo [inicioDelDía, siguienteDía), hora local y excluye cancelados. Declara primero observarPedidosPorHora en CafeRepository y luego implementa su delegación. No cambies las entidades ni la versión de la base. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El gráfico pasa a depender de registros reales de pedidos, sin barras inventadas.

**Reflexión:** Los límites de fecha y los cancelados también forman parte de la exactitud de un reporte.

## 05. Visualización del gráfico

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Conecta PanelAdminViewModel a observarPedidosPorHora. Completa con cero las horas sin pedidos y presenta barras en GraficoPedidos con hora, cantidad y descripción accesible. Evita datos de ejemplo como sustituto de la consulta. Incluye un mensaje cuando el día esté vacío. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El panel muestra la actividad real y distingue un día vacío de uno con pedidos.

**Reflexión:** Un gráfico agradable debe seguir diciendo la verdad cuando todos los valores son cero.

## 06. Paleta, tipografía y tema

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Renueva Color.kt, Type.kt y Theme.kt para Campus fresco: marfil frío #F8F9F2, azul #2455D8 y acento cítrico #DFED99. Usa Manrope local con su licencia, Material 3 y tamaños en sp. Adapta también el tema oscuro. Mantén textos legibles, controles de 48 dp o más y jerarquía consistente. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La base empleaba componentes más simples; la mejora unifica color, letra y espacios de ambos perfiles.

**Reflexión:** La identidad visual debe conservar contraste y legibilidad, no depender solo de colores llamativos.

## 07. Recursos fotográficos y transparencia

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Integra los PNG optimizados ya entregados en drawable-nodpi mediante Image y painterResource. Conserva transparencias, usa Fit en recortes de productos y Crop solo para fondos. Centraliza el mapeo de icono de producto a foto. Todas las pantallas deben incluir imágenes pertinentes al contexto. No descargues fotos ni inventes archivos ausentes. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Los productos y las pantallas secundarias pasan a tener recursos generados reales, en lugar de depender únicamente de iconos.

**Reflexión:** Las imágenes acompañan la tarea; los textos y botones siguen siendo controles nativos y accesibles.

## 08. Detalles de Daniella

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Integra ramita_floral, lazo_lila, flor_porcelana y petalos_campus con su alfa real. Distribúyelos con intención en bienvenida, acceso, cabecera, productos y perfil; sin cubrir textos ni objetivos táctiles. Conserva cobalto y marfil, añade rosa y lila discretos y volumen de cerámica en superficies sólidas. No inventes un rostro ni anides tarjetas. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La base incorpora una identidad más personal con flores, cerámica y acentos suaves.

**Reflexión:** Personalizar consiste en componer detalles coherentes, sin recargar las tareas.

## 09. Óptica de Liquid Glass

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Crea LenteCampus con RuntimeShader AGSL desde API33. Refracta la imagen del material en el borde; desplaza refracción y reflejo según intensidad y punto de contacto. Mantén texto e iconos fuera del shader. Antes de API33 conserva geometría y contraste sin AGSL. No sustituyas Liquid Glass por desenfoque y transparencia estáticos. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Se añade una lente que cambia con la interacción y no afecta la nitidez de los textos.

**Reflexión:** El efecto óptico y el movimiento deben describir un mismo material; Android necesita una implementación propia.

## 10. Sombras exteriores sin recuadro

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Implementa SombraCampus con una sombra suave desplazada y realce opuesto. En superficies translúcidas borra el interior de la máscara de sombra, para que no aparezca un rectángulo a través del material. Adapta las tintas al tema claro y oscuro, usa caché por tamaño y evita elevación nativa debajo del relleno transparente. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La profundidad rodea la silueta sin introducir otro panel dentro de la tarjeta.

**Reflexión:** La sombra también se ve a través del material: controlar su interior evita un defecto óptico que la transparencia no corrige.

## 11. Presión elástica localizada

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Observa PressInteraction con su posición. Anima presión mediante un resorte interrumpible y úsala para deformar suavemente botones circulares y productos, mover reflejos y alimentar LenteCampus. Recupera la forma al soltar o cancelar; no retrases acciones. Respeta escala de animación cero y conserva semántica y áreas táctiles. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Los controles reaccionan al lugar donde se tocan y recuperan su forma sin bloquear la acción.

**Reflexión:** Una respuesta líquida debe sentirse conectada al dedo y poder interrumpirse.

## 12. Composición con color y capas

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Usa un estilo editorial sólido para las pantallas: azul, marfil, acento cítrico, fotografía de gran escala y texto nativo claro. Mantén filas y formularios abiertos. Reserva Liquid Glass para productos y controles elegidos. El menú principal usa catálogo de dos columnas sin banner. Conserva todos los datos y acciones reales. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La base adquiere una composición fotográfica con color, textura y profundidad.

**Reflexión:** La personalidad depende de cómo se componen las capas, no de añadir cajas por toda la pantalla.

## 13. Catálogo con Liquid Glass

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Usa una sola superficie óptica por producto, todas de igual tamaño y adaptadas a escala de fuente. Aplica refracción AGSL al fondo fotográfico local solo desde API33 y una alternativa translúcida en anteriores. Foto libre, texto nativo nítido y tarjeta tocable; sin tarjeta interna ni botón presentado como tarjeta. Liquid Glass se limita a controles seleccionados, no al fondo de toda la app. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El catálogo gana un material líquido uniforme y fotos libres, sin tarjetas anidadas.

**Reflexión:** El material debe responder al tacto y conservar la lectura; una tarjeta contiene un producto completo.

## 14. Bienvenida

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Rediseña SplashScreen con fotografía campus, ramita floral generada con transparencia, título Tu pausa, bien elegida y botón Comenzar. Usa composición con profundidad, texto nativo y desplazamiento para fuentes grandes. Conserva la navegación existente hacia login. No agregues tiempos de espera artificiales. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La entrada adquiere una composición fotográfica con identidad Campus fresco.

**Reflexión:** La bienvenida debe orientar y dejar entrar de inmediato.

## 15. Inicio de sesión

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Rediseña LoginScreen con cabecera fotográfica, saludo, selector Estudiante/Administrador, correo y contraseña, error e Ingresar. Conserva SesionViewModel y las credenciales de práctica de Daniella. Permite scroll con teclado abierto y fuente ampliada. No pongas validaciones ni consultas de datos en el Composable. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El acceso mantiene la misma función con mejor orden, espacios y estados visibles.

**Reflexión:** Un formulario se mejora cuando se entiende y se usa con menos esfuerzo.

## 16. Menú del estudiante

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Rediseña MenuScreen como una vitrina: saludo compacto con espacio vertical, búsqueda mediante lupa en la cabecera y categorías en un selector desplegable. Muestra productos en una cuadrícula de dos columnas con tarjetas uniformes. Elimina el banner promocional y la búsqueda permanentemente debajo. Conserva bolsa, disponibilidad, filtros y estado de carga real. Cada producto abre su detalle al tocarlo. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El catálogo incorpora fotografías, búsqueda y una bolsa más visible.

**Reflexión:** Las imágenes ayudan a elegir, mientras precio y disponibilidad deben permanecer claros.

## 17. Detalle del producto

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Mejora DetalleProductoScreen con recorte fotográfico grande y cítricos transparentes discretos, nombre, precio, disponibilidad y descripción. Conserva cantidad, nota y validación. Coloca total y Agregar al pedido en una zona inferior accesible. No permitas agregar agotados ni exceder las cantidades admitidas. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El detalle gana profundidad visual y mantiene la acción de compra al alcance.

**Reflexión:** Una imagen protagonista debe convivir con un control de cantidad fácil de entender.

## 18. Confirmación de compra

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Renueva ConfirmarPedidoScreen con imagen de bolsa, resumen con foto de cada producto, quitar producto, recojo, forma de pago y nota. Mantén total y Confirmar pedido visibles en el área inferior. Conserva errores y bloqueo con cafetería cerrada. Solo PedidosClienteViewModel puede confirmar mediante Repository. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La revisión del pedido es más visual y mantiene explícito el paso final.

**Reflexión:** La estética no debe ocultar el total ni provocar compras al tocar una imagen.

## 19. Seguimiento del pedido

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Mejora SeguimientoScreen con imagen de recojo y resumen del número, hora y total. Conserva el Flow de Room y un stepper Recibido, Preparando, Listo y Entregado. Muestra cancelación de forma clara. Los cambios del administrador deben verse sin recargar manualmente. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La base ya recibía estados; la mejora los presenta con mayor jerarquía y una imagen contextual.

**Reflexión:** La información observable es lo que hace útil al seguimiento.

## 20. Pedidos activos e historial

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Renueva MisPedidosScreen con imagen de pausas del campus, selector Activos/Historial y tarjetas con número, estado, resumen, hora y total. Conserva la navegación al detalle y Repetir pedido en el historial. Incluye imagen y texto en estados vacíos. No reemplaces pedidos reales por muestras visuales. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El historial resulta más legible e incorpora repetición desde los datos guardados.

**Reflexión:** El mismo diseño debe funcionar con muchos pedidos y con ninguno.

## 21. Panel administrativo

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Rediseña PanelScreen con imagen del café en preparación, saludo, contadores por estado, ventas del día, gráfico real por hora y pedidos recientes. Conserva los accesos y Preparar. Usa tarjetas con jerarquía, azul intenso y superficies claras; no agregues métricas ficticias. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El panel reúne los mismos controles con nuevas imágenes y lectura más rápida, además del gráfico Room.

**Reflexión:** Los adornos no deben competir con los números necesarios para trabajar.

## 22. Gestión de pedidos

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Renueva PedidosAdminScreen con imagen de cocina, filtros por estado y tarjetas legibles. Conserva abrir detalle, avanzar y cancelar según las transiciones permitidas. Incluye vacío contextual y botones de al menos 48 dp. La pantalla solo llama funciones de PedidosAdminViewModel. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La gestión se vuelve visualmente consistente y sigue trabajando sobre pedidos reales.

**Reflexión:** Los permisos de transición no deben relajarse para simplificar la presentación.

## 23. Detalle administrativo del pedido

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Mejora DetallePedidoAdminScreen con imagen de preparación, número, estado, cliente, productos, cantidades, precios, nota y total. Conserva las acciones avanzar y cancelar y los bloqueos durante guardado. La identidad del estudiante debe ser Daniella, obtenida de la relación Room. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La información del cliente y las acciones quedan mejor agrupadas sin alterar el flujo de estados.

**Reflexión:** Separar información y acciones reduce errores al operar un pedido.

## 24. Gestión del catálogo

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Rediseña MenuAdminScreen con fotos generadas de productos, categorías, precio y disponibilidad. Conserva editar, agregar, agotar y eliminar con confirmación. Al borrar un producto los pedidos anteriores deben mantener sus datos históricos. Usa controles claros y espacio suficiente entre acciones. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El catálogo administrativo ahora comparte las mismas fotos del menú del estudiante.

**Reflexión:** Una foto consistente ayuda a identificar el producto, pero eliminar sigue requiriendo una acción explícita.

## 25. Formulario de producto

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Renueva FormularioProductoScreen con previsualización fotográfica y selector de fotos con nombres legibles. Conserva nombre, categoría, precio, descripción, disponibilidad, Guardar y Cancelar, así como errores y carga del ViewModel. Maneja teclado y scroll. Usa imágenes locales entregadas; no simules subida de archivos. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Crear y editar productos permite ver la foto elegida y mantiene las validaciones existentes.

**Reflexión:** Una previsualización útil muestra lo que realmente se publicará en el menú.

## 26. Perfil de Daniella

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Mejora AjustesScreen en modo estudiante con imagen de estudio, nombre Daniella y correo provenientes de la sesión. Mantén un avatar con inicial hasta recibir su fotografía; no inventes su rostro. Conserva modo oscuro, cambiar de perfil y cerrar sesión. Presenta preferencias con descripciones breves. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** El perfil pasa de una lista simple a un espacio personal con identidad y contexto visual.

**Reflexión:** Personalizar requiere datos reales; una inicial es preferible a atribuirle una cara inventada.

## 27. Ajustes del administrador

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Adapta AjustesScreen para administrador con imagen contextual, cuenta activa, modo oscuro y Recibir pedidos. Conserva DataStore, cambio de perfil y cierre de sesión. El cierre debe impedir nuevas confirmaciones sin borrar el historial. Mantén los mismos patrones visuales del perfil estudiante. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Los ajustes ganan claridad visual y conservan las preferencias persistentes de la base.

**Reflexión:** Abrir y cerrar una cafetería es un estado funcional que debe entenderse al instante.

## 28. Navegación inferior

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Mejora la barra inferior de NavCafe con superficie flotante, esquinas suaves, iconos Material, etiqueta y selección visible. Conserva las rutas y el comportamiento de volver sin duplicar pantallas. Respeta los insets del sistema y evita tapar el contenido o los botones de compra. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La navegación adquiere mayor profundidad y una selección más clara.

**Reflexión:** La barra debe facilitar la orientación y respetar el espacio de la pantalla.

## 29. Feedback y transiciones

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Agrega respuesta de presión breve con spring a CafeButton y transiciones discretas de entrada y regreso en NavHost. Mantén eventos inmediatos, foco y semántica de botones. Usa animaciones nativas que respeten la escala de animación del sistema; evita loops decorativos y dependencias nuevas. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La base cambia de pantalla sin el mismo cuidado de movimiento; ahora las acciones reciben feedback sutil.

**Reflexión:** Una animación útil confirma una acción y no retrasa al usuario.

## 30. Stepper, gráfico y estados vacíos

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Anima suavemente el color del stepper y la altura de las barras cuando llegan datos nuevos. Distingue carga con progreso de vacío con imagen generada y mensaje contextual. Adapta tamaños de tarjetas al contenido sin cortar texto. Conserva los datos reales y el tema oscuro. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Los cambios de estado se perciben mejor y los vacíos dejan de parecer pantallas incompletas.

**Reflexión:** Un estado vacío y una carga explican situaciones distintas y deben verse distintos.

## 31. Escenas vivas y respuesta del catálogo

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Añade flotación de unos pocos píxeles a la escena fotográfica, respuesta spring al presionar productos, transiciones del contador de bolsa y cantidad y movimiento de elementos del catálogo. No bloquees acciones. Desactiva la flotación si la escala de animación del sistema es cero. Evita repetir entradas por cada dato recibido. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La versión mejorada responde a las interacciones y conecta las fotografías con el movimiento.

**Reflexión:** El movimiento debe acompañar la tarea y seguir siendo opcional para quien reduce animaciones.

## 32. Volver contextual con Liquid Glass

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Agrega VolverCampus circular de56dp con material óptico localizado y flecha nítida. Úsalo en el formulario de acceso y pantallas secundarias: detalle, confirmación y edición. Regresa al paso anterior con Navigation Compose. No lo muestres en bienvenida, menú principal ni panel raíz. Comprueba que del correo y contraseña se pueda regresar al acceso anterior. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** Volver pasa de una flecha pequeña a un control circular claro, presente solo en niveles donde tiene sentido.

**Reflexión:** La coherencia de navegación importa más que repetir un botón en cada pantalla.

## 33. Gota de navegación

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Sustituye el indicador fijo por una gota compartida entre pestañas. Interpola su posición con Animatable; usa velocidad para estirar y fusionar suavemente su cola mediante SDF AGSL. Permite arrastrar entre destinos y asentarse al soltar. Conserva pulsación, etiquetas y Role.Tab. Refracta una captura GraphicsLayer de la página real; no pongas una foto fija como falso fondo. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La selección puede seguir el gesto y transformarse al cambiar de sección.

**Reflexión:** El movimiento tiene una tarea real: comunicar y cambiar el destino, sin crear paneles anidados.

## 34. Lupa que se transforma

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Convierte la lupa en un buscador expandiendo la misma superficie circular hasta una cápsula dentro de la cabecera. Usa un resorte reversible, adapta refracción y conserva la bolsa visible. Abre el campo nativo cuando hay espacio; cerrar restaura saludo y limpia consulta. No agregues un banner ni otro buscador debajo. Respeta fuente ampliada y animación reducida. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La búsqueda aparece desde el control tocado y devuelve el espacio al saludo al cerrarse.

**Reflexión:** La transformación explica de dónde viene el campo y evita añadir otra caja permanente.

## 35. Pruebas y documentación

**Prompt para copiar:**

```text
CafeCampus usa Kotlin, Compose, Material 3, MVVM, Room y CafeRepository (interface). Verifica compra, administración, búsqueda sin tildes, repetición con precios actuales y gráfico Room con límites del día y cancelados. Captura las pantallas reales con Daniella. Actualiza README y PROMPTS.md con resultados comprobados, correcciones y reflexión. No declares pruebas aprobadas ni uso de Gemini si no ocurrieron. Respeta MVVM; no DAO ni Repository en la View. Sin XML. Muestra solo los archivos que cambian y verifica compilación.
```

**Diferencia frente a la base:** La entrega agrega evidencia de las mejoras y una comparación trazable con la base.

**Reflexión:** Documentar con honestidad permite saber qué se probó y qué falta comprobar.

## Registro del trabajo realizado

**Herramientas utilizadas:** Codex para implementación y revisión; generación integrada de OpenAI para las imágenes. Gemini: no ejecutado en esta entrega.

**Qué generó la IA:** búsqueda en StateFlow, repetición con productos actuales, consulta Room y gráfico horario, rediseño de todas las pantallas, fotografías locales, temas, navegación y transiciones.

**Qué se corrigió durante la revisión:** normalización de tildes; carga asíncrona de productos antes de repetir y navegación al finalizar; recursos de imagen disponibles antes de compilar; parámetro de espacio de secciones compartidas; prueba de interfaz adaptada a botones inferiores fijos. Estas correcciones fueron realizadas en esta sesión con Codex, no se atribuyen a Daniella ni a Gemini.

**¿Respeta MVVM?:** Sí. Las pantallas reciben estado y llaman al ViewModel. La búsqueda y repetición viven en MenuViewModel; el gráfico atraviesa DAO → Repository interface/implementación → PanelAdminViewModel → View. Room continúa siendo la fuente compartida de ambos perfiles.

**Para completar después de usar Gemini:** texto realmente enviado; respuesta obtenida; archivos modificados; corrección realizada por la estudiante; resultado de compilación y prueba. No sustituyas este registro con respuestas inventadas.

## Reflexión (5 líneas)

La IA facilitó explorar una identidad visual y convertirla en componentes reutilizables.  
Las funciones nuevas necesitaban comprobar precios, disponibilidad y límites de fecha.  
La revisión detectó detalles de integración que una apariencia bonita no resuelve.  
MVVM mantuvo las decisiones en el ViewModel y Room como fuente compartida.  
La entrega incluye evidencia y propone prompts breves que aún deben ejecutarse si se elige Gemini.

## Prompts de las imágenes

El texto exacto, la herramienta y el recurso local de cada imagen están en [docs/arte/PROMPTS_IMAGENES.json](docs/arte/PROMPTS_IMAGENES.json). Son recursos generados para el laboratorio; no son fotos de una cafetería real. El avatar personal conserva una inicial hasta recibir la fotografía de Daniella.
