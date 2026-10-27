# Prompts breves para Gemini · rediseño de cafetería

Estos bloques están organizados por funcionalidad, no por commit. Envía uno, espera la respuesta, compila y verifica antes del siguiente. La implementación entregada fue hecha con Codex; no se ejecutaron estos prompts en Gemini ni se inventan sus respuestas. Son instrucciones propuestas para reproducir un trabajo similar. Si ya copiaste el proyecto, no necesitas ejecutarlos otra vez.

## Ambiente del estudiante

```text
Mantén MVVM, Room y rutas. Cambia el fondo del estudiante por la imagen local fondo_cafe_blanco: cafetería luminosa con cerámica, café y flores blancas. Usa base neutra, tinta oscura y rosa suave solo en acabados. Conserva a Daniella y respeta barras del sistema. No añadas una tarjeta promocional grande.
```

**Diferencia frente a la base:** La base lisa azul/marfil da paso a una cafetería fotografiada; el rosa acompaña sin cubrir toda la interfaz.

**Reflexión:** El contexto del negocio se reconoce visualmente y el contenido mantiene contraste.

## Liquid Glass: material

```text
Crea un componente Compose reutilizable de cristal transparente con borde curvo blanco brillante, reflejos y sombra suave con interior vacío. Captura únicamente el fondo real mediante GraphicsLayer, sin texto. Desde API33 refráctalo con RuntimeShader; en API24–32 usa alternativa geométrica. Pinta iconos y texto después del efecto. No uses imágenes falsas de vidrio ni velos blancos opacos.
```

**Diferencia frente a la base:** Los controles muestran la escena situada debajo, en lugar de un relleno translúcido fijo.

**Reflexión:** La legibilidad exige separar el plano óptico del contenido. La alternativa antigua no ofrece idéntica refracción.

## Liquid Glass: interacción

```text
Sobre el material anterior, conecta presión y posición del toque a un resorte: compresión corta, estiramiento suave y retorno interrumpible. La barra inferior debe tener una gota que se estira al cambiar de pestaña y se arrastra para navegar de verdad. Respeta duración de animaciones desactivada. Mantén las acciones existentes.
```

**Diferencia frente a la base:** Los controles reaccionan al gesto; la navegación tiene movimiento ligado al destino real.

**Reflexión:** Una animación aporta información cuando responde a la acción y no compite con la tarea.

## Carta visual

```text
Reorganiza Menú: marca y acciones circulares, saludo completo, título con filtro, categorías horizontales y cuadrícula de dos columnas. Tarjetas iguales: altura base260dp, foto154dp, nombre, precio, corazón y flecha circular de detalles. Usa las seis fotos *_rosa locales; evita descripciones, categoría repetida y tarjetas dentro de tarjetas. Adapta altura al tamaño de fuente. Conserva búsqueda, filtros y bolsa.
```

**Diferencia frente a la base:** Cada producto se reconoce por su foto; el texto queda reducido a lo necesario para elegir.

**Reflexión:** Debes verlo, no leerlo: los detalles extensos pertenecen a la pantalla de producto.

## Búsqueda y categorías

```text
Conserva la búsqueda sin tildes ni mayúsculas del ViewModel. Transforma la lupa de la cabecera en un campo mediante resorte, con cerrar y limpiar. El filtro abre categorías reales de la base. Añade accesos Todo, Bebidas, Snacks y Favoritos. No inventes una categoría Postres si no existe en los datos.
```

**Diferencia frente a la base:** La búsqueda ocupa espacio solo cuando se solicita y los filtros reflejan productos existentes.

**Reflexión:** La referencia visual debe adaptarse a la verdad del catálogo, evitando opciones vacías.

## Favoritos persistentes

```text
Implementa corazones reales en las tarjetas. Guarda un conjunto de IDs por usuario en DataStore, expón StateFlow desde SesionViewModel y combínalo en MenuViewModel para filtrar Favoritos. Mantén la selección tras recrear Activity. Añade descripciones accesibles guardar/quitar y prueba persistencia, eliminación y separación entre usuarios.
```

**Diferencia frente a la base:** El corazón deja de ser decoración y permite recuperar productos elegidos.

**Reflexión:** La persistencia convierte una preferencia visual en una función útil.

## Iconos originales

```text
Dibuja una familia propia de35 SVG de24x24, trazo1.7, extremos y uniones redondos, currentColor. Incluye navegación, café, bolsa, corazón, filtros, cantidades, volver y gestión. Exporta los mismos paths como ImageVector en IconosCafe y úsalos en ambos perfiles. No uses emojis como iconos. Conserva etiquetas accesibles y áreas táctiles de48dp o más.
```

**Diferencia frente a la base:** Estudiante y administrador comparten una geometría coherente con color propio de cada perfil.

**Reflexión:** Los SVG se pueden editar y escalar sin depender de imágenes de botones.

## Administración azul

```text
Da al administrador una identidad azul sobria, también en modo oscuro. Usa fondo_gestor y escenas gestor_panel, gestor_pedidos y gestor_catalogo en sus funciones correspondientes. Mantén métricas y gráfico derivados de Room, lista operativa de pedidos y edición de catálogo. Usa encabezados abiertos compactos y controles claros; evita carteles grandes y rosa en superficies del gestor.
```

**Diferencia frente a la base:** La administración comparte la calidad del material pero prioriza lectura rápida y gestión en azul.

**Reflexión:** El estilo común no exige que ambos perfiles tengan la misma intensidad ornamental.

## Pantallas interiores y volver

```text
Extiende el fondo y los acabados a bienvenida, login, detalle, bolsa, seguimiento, historial, perfil y formularios. Incluye volver circular de56dp donde existe un destino anterior, también de login a bienvenida; no lo pongas en Menú. Conserva validaciones, estados vacíos, bloqueo por disponibilidad y datos actuales al repetir pedidos. Comprueba compra y gestión completas.
```

**Diferencia frente a la base:** El cuidado visual y la navegación se mantienen durante toda la tarea.

**Reflexión:** Una portada bonita no compensa pantallas interiores inconexas o acciones rotas.

## Horarios de recojo vigentes

```text
Sustituye las horas fijas de la bolsa por tres horarios futuros del reloj local, con al menos15min de margen y redondeo a30min. Conserva Ahora y muestra Hoy/Mañana, incluso al cruzar mes o año. Refresca cada30seg y revalida antes de confirmar; si venció la elección, pide elegir otra sin crear el pedido. Prueba una hora de mediodía y el cruce de fin de año.
```

**Diferencia frente a la base:** desaparecen los horarios fijos que podían quedar en el pasado.

**Reflexión:** una interfaz cuidada también debe mostrar opciones temporalmente válidas; una fotografía no basta para comprobar la lógica.

## Registro del resultado real en Android Studio

Por cada bloque que ejecutes, registra fecha, prompt exacto, archivos que Gemini cambió, resultado real de compilación y tu reflexión. No presentes los resultados de Codex como respuestas de Gemini. Los prompts de las mejoras previas siguen en PROMPTS.md.
