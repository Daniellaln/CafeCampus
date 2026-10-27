package com.leon.cafecampus.ui.components
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
class FondoCristal(val capa: GraphicsLayer) { var origen by mutableStateOf(Offset.Zero) }
val LocalFondoCristal=staticCompositionLocalOf<FondoCristal?> { null }
@Composable fun recordarFondoCristal(): FondoCristal { val capa=rememberGraphicsLayer();return remember(capa) { FondoCristal(capa) } }
fun Modifier.capturarFondo(fondo: FondoCristal): Modifier=onGloballyPositioned { fondo.origen=it.positionInRoot() }.drawWithContent {
    fondo.capa.record { this@drawWithContent.drawContent() };drawLayer(fondo.capa)
}

@Composable fun MaterialDeFondo(modifier: Modifier=Modifier,presion: Float=0f) {
    val fondo=LocalFondoCristal.current
    var sitio by remember { mutableStateOf(Offset.Zero) }
    if(fondo!=null) androidx.compose.foundation.layout.Box(modifier.onGloballyPositioned { sitio=it.positionInRoot() }.lenteCampus(presion).drawWithContent {
        translate(fondo.origen.x-sitio.x,fondo.origen.y-sitio.y) { drawLayer(fondo.capa) }
    })
}

