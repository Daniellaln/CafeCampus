package com.leon.cafecampus.ui.components
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.graphics.layer.drawLayer
import com.leon.cafecampus.ui.theme.LocalAdminCampus
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.*
import com.leon.cafecampus.R

// Optical highlights follow the rounded lens. This is not an extra panel.
fun Modifier.reflejosRosa(radio: Dp,energia: Float=0f): Modifier=drawWithCache {
    val r=radio.toPx();val grosor=1.5.dp.toPx()
    val borde=Brush.linearGradient(listOf(Color.White.copy(alpha=.98f),Color.White.copy(alpha=.36f),Color.White.copy(alpha=.94f)),start=Offset.Zero,end=Offset(size.width,size.height))
    val luz=Brush.verticalGradient(listOf(Color.White.copy(alpha=.52f+energia*.14f),Color.White.copy(alpha=.02f),Color.White.copy(alpha=.18f)))
    onDrawWithContent {
        drawContent()
        drawRoundRect(luz,cornerRadius=CornerRadius(r))
        drawRoundRect(borde,Offset(grosor/2,grosor/2),Size(size.width-grosor,size.height-grosor),CornerRadius(r),style=Stroke(grosor))
        drawArc(Color.White.copy(alpha=.7f),startAngle=195f,sweepAngle=65f,useCenter=false,topLeft=Offset(3.dp.toPx(),3.dp.toPx()),size=Size((r*2).coerceAtMost(size.width-6.dp.toPx()),(r*2).coerceAtMost(size.height-6.dp.toPx())),style=Stroke(2.dp.toPx(),cap=StrokeCap.Round))
    }
}
@Composable fun CristalRosa(modifier: Modifier=Modifier,radio: Dp=28.dp,descripcion: String?=null,seleccionado: Boolean=false,enabled: Boolean=true,accion: ()->Unit,contenido: @Composable BoxScope.()->Unit) {
    val fuente=remember { MutableInteractionSource() };val tacto=recordarTacto(fuente)
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    val fondo=LocalFondoCristal.current
    var sitio by remember { mutableStateOf(Offset.Zero) }
    val forma=RoundedCornerShape(radio)
    val tinta=if(seleccionado) MaterialTheme.colorScheme.primary.copy(alpha=if(oscuro) .16f else .075f) else Color.White.copy(alpha=if(oscuro) .035f else .025f)
    Box(modifier.alpha(if(enabled) 1f else .38f).onGloballyPositioned { sitio=it.positionInRoot() }.deformacionLiquida(tacto).sombraCampus(radio,oscuro).clip(forma).background(tinta).clickable(interactionSource=fuente,indication=null,enabled=enabled,role=Role.Button,onClick=accion).semantics { if(descripcion!=null) contentDescription=descripcion }) {
        if(fondo!=null) Box(Modifier.matchParentSize().lenteCampus(tacto.intensidad.value,tacto.punto.value).drawWithContent {
            translate(fondo.origen.x-sitio.x,fondo.origen.y-sitio.y) { drawLayer(fondo.capa) }
        })
        Box(Modifier.matchParentSize().background(tinta).reflejosRosa(radio,tacto.intensidad.value))
        contenido()
    }
}
