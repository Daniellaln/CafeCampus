---
name: CafeCampus
description: Pedidos de cafetería en Android según los mockups del laboratorio S08.
colors:
  cliente-primary: "#EA580C"
  admin-primary: "#4F46E5"
  cliente-container: "#FFEDD5"
  admin-container: "#E0E7FF"
  cliente-on-container: "#7C2D12"
  admin-on-container: "#312E81"
  cliente-background: "#FFFBF7"
  admin-background: "#F7F7FD"
  surface: "#FFFFFF"
  text: "#202733"
  text-muted: "#626871"
  outline: "#E3E3E8"
  surface-variant: "#F0EEEC"
  recibido-background: "#DBEAFE"
  recibido-text: "#1E40AF"
  preparando-background: "#FEF3C7"
  preparando-text: "#92400E"
  listo-background: "#DCFCE7"
  listo-text: "#166534"
  entregado-background: "#E5E7EB"
  entregado-text: "#374151"
  cancelado-background: "#FEE2E2"
  cancelado-text: "#991B1B"
  dark-cliente-primary: "#FFB783"
  dark-admin-primary: "#C4B5FD"
  dark-on-primary: "#27133A"
  dark-cliente-container: "#653415"
  dark-admin-container: "#37306B"
  dark-background: "#17151B"
  dark-surface: "#211F26"
  dark-surface-variant: "#343137"
typography:
  body:
    fontFamily: "Android system default"
    fontSize: "16sp"
    fontWeight: 400
    lineHeight: "24sp"
    letterSpacing: "0.5sp"
rounded:
  control: "10dp"
  card: "12dp"
  large: "16dp"
  badge: "20dp"
spacing:
  compact: "4dp"
  small: "8dp"
  card-inset: "14dp"
  page: "16dp"
components:
  button-cliente:
    backgroundColor: "{colors.cliente-primary}"
    textColor: "{colors.surface}"
    rounded: "{rounded.control}"
    height: "min 50dp"
    width: "100%"
  button-admin:
    backgroundColor: "{colors.admin-primary}"
    textColor: "{colors.surface}"
    rounded: "{rounded.control}"
    height: "min 50dp"
    width: "100%"
  card:
    backgroundColor: "{colors.surface}"
    textColor: "{colors.text}"
    rounded: "{rounded.card}"
    padding: "14dp"
  field:
    rounded: "{rounded.card}"
    width: "100%"
  status-listo:
    backgroundColor: "{colors.listo-background}"
    textColor: "{colors.listo-text}"
    rounded: "{rounded.badge}"
    padding: "4dp 8dp"
---

# Design System: CafeCampus

## Overview

**Creative North Star: "Los mockups del laboratorio S08"**

La autoridad visual es el Word proporcionado: una aplicación nativa Android Material 3 para pedir en la cafetería y atender esos pedidos. El sistema conserva sus superficies claras, controles redondeados, color por perfil y jerarquía simple. No se plantea una identidad nueva ni una exploración de conceptos.

El código implementado determina los valores documentados. La experiencia utiliza tipografía del sistema, iconos Material y pequeñas ilustraciones geométricas de alimentos dibujadas en Canvas. El estudiante compra y sigue su pedido; el administrador consulta estados y gestiona el menú. Ambos perfiles mantienen la misma estructura visual.

**Key Characteristics:**
- Material 3 nativo con controles y estados accesibles de Compose.
- Naranja para estudiante e índigo para administrador.
- Fondos cálidos o lavanda, tarjetas delimitadas y esquinas suaves.
- Estados de pedido identificados por texto y color.

## Colors

La paleta combina acentos por perfil con superficies discretas y colores semánticos para pedidos.

### Primary
- **Naranja de estudiante:** acciones principales, precios, progreso y selección de navegación del cliente; su contenedor es naranja pálido.
- **Índigo de administrador:** acciones y selección del administrador; su contenedor es lavanda.

### Neutral
- **Crema de cliente / Lavanda de administrador:** fondo de pantalla por rol.
- **Blanco de superficie:** tarjetas y navegación inferior en modo claro.
- **Texto oscuro / Texto secundario:** contenido principal y notas; el contorno sutil delimita tarjetas.
- **Superficies oscuras:** fondo y superficie independientes, con acentos más claros por perfil. Los valores no establecidos explícitamente por CafeTheme proceden de Material 3.

Los estados comparten pares de fondo y texto: azul para Recibido, ámbar para Preparando, verde para Listo, gris para Entregado y rojo para Cancelado. Disponible reutiliza verde; Agotado reutiliza rojo. Estos pares permanecen explícitos también en modo oscuro.

### Named Rules
**The Role Accent Rule.** El acento depende del perfil; los colores semánticos del pedido conservan su significado en ambos perfiles.

## Typography

**Display Font:** predeterminado del sistema Android, mediante Material 3.
**Body Font:** predeterminado del sistema Android.

**Character:** texto funcional con contraste de tamaño y peso; no hay fuente decorativa ni familia descargada. La única escala sobrescrita explícitamente en Type.kt es bodyLarge, registrada en el frontmatter.

### Hierarchy
- **Headline:** headlineSmall en títulos de pantalla y headlineMedium en contadores y ventas, con peso Bold explícito.
- **Title:** titleMedium en barras y secciones; titleSmall en nombres de producto.
- **Body:** bodyLarge usa el estilo explícito documentado; bodyMedium para precios y errores; bodySmall para notas del seguimiento.
- **Label:** labelSmall en insignias y labelMedium en contadores; las métricas restantes pertenecen a Typography de Material 3.

### Named Rules
**The Native Type Rule.** Usar los roles de MaterialTheme.typography y reservar el peso Bold para las jerarquías que ya lo emplean.

## Layout

La pantalla se organiza con Scaffold y respeta su padding para evitar solaparse con navegación inferior. Las listas principales usan margen y separación de página de 16 dp; los grupos compactos usan 8 dp. Las tarjetas comunes tienen padding de 14 dp y separación interna de 8 dp. El catálogo presenta dos columnas; las categorías se desplazan horizontalmente. El panel administrativo coloca tres contadores de igual ancho en una fila.

El acceso comienza con splash/login. Tras autenticarse, el cliente abre el menú y el administrador abre el panel. Las páginas largas usan listas o desplazamiento vertical. No se ha definido una retícula específica para tabletas ni puntos de ruptura; no inventarlos como parte de este sistema.

## Elevation & Depth

Predomina la separación tonal y el borde de 1 dp de las tarjetas comunes, sin sombra personalizada. Botones, chips y navegación conservan el comportamiento Material 3. La tarjeta de ventas usa el acento pleno y los contadores usan fondos semánticos suaves. No existe un vocabulario de sombras CSS ni una curva de movimiento propia: las transiciones y respuestas de los controles son las del framework.

## Shapes

Controles principales y botones de añadir usan esquinas de 10 dp; tarjetas, campos e ilustraciones usan 12 dp. Shapes define además large de 16 dp. Las insignias usan 20 dp. El seguimiento combina círculos de 32 dp con conectores rectos. Las ilustraciones mantienen geometría plana, colores de alimento y fondos suaves; sus colores locales no constituyen acentos nuevos para la interfaz.

## Components

### Buttons
Botones principales de ancho completo, altura mínima de 50 dp, esquinas suaves y texto Bold. El acento sigue al perfil. Enabled controla el estado deshabilitado; el feedback táctil, foco y semántica proceden de Material 3. Las acciones secundarias emplean TextButton e IconButton nativos.

### Chips
Las categorías usan FilterChip con selección única y una fila desplazable. Las insignias de estado muestran siempre la etiqueta textual. DisponibilidadChip es interactivo y utiliza los mismos pares verde/rojo que Disponible. La selección de FilterChip hereda el acento del tema salvo los pares explícitos de disponibilidad.

### Cards / Containers
Tarjeta usa superficie del tema, borde sutil, esquinas de tarjeta, padding de tarjeta y separación pequeña. ProductoCard tiene padding compacto de 8 dp, ilustración de 86 dp de alto, nombre de al menos dos líneas, precio en el acento y botón de añadir. EstadoContador usa fondo semántico, cantidad grande y texto de estado.

### Inputs / Fields
CafeTextField utiliza OutlinedTextField, una línea, ancho completo y esquinas de tarjeta. La variante secreta oculta el contenido; el tipo de teclado depende del dato. Contorno, foco y estados estándar permanecen bajo Material 3. ErrorTexto se muestra aparte con el color de error del tema.

### Navigation
El cliente tiene Menú, Pedidos y Perfil; el administrador Panel, Pedidos, Menú y Ajustes. La barra inferior usa superficie del tema y marca icono y etiqueta seleccionados con el acento, sin indicador de fondo. Seguimiento conserva Pedidos seleccionado. La barra superior centra el título y usa el fondo de pantalla, con volver cuando corresponde.

### Seguimiento e ilustraciones
StepperEstado utiliza cuatro pasos verticales: círculos activos con acento, pasos anteriores con marca de verificación y notas de estado. Cancelado sustituye el progreso por su insignia y explicación. ProductoIlustracion dibuja café, pan, jugo, plato, botella y una variante genérica con Canvas; es una ilustración funcional, no un pictograma textual.

## Do's and Don'ts

### Do:
- **Do** conservar el lenguaje Material 3 y los mockups del laboratorio como autoridad visual.
- **Do** cambiar el acento y fondo por perfil mediante CafeTheme.
- **Do** acompañar el color de estado con su etiqueta textual.
- **Do** reutilizar los componentes compartidos y el espaciado observado.

### Don't:
- **Don't** sustituir la dirección suministrada por una identidad visual nueva.
- **Don't** usar los colores de los alimentos como nuevos acentos de navegación.
- **Don't** convertir valores locales de ilustraciones o métricas no verificadas en reglas globales.

No canonizado como criterio de accesibilidad: el botón naranja con texto blanco reproduce el código y el mockup, pero ese par requiere revisión de contraste para texto pequeño. Su presencia aquí documenta el artefacto y no certifica su contraste. Los tamaños táctiles se apoyan en Material 3; no se afirma una auditoría global de 48 dp ni una adaptación de tableta no verificada.
