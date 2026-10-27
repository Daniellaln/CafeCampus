package com.leon.cafecampus.ui.components
import android.os.Build
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.layer.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.*

@Composable fun movimientoLiquido(): Boolean {
    val contexto=LocalContext.current
    return remember(contexto) { android.provider.Settings.Global.getFloat(contexto.contentResolver,android.provider.Settings.Global.ANIMATOR_DURATION_SCALE,1f)>0f }
}
class TactoLiquido(val intensidad: State<Float>,val punto: State<Offset>)
@Composable fun recordarTacto(fuente: MutableInteractionSource): TactoLiquido {
    var tocado by remember { mutableStateOf(false) }
    val punto=remember { mutableStateOf(Offset(.5f,.5f)) }
    val animar=movimientoLiquido()
    val presion=animateFloatAsState(if(tocado && animar) 1f else 0f,spring(dampingRatio=.58f,stiffness=430f),label="Elasticidad del material")
    LaunchedEffect(fuente) { fuente.interactions.collect { accion -> when(accion) {
        is PressInteraction.Press -> { punto.value=accion.pressPosition;tocado=true }
        is PressInteraction.Release,is PressInteraction.Cancel -> tocado=false
    } } }
    return remember(presion) { TactoLiquido(presion,punto) }
}
fun Modifier.deformacionLiquida(tacto: TactoLiquido,amplitud: Float=1f): Modifier=graphicsLayer {
    val p=tacto.intensidad.value*amplitud
    scaleX=1f+.025f*p;scaleY=1f-.048f*p
    translationX=((tacto.punto.value.x/size.width.coerceAtLeast(1f))-.5f).coerceIn(-.5f,.5f)*4f*p
    translationY=2f*p
}

// One refracting plane. The selected droplet is a change of thickness, not a nested panel.
@Composable fun NavegacionLiquida(items: List<Pair<String,ImageVector>>,actual: Int,elegir: (Int)->Unit,plano: GraphicsLayer,origen: Offset) {
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    val veloOscuro=MaterialTheme.colorScheme.surface.copy(alpha=.46f)
    val posicion=remember { Animatable(actual.toFloat()) }
    val alcance=rememberCoroutineScope()
    val animar=movimientoLiquido()
    var ancho by remember { mutableFloatStateOf(1f) }
    var sitio by remember { mutableStateOf(Offset.Zero) }
    var arrastrando by remember { mutableStateOf(false) }
    val escoger by rememberUpdatedState(elegir)
    var valorArrastre by remember { mutableFloatStateOf(actual.toFloat()) }
    LaunchedEffect(actual,arrastrando,animar) { if(!arrastrando) { if(animar) posicion.animateTo(actual.toFloat(),spring(dampingRatio=.66f,stiffness=370f)) else posicion.snapTo(actual.toFloat()) } }
    val deformacion=(abs(posicion.velocity)/15f).coerceIn(0f,1f)
    val contacto=if(arrastrando) .8f else deformacion
    Box(Modifier.navigationBarsPadding().padding(horizontal=16.dp,vertical=10.dp).fillMaxWidth().height(72.dp).testTag("navegacion_liquida").sombraCampus(30.dp,oscuro).onGloballyPositioned { sitio=it.positionInRoot();ancho=it.size.width.toFloat() }
        .draggable(rememberDraggableState { delta ->
            valorArrastre=(valorArrastre+delta/(ancho/items.size)).coerceIn(0f,(items.size-1).toFloat())
            alcance.launch { posicion.snapTo(valorArrastre) }
        },Orientation.Horizontal,onDragStarted={ arrastrando=true;valorArrastre=posicion.value },onDragStopped={ velocidad ->
            val destino=(valorArrastre+velocidad/ancho*.06f).roundToInt().coerceIn(items.indices)
            escoger(destino);arrastrando=false
        })) {
        Box(Modifier.matchParentSize().clip(RoundedCornerShape(30.dp))) {
            // Capture of the actual page, not a screenshot or an unrelated texture.
            Box(Modifier.matchParentSize().blur(if(oscuro) 0.dp else 14.dp,BlurredEdgeTreatment.Unbounded).lenteCampus(contacto).clip(RoundedCornerShape(30.dp)).drawWithContent {
                translate(origen.x-sitio.x,origen.y-sitio.y) { drawLayer(plano) }
            })
            Canvas(Modifier.matchParentSize()) {
                drawRoundRect(if(oscuro) veloOscuro else Color.White.copy(alpha=.22f),cornerRadius=androidx.compose.ui.geometry.CornerRadius(30.dp.toPx()))
                drawRoundRect(Brush.linearGradient(listOf(Color.White.copy(alpha=.85f),Color.White.copy(alpha=.12f),Color.White.copy(alpha=.45f))),topLeft=Offset(.5.dp.toPx(),.5.dp.toPx()),size=androidx.compose.ui.geometry.Size(size.width-1.dp.toPx(),size.height-1.dp.toPx()),cornerRadius=androidx.compose.ui.geometry.CornerRadius(30.dp.toPx()),style=Stroke(1.dp.toPx()))
            }
            Canvas(Modifier.matchParentSize().reflejosRosa(30.dp)) {}
            GotaSeleccion(posicion.value,posicion.velocity,items.size,oscuro,contacto,Modifier.matchParentSize())
        }
        Row(Modifier.fillMaxSize().clip(RoundedCornerShape(30.dp)),verticalAlignment=Alignment.CenterVertically) {
            items.forEachIndexed { i,(titulo,icono) ->
                Column(Modifier.weight(1f).fillMaxHeight().selectable(selected=actual==i,interactionSource=remember { MutableInteractionSource() },indication=null,role=Role.Tab,onClick={escoger(i)}).padding(vertical=9.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(5.dp)) {
                    Icon(icono,null,Modifier.size(24.dp),tint=if(actual==i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(titulo,style=MaterialTheme.typography.labelSmall,color=if(actual==i) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
@Composable private fun GotaSeleccion(posicion: Float,velocidad: Float,total: Int,oscuro: Boolean,presion: Float,modifier: Modifier) {
    val tinta=MaterialTheme.colorScheme.primary.copy(alpha=.10f)
    if(Build.VERSION.SDK_INT>=33) GotaAgsl(posicion,velocidad,total,oscuro,presion,modifier)
    else Canvas(modifier) {
        val celda=size.width/total;val extension=min(abs(velocidad)*2.dp.toPx(),12.dp.toPx())
        drawRoundRect(tinta,Offset((posicion+.5f)*celda-celda*.42f-extension/2,8.dp.toPx()),androidx.compose.ui.geometry.Size(celda*.84f+extension,54.dp.toPx()),androidx.compose.ui.geometry.CornerRadius(28.dp.toPx()))
    }
}
@androidx.annotation.RequiresApi(33)
@Composable private fun GotaAgsl(posicion: Float,velocidad: Float,total: Int,oscuro: Boolean,presion: Float,modifier: Modifier) {
    val tono=MaterialTheme.colorScheme.primary
    val shader=remember { android.graphics.RuntimeShader("""
        uniform float2 tam;
        uniform float centro;
        uniform float cola;
        uniform float ancho;
        uniform float fuerza;
        uniform float noche;
        uniform float3 tono;
        float redonda(float2 p,float2 b,float r) {float2 q=abs(p)-b+r;return length(max(q,0.0))+min(max(q.x,q.y),0.0)-r;}
        float unir(float a,float b,float k) {float h=clamp(.5+.5*(b-a)/k,0.0,1.0);return mix(b,a,h)-k*h*(1.0-h);}
        float forma(float2 p) {
            float a=redonda(p-float2(centro,tam.y*.5),float2(ancho*.42,tam.y*.38*(1.0-.055*fuerza)),tam.y*.29);
            float b=length(p-float2(cola,tam.y*.5))-tam.y*.27*fuerza;
            return unir(a,b,max(1.0,tam.y*.22*fuerza));
        }
        half4 main(float2 p) {
            float d=forma(p);float mascara=1.0-smoothstep(-1.0,1.0,d);
            float2 n=normalize(float2(forma(p+float2(1,0))-forma(p-float2(1,0)),forma(p+float2(0,1))-forma(p-float2(0,1)))+float2(.001));
            float borde=exp(-abs(d)*.32);
            float luz=pow(max(dot(n,normalize(float2(-.55,-.85))),0.0),3.0)*borde;
            float3 base=mix(float3(.98),tono,.20+.12*noche);
            float3 color=mix(base,float3(1.0),luz*.88);
            float alpha=(.13+.60*borde+.06*fuerza)*mascara;
            return half4(half3(color*alpha),half(alpha));
        }
    """) }
    Canvas(modifier) {
        val celda=size.width/total
        shader.setFloatUniform("tam",size.width,size.height)
        shader.setFloatUniform("centro",(posicion+.5f)*celda)
        shader.setFloatUniform("cola",(posicion+.5f)*celda-velocidad.coerceIn(-10f,10f)*2.7.dp.toPx())
        shader.setFloatUniform("ancho",celda)
        shader.setFloatUniform("fuerza",presion)
        shader.setFloatUniform("noche",if(oscuro) 1f else 0f)
        shader.setFloatUniform("tono",tono.red,tono.green,tono.blue)
        drawRect(ShaderBrush(shader))
    }
}
