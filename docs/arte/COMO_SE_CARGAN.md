# Imágenes locales

Las imágenes usadas por Android viven en app/src/main/res/drawable-nodpi y se incluyen en el APK. No se descargan en ejecución. Compose las obtiene mediante painterResource(R.drawable.nombre); Image y ContentScale controlan cómo encajan en el espacio.

nodpi evita el preescalado automático por densidad de las imágenes. drawable también es una carpeta válida, pero los bitmaps sin calificador se tratan como densidad base y pueden preescalarse. Para fotografías que ya ajustamos en Compose, conservamos los píxeles del recurso y dimensionamos su presentación.

Fuente primaria: https://developer.android.com/training/multiscreen/screendensities

Los iconos originales SVG se conservan en design/icons-svg; IconosCafe.kt usa ImageVector con los mismos paths. Los originales generados y prompts exactos están fuera del proyecto Android, en arte-original. La foto aportada tiene su propio registro de origen; no es una imagen generada.
