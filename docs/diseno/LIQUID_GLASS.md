# Liquid Glass en CafeCampus

La dirección solicitada por Daniella es Liquid Glass dinámico, localizado en catálogo, controles circulares y navegación. El resto emplea superficies sólidas de marfil/cobalto, sombras suaves y detalles de cerámica, flores, rosa y lila. Las categorías se abren en un selector; no existe barra lateral fija ni banner en el menú.

## Investigación y decisiones

Apple describe el material por la combinación de refracción, flexibilidad ante el tacto y transformaciones continuas entre controles. La luz y el movimiento forman una sola respuesta. Se tomó esa relación como referencia para una implementación propia de Android, sin afirmar que es el componente nativo de Apple. Fuente primaria: [Meet Liquid Glass, WWDC25](https://developer.apple.com/videos/play/wwdc2025/219/).

Se revisó también [Backdrop de Kyant0](https://github.com/Kyant0/AndroidLiquidGlass) y su [documentación](https://kyant.gitbook.io/backdrop). Confirma el enfoque de copiar el contenido de fondo a otra capa con efectos. No se integró esa biblioteca ni se copió su código: sus dependencias actuales difieren de las versiones del laboratorio. La implementación local conserva el entorno existente.

[Android: gráficos en Compose](https://developer.android.com/develop/ui/compose/graphics/draw/modifiers) documenta GraphicsLayer y la grabación de instrucciones de dibujo. [Android: AGSL](https://developer.android.com/develop/ui/views/graphics/agsl/using-agsl) documenta RuntimeShader y su integración con RenderEffect.

## Implementación comprobable

- `LiquidoCampus.kt`: interacción con punto de contacto, presión por resorte y deformación reversible. La navegación admite pulsación y arrastre; la gota se desplaza con Animatable y su velocidad cambia la cola. Dos campos de distancia se unen suavemente para modelar el grosor del indicador. Es una sola capa de navegación, sin tarjeta dentro de tarjeta.
- `LenteCampus.kt`: la refracción y el reflejo varían con el contacto. El shader se aplica a la imagen o fondo capturado, nunca a las letras e iconos. La barra copia la página mediante GraphicsLayer y la alinea por coordenadas reales; no usa un mockup del fondo.
- `MenuScreen.kt`: el círculo de búsqueda se transforma en cápsula en su misma posición; el campo de texto sigue siendo nativo. Cerrar limpia la búsqueda y recupera el saludo.
- `SombraCampus.kt`: sombras fuera de la silueta. Se eliminó la elevación nativa en superficies translúcidas porque se veía a través de ellas y producía el rectángulo interior denunciado por Daniella.

## Límites

Desde API33 se usa AGSL; en API24–32 permanece una alternativa geométrica, sin prometer la misma refracción. La presión y la deformación se desactivan cuando la escala de animación del sistema es cero. La flotación decorativa también se detiene. No hay sensor de inclinación ni una simulación óptica completa de Apple; es una interpretación local para Compose. Las capturas estáticas prueban el aspecto; la prueba de arrastre acredita navegación funcional, no una medición de rendimiento. No se midió fluidez en teléfono físico.
