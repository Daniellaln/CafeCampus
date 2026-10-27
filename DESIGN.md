---
name: CafeCampus — Campus fresco líquido
description: Cafetería Android con cobalto, cerámica floral y Liquid Glass dinámico localizado.
colors:
  cliente-primary: "#2455D8"
  admin-primary: "#23428B"
  primary-container: "#E7EDFF"
  on-primary-container: "#183978"
  secondary: "#80627C"
  citron: "#DFED99"
  secondary-container: "#ECDCE7"
  on-secondary-container: "#5C3B53"
  tertiary: "#7F5113"
  tertiary-container: "#FFE6BB"
  on-tertiary-container: "#513006"
  cliente-background: "#F8F9F2"
  admin-background: "#F3F6FB"
  surface: "#FFFFFF"
  ink: "#142D4F"
  muted: "#586A82"
  surface-variant: "#ECF0F6"
  outline: "#7E8C9E"
  outline-variant: "#DFE5EC"
  error: "#AE302B"
  error-container: "#FFE5E1"
  on-error-container: "#7C2622"
  recibido-text: "#244BA0"
  preparando-background: "#FFEBC8"
  preparando-text: "#805112"
  listo-background: "#E5F2D8"
  listo-text: "#395A1A"
  entregado-background: "#E9EDF2"
  entregado-text: "#4C5C71"
  cancelado-text: "#922F29"
  disponible-background: "#E9F3D9"
  disponible-text: "#39541C"
  dark-primary: "#ABC1FF"
  dark-on-primary: "#102B60"
  dark-primary-container: "#263F68"
  dark-on-primary-container: "#E6EDFF"
  dark-on-secondary: "#273312"
  dark-secondary-container: "#364725"
  dark-on-secondary-container: "#F0F7CF"
  dark-tertiary: "#F2C178"
  dark-tertiary-container: "#4A3823"
  dark-on-tertiary-container: "#FFE7C4"
  dark-background: "#0F1B2D"
  dark-surface: "#19283D"
  dark-text: "#F1F5FC"
  dark-surface-variant: "#26364D"
  dark-muted: "#B9C6D9"
  dark-outline: "#72819A"
  dark-outline-variant: "#34455D"
  dark-error: "#FFB4AB"
  dark-error-container: "#5B2826"
  dark-on-error-container: "#FFDAD6"
  glass-product: "#F4F1FADC"
  dark-glass-product: "#1F324EE5"
  glass-navigation: "#F7F4FAB8"
  dark-glass-navigation: "#20314CDE"
  glass-control: "#F8F5FB9F"
  dark-glass-control: "#2C3D58B3"
  glass-search: "#F5F1FABF"
  dark-glass-search: "#283955C9"
  glass-action-bar: "#F5F1F8DF"
  dark-glass-action-bar: "#23334EED"
typography:
  display-large: {fontFamily: Manrope, fontSize: "48sp", lineHeight: "54sp", fontWeight: 700, letterSpacing: "0sp"}
  display-medium: {fontFamily: Manrope, fontSize: "42sp", lineHeight: "48sp", fontWeight: 700, letterSpacing: "0sp"}
  display-small: {fontFamily: Manrope, fontSize: "36sp", lineHeight: "42sp", fontWeight: 700, letterSpacing: "0sp"}
  headline-large: {fontFamily: Manrope, fontSize: "32sp", lineHeight: "39sp", fontWeight: 700, letterSpacing: "0sp"}
  headline-medium: {fontFamily: Manrope, fontSize: "28sp", lineHeight: "35sp", fontWeight: 700, letterSpacing: "0sp"}
  headline-small: {fontFamily: Manrope, fontSize: "24sp", lineHeight: "31sp", fontWeight: 700, letterSpacing: "0sp"}
  title-large: {fontFamily: Manrope, fontSize: "21sp", lineHeight: "28sp", fontWeight: 700, letterSpacing: "0sp"}
  title-medium: {fontFamily: Manrope, fontSize: "16sp", lineHeight: "23sp", fontWeight: 700, letterSpacing: "0sp"}
  title-small: {fontFamily: Manrope, fontSize: "14sp", lineHeight: "20sp", fontWeight: 600, letterSpacing: "0sp"}
  body-large: {fontFamily: Manrope, fontSize: "16sp", lineHeight: "25sp", fontWeight: 400, letterSpacing: "0sp"}
  body-medium: {fontFamily: Manrope, fontSize: "14sp", lineHeight: "22sp", fontWeight: 400, letterSpacing: "0sp"}
  body-small: {fontFamily: Manrope, fontSize: "12sp", lineHeight: "18sp", fontWeight: 400, letterSpacing: "0sp"}
  label-large: {fontFamily: Manrope, fontSize: "14sp", lineHeight: "20sp", fontWeight: 700, letterSpacing: "0sp"}
  label-medium: {fontFamily: Manrope, fontSize: "12sp", lineHeight: "18sp", fontWeight: 600, letterSpacing: "0sp"}
  label-small: {fontFamily: Manrope, fontSize: "11sp", lineHeight: "16sp", fontWeight: 600, letterSpacing: "0sp"}
rounded:
  extra-small: "8dp"
  small: "12dp"
  field: "14dp"
  medium: "16dp"
  hero: "20dp"
  large: "22dp"
  navigation: "30dp"
  glass-product: "26dp"
  sheet: "30dp"
  hero-bottom: "34dp"
  extra-large: "28dp"
spacing:
  micro: "4dp"
  small: "8dp"
  compact: "10dp"
  group: "12dp"
  inset: "16dp"
  page: "20dp"
  access: "24dp"
components:
  button-cliente:
    backgroundColor: "{colors.cliente-primary}"
    textColor: "{colors.surface}"
    rounded: "{rounded.medium}"
    typography: "{typography.label-large}"
    padding: "15dp 18dp"
    height: "min 56dp"
    width: "100%"
  button-admin:
    backgroundColor: "{colors.admin-primary}"
    textColor: "{colors.surface}"
    rounded: "{rounded.medium}"
    typography: "{typography.label-large}"
    padding: "15dp 18dp"
    height: "min 56dp"
    width: "100%"
  open-record:
    textColor: "{colors.ink}"
    padding: "10dp 0"
    width: "100%"
  field:
    backgroundColor: "{colors.surface}"
    rounded: "{rounded.field}"
    width: "100%"
  category-selected:
    backgroundColor: "{colors.cliente-primary}"
    textColor: "{colors.surface}"
    rounded: "{rounded.small}"
  status-listo:
    backgroundColor: "{colors.listo-background}"
    textColor: "{colors.listo-text}"
    rounded: "{rounded.extra-small}"
    typography: "{typography.label-small}"
    padding: "5dp 9dp"
  navigation:
    backgroundColor: "{colors.glass-navigation}"
    rounded: "{rounded.navigation}"
    height: "82dp"
  glass-product:
    backgroundColor: "{colors.glass-product}"
    textColor: "{colors.ink}"
    rounded: "{rounded.glass-product}"
    padding: "14dp"
    height: "304dp × max(fontScale, 1)"
  glass-control:
    backgroundColor: "{colors.glass-control}"
    size: "56dp"
    rounded: "{rounded.extra-large}"
---

# Design System: CafeCampus

## Overview

**Creative North Star: "Campus fresco líquido"**

La dirección aprobada conserva cobalto, marfil y Manrope, y suma cerámica rosa/lila, flores pequeñas y Liquid Glass dinámico localizado. El catálogo usa una sola superficie óptica por producto; los controles contextuales y la navegación responden al tacto. El resto mantiene un lienzo editorial opaco con texto nativo. Esta revisión sustituye la prohibición anterior de tarjetas de producto y cualquier propuesta de barra lateral fija.

La fotografía aporta alimento y material; el código aporta geometría, refracción, fusión, presión y lectura. El material es una interpretación propia para Android: AGSL desde API 33 y una alternativa geométrica/translúcida en API 24–32. No es el material nativo de Apple ni un simple fondo transparente inmóvil. La intención y sus límites se recogen en [Liquid Glass en CafeCampus](docs/diseno/LIQUID_GLASS.md).

**Key Characteristics:**
- Cobalto y marfil con porcelana rosa/lila, pétalos y fotografías locales con alfa.
- Manrope estática 400/600/700 y contenido funcional Compose.
- Una sola tarjeta óptica por producto, sin recuadro fotográfico interior.
- Navegación con fondo real capturado, gota elástica y arrastre entre destinos.

## Colors

Cobalto da dirección; marfil mantiene la lectura y rosa/lila da carácter material al cristal y la cerámica.

### Primary
- **Azul campus:** acciones del cliente, cabeceras y selección.
- **Azul de administración:** variante del tema para administrador; las cabeceras fotográficas y AppTopBar conservan CampusBlue explícito.
- **Porcelana azul:** contenedor primario de detalles y componentes auxiliares.

### Secondary
- **Malva de cerámica:** secondary claro del tema, con contenedor rosa pálido y texto ciruela profundo. Sustituye el verde secundario anterior.
- **Cítrico:** CampusCitron continúa en acciones de acceso y secondary oscuro; no es el contenedor secundario claro actual.
- **Lila óptico:** rellenos específicos de producto, navegación, búsqueda y controles. El frontmatter expresa los ARGB de Kotlin como colores CSS de ocho cifras con alfa al final.

### Tertiary
- **Ámbar tostado:** rol terciario y contenedor cálido del tema.

### Neutral
- **Marfil frío / Niebla azul:** fondo opaco según perfil.
- **Tinta campus / Azul grisáceo:** texto principal y secundario.
- **Porcelana blanca:** controles y superficies sólidas; bordes del cristal con alfa según componente.
- **Noche campus:** fondo azul profundo, superficies azuladas y texto claro; ambos perfiles comparten el esquema oscuro explícito.

Los pedidos conservan pares explícitos azul, ámbar, verde, gris y rojo para Recibido, Preparando, Listo, Entregado y Cancelado. Disponible/Agotado muestran etiqueta o descripción accesible. Esos pares no dependen de la refracción ni cambian automáticamente con el tema.

### Named Rules
**The Blue Action Rule.** El azul comunica acción y selección; cerámica, flores y cítrico aportan carácter sin sustituir la jerarquía funcional.

**The State Label Rule.** Cada color de pedido o disponibilidad aparece acompañado por texto o una descripción accesible de su significado.

## Typography

**Display Font:** Manrope local.
**Body Font:** Manrope local.

**Character:** una sola familia geométrica con titulares firmes y texto tranquilo. CampusFont carga tres archivos estáticos; no depende de una descarga en ejecución. Los quince roles de Type.kt usan tracking cero.

### Hierarchy
- **Display:** 48/54, 42/48 y 36/42 sp, peso 700; acceso y cifras principales.
- **Headline:** 32/39, 28/35 y 24/31 sp, peso 700; escenas y secciones.
- **Title:** 21/28 y 16/23 sp, peso 700; 14/20 sp, peso 600; saludo, producto y filas.
- **Body:** 16/25, 14/22 y 12/18 sp, peso 400; explicación, formularios y notas.
- **Label:** 14/20 sp, peso 700; 12/18 y 11/16 sp, peso 600; acciones, categorías, navegación e insignias.

### Named Rules
**The Clear Text Rule.** Aplicar la lente al fondo o imagen; letras e iconos permanecen fuera del shader. El contenedor puede deformarse con ellos como unidad, pero la refracción no distorsiona sus glifos.

## Layout

El menú tiene cabecera de 144 dp con saludo, flores, búsqueda y bolsa; debajo aparecen título de carta y selector de categoría. No incluye banner ni barra lateral fija. LazyVerticalGrid usa exactamente dos columnas iguales, margen lateral de 20 dp, separación horizontal de 14 dp y vertical de 20 dp. El padding inferior de 128 dp permite superponer navegación sobre el catálogo.

La lupa nace en un círculo de 56 dp en la misma cabecera: su anchura se interpola a cápsula con spring; el saludo se desvanece y BasicTextField aparece después del 65% de apertura. Cerrar limpia la consulta y recupera el saludo. Categorías abre ModalBottomSheet con esquinas superiores de 30 dp y filas con miniatura y marca de selección. Categorias permanece como filtro horizontal en gestión administrativa.

ProductoCard usa altura de 304 dp multiplicada por max(fontScale, 1), padding de 14 dp, foto de 142 dp y nombre de dos líneas con elipsis. No se afirma una auditoría de todo tamaño de fuente o tableta. Pedidos, preferencias y gráfico siguen abiertos: Tarjeta es una columna con padding vertical de 10 dp, gap 12 dp y divisor inferior al 65% de outlineVariant, sin caja exterior.

La navegación mide 82 dp y tiene margen de 16 dp horizontal y 10 dp vertical. NavCafe graba la página en GraphicsLayer, conserva sus coordenadas y permite superposición en menú; las otras rutas respetan padding inferior de Scaffold. EscenaCampus usa mínimo 196 dp, texto al 55% y recorte al 55% desplazado al borde. HeroCampus de acceso usa mínimo 224 dp, texto al 55% y escena al 58%. No hay puntos de ruptura ni columna lateral documentados.

## Elevation & Depth

La profundidad del cristal es óptica y dinámica. ProductoCard aplica lenteCampus a papel_campus con presión localizada. BotonCristal aplica lente a textura y brillo radial dependiente del contacto. NavegacionLiquida dibuja la página real capturada en GraphicsLayer alineada por positionInRoot; la lente recibe la intensidad de contacto. Texto e iconos se componen después, fuera del shader.

En API 33+, LenteCampus usa RuntimeShader y RenderEffect: desvía el muestreo del fondo en bordes y alrededor del contacto, con reflejo dependiente de la misma fuerza. La gota de navegación es otro shader: combina rectángulo redondeado SDF y campo circular mediante smooth union. Su centro sigue Animatable, su cola depende de velocidad y su grosor cambia con fuerza. Modula la misma capa de navegación; no contiene otra tarjeta. No hay sensor de inclinación ni simulación óptica completa.

En API 24–32, lenteCampus devuelve el modificador sin refracción AGSL y la gota usa geometría redondeada con extensión según velocidad. Permanecen relleno translúcido y respuesta geométrica; no se promete equivalencia óptica entre versiones.

### Shadow Vocabulary
- **Exterior Campus:** SombraCampus dibuja dos sombras bitmap y borra la silueta interior con PorterDuff CLEAR. En claro: principal 0x426B7895, radio 11 dp, desplazamiento (6, 8) dp; realce 0xD9FFFFFF, radio 8 dp, desplazamiento (-5, -5) dp. En oscuro cambia a 0x78000000 y 0x163C5070. Margen bitmap 30 dp. No equivale directamente a elevación CSS.
- **Acción sólida:** detalle y confirmación conservan superficies nativas de 6 dp en el código muestreado. Evitar elevación nativa con centro relleno específicamente detrás de superficies translúcidas.

### Named Rules
**The Single Optical Plane Rule.** Una tarjeta glass por producto; foto, nombre, categoría y precio se leen en el mismo plano, sin otra tarjeta o recuadro fotográfico interno.

**The Empty Shadow Rule.** En superficies translúcidas usar sombra exterior con centro transparente; no recuperar el relleno rectangular visible a través del cristal.

El tacto usa spring 0.58/430 (dampingRatio/stiffness): scaleX 1 + 0.025p, scaleY 1 - 0.048p y traslación ligada al contacto. ProductoCard aplica media amplitud y escala 0.975 con spring 1/600. Navegación usa 0.66/370; lupa 0.72/350. Flotación oscila de -3 a 3 dp con tramos de 4400 ms. movimientoLiquido y FlotacionCampus consultan ANIMATOR_DURATION_SCALE al recordarse; cero desactiva presión líquida/flotación y usa snap en lupa y desplazamiento de selección. El código no observa expresamente cambios de esa preferencia sin recrear la composición. Otras animaciones usan mecanismos Compose; una captura no certifica cada transición.

## Shapes

El tema conserva 8/12/16/22/28 dp. Catálogo usa 26 dp; navegación y hoja 30 dp; controles circulares 56 dp de diámetro. La cápsula usa CircleShape y altura 56 dp. EscenaCampus recorta esquina superior inicial de 28 dp e inferior final de 42 dp; HeroCampus usa esquinas inferiores de 34 dp. Las filas abiertas no adquieren radio o superficie por el nombre antiguo Tarjeta.

## Components

### Buttons
CafeButton conserva ancho completo, mínimo 56 dp, radio 16 dp, padding 18 × 15 dp y presión 0.975, ahora con SombraCampus. BotonCristal usa diámetro 56 dp, interacción localizada, brillo radial y lente sobre textura. VolverCampus lo reutiliza solo en login o niveles secundarios; bienvenida, menú y panel no muestran volver. Estados y semántica siguen siendo nativos.

### Chips
EstadoChip usa pares semánticos y radio 8 dp; Disponible/DisponibilidadChip mantienen verde/rojo y significado accesible. Menú selecciona categorías mediante hoja, no fila permanente de chips; Categorias sigue en administración. El selector que abre la hoja usa FilledTonalButton de mínimo 48 dp y radio 24 dp.

### Cards / Containers
ProductoCard es la excepción deliberada al lienzo abierto: una sola Surface óptica con altura uniforme, color glass-product, borde blanco de 1 dp al 85% en claro o 22% en oscuro y shadowElevation cero. Papel refractado al 18% de alfa claro u 8% oscuro; foto libre, sin ProductoIcono interno. Pétalos acompañan el borde superior. Pedidos, ajustes, gráfico y contadores mantienen estructura abierta; ventas conserva superficie principal azul. FondoCampus permanece opaco.

### Inputs / Fields
CafeTextField conserva surface, borde outlineVariant sin foco, radio 14 dp y teclado adecuado. El buscador de menú usa BasicTextField nativo dentro de la cápsula transformable, con cerrar/limpiar, cursor primary y descripción accesible. El relleno glass-search y textura al 22% reciben lente dependiente de apertura, no una imagen del texto.

### Navigation
Cliente: Menú, Pedidos, Perfil; administrador: Panel, Pedidos, Menú, Ajustes. NavegacionLiquida usa iconos de 24 dp, labelSmall, Role.Tab y selección primary. Admite click y arrastre horizontal: gesto actualiza posición; soltar decide destino con velocidad y redondeo limitado a índices válidos. Seguimiento mantiene Pedidos seleccionado. Página capturada, velo y borde luminoso forman el plano óptico; iconos y letras permanecen encima. AppTopBar ahora usa cobalto y título blanco.

### Escenas, fotografía y material
Los assets locales muestran comida, papel, cerámica, flores y lazos con alfa. HeroCampus/EscenaCampus combinan estudio_cobalto y recortes superpuestos sin texto funcional rasterizado. ProductoIlustracion conserva Canvas geométrico antiguo sin uso desde ProductoIcono: sus colores no redefinen la identidad. Los recuentos anteriores de assets no son invariantes del sistema.

### Seguimiento y gráfico
StepperEstado y GraficoPedidos conservan datos y etiquetas nativas, progreso y alturas animadas. Gráfico sin marco en el lienzo; no se rasteriza ni se rellena con cifras inventadas. Vacío combina texto, foto y pétalos; carga usa progreso observable.

## Do's and Don'ts

### Do:
- **Do** mantener cobalto, marfil y Manrope con cerámica floral rosa/lila y cítrico localizado.
- **Do** vincular presión, geometría y luz al contacto, respetando la alternativa de versión Android.
- **Do** mantener texto e iconos fuera del shader y resto editorial sobre fondo opaco.
- **Do** usar una superficie óptica por producto y navegación con fondo real capturado.
- **Do** reservar volver circular para login y niveles secundarios, dos columnas iguales y categorías en hoja en menú.

### Don't:
- **Don't** anidar tarjeta o recuadro fotográfico dentro de la tarjeta glass de producto.
- **Don't** sustituir respuesta líquida por transparencia o desenfoque estático y llamarlo equivalente.
- **Don't** usar elevación nativa con centro relleno detrás de una superficie translúcida.
- **Don't** recuperar barra lateral fija, banner o buscador en tarjeta separada dentro del menú.
- **Don't** afirmar refracción equivalente entre API 24–32 y API 33+, material Apple nativo o fluidez física medida.

No canonizado: equivalencia óptica, simulación completa de Apple y rendimiento en teléfono físico no medidos. El control líquido lee escala de animación al entrar en composición, sin observador explícito posterior. Son límites comprobables, no reglas para rebajar futuras superficies. Esta actualización deriva del código; una captura de estado no acredita movimiento, arrastre ni todas las versiones Android.
