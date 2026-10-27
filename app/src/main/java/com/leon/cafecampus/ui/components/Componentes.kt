package com.leon.cafecampus.ui.components
import androidx.compose.foundation.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.platform.testTag
import androidx.compose.foundation.interaction.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.foundation.interaction.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.leon.cafecampus.R
import com.leon.cafecampus.model.EstadoPedido
import com.leon.cafecampus.data.local.entity.Producto
import com.leon.cafecampus.ui.theme.*
import java.util.Locale
fun dinero(n: Double)="S/ %.2f".format(Locale.US,n)
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun AppTopBar(titulo: String,volver: (() -> Unit)?=null,acciones: @Composable RowScope.()->Unit={}) {
    CenterAlignedTopAppBar(title={Text(titulo,style=MaterialTheme.typography.titleMedium)},navigationIcon={if(volver!=null) Box(Modifier.padding(start=8.dp)) { VolverCampus(volver) }},actions=acciones,
        windowInsets=WindowInsets(0),colors=TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor=CampusBlue,titleContentColor=Color.White,navigationIconContentColor=Color.White,actionIconContentColor=Color.White))
}
@Composable fun CafeButton(texto: String,enabled: Boolean=true,onClick: ()->Unit) {
    val interaction=remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(if(pressed) .975f else 1f,spring(dampingRatio=1f,stiffness=650f),label="Respuesta del botón")
    Button(onClick=onClick,enabled=enabled,interactionSource=interaction,modifier=Modifier.sombraCampus(16.dp,MaterialTheme.colorScheme.background.luminance()<.2f).graphicsLayer { scaleX=scale;scaleY=scale }.fillMaxWidth().heightIn(min=56.dp),shape=RoundedCornerShape(16.dp),contentPadding=PaddingValues(horizontal=18.dp,vertical=15.dp)) { Text(texto,style=MaterialTheme.typography.labelLarge) }
}
@Composable fun CafeTextField(valor: String,cambio: (String)->Unit,etiqueta: String,secreto: Boolean=false,teclado: KeyboardType=KeyboardType.Text) {
    OutlinedTextField(value=valor,onValueChange=cambio,label={Text(etiqueta)},modifier=Modifier.fillMaxWidth(),singleLine=true,shape=RoundedCornerShape(14.dp),
        visualTransformation=if(secreto) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions=androidx.compose.foundation.text.KeyboardOptions(keyboardType=teclado),
        colors=OutlinedTextFieldDefaults.colors(unfocusedContainerColor=MaterialTheme.colorScheme.surface,focusedContainerColor=MaterialTheme.colorScheme.surface,unfocusedBorderColor=MaterialTheme.colorScheme.outlineVariant))
}
// Open content plane: a divider groups records without enclosing every section.
@Composable fun Tarjeta(modifier: Modifier=Modifier,content: @Composable ColumnScope.()->Unit) {
    Column(modifier.fillMaxWidth().animateContentSize(androidx.compose.animation.core.tween(180)).padding(vertical=10.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
        content()
        HorizontalDivider(Modifier.padding(top=6.dp),color=MaterialTheme.colorScheme.outlineVariant.copy(alpha=.65f))
    }
}
@Composable fun ErrorTexto(texto: String?) { if(texto!=null) Surface(color=MaterialTheme.colorScheme.errorContainer,shape=MaterialTheme.shapes.small) { Text(texto,Modifier.fillMaxWidth().padding(12.dp),color=MaterialTheme.colorScheme.onErrorContainer,style=MaterialTheme.typography.bodyMedium) } }
@Composable fun Vacio(texto: String) {
    Column(Modifier.fillMaxWidth().padding(vertical=28.dp,horizontal=16.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(12.dp)) {
        if(texto.startsWith("Cargando")) CircularProgressIndicator(Modifier.size(28.dp),strokeWidth=2.dp)
        else Box(Modifier.size(116.dp),contentAlignment=Alignment.Center) {
            Surface(shape=CircleShape,color=MaterialTheme.colorScheme.secondaryContainer,modifier=Modifier.size(88.dp)) {}
            FotoLibre("cafe",Modifier.fillMaxSize());PetalosCampus(Modifier.fillMaxSize())
        }
        Text(texto,style=MaterialTheme.typography.titleSmall,color=MaterialTheme.colorScheme.onSurfaceVariant,textAlign=androidx.compose.ui.text.style.TextAlign.Center)
    }
}
fun fotoProducto(icono: String): Int = when(icono) { "cafe"->R.drawable.cafe_foto;"plato"->R.drawable.plato_foto;"jugo"->R.drawable.jugo_foto;"pan"->R.drawable.pan_foto;"botella"->R.drawable.botella_foto;else->R.drawable.dulce_foto }
@Composable fun ProductoIcono(icono: String,modifier: Modifier=Modifier) {
    Box(modifier.clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.55f)),contentAlignment=Alignment.Center) {
        Image(painterResource(fotoProducto(icono)),null,Modifier.fillMaxSize().padding(5.dp),contentScale=ContentScale.Fit)
    }
}
@Composable fun FotoLibre(icono: String,modifier: Modifier=Modifier) { Image(painterResource(fotoProducto(icono)),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun FotoCampus(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.campus_fresco_hero),null,modifier,contentScale=ContentScale.Crop) }
@Composable fun Citricos(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.citrus_particulas),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun HeroCampus(titulo: String,subtitulo: String,modifier: Modifier=Modifier,accion: String?=null,onAccion: ()->Unit={}) {
    val flota=FlotacionCampus()
    val densidad=androidx.compose.ui.platform.LocalDensity.current.density
    BoxWithConstraints(modifier.fillMaxWidth().heightIn(min=224.dp).clip(RoundedCornerShape(bottomStart=34.dp,bottomEnd=34.dp)).background(CampusBlue)) {
        Image(painterResource(R.drawable.estudio_cobalto),null,Modifier.matchParentSize(),contentScale=ContentScale.Crop)
        ImagenEscena("bolsa",Modifier.width(maxWidth*.58f).height(210.dp).align(Alignment.BottomEnd).offset(x=24.dp,y=5.dp).graphicsLayer { translationY=flota*densidad;rotationZ=-3f })
        Column(Modifier.fillMaxWidth(.55f).padding(start=20.dp,top=22.dp,bottom=24.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
            Text(titulo,color=Color.White,style=MaterialTheme.typography.headlineLarge)
            Text(subtitulo,color=Color.White.copy(alpha=.94f),style=MaterialTheme.typography.bodySmall)
            if(accion!=null) FilledTonalButton(onClick=onAccion,colors=ButtonDefaults.filledTonalButtonColors(containerColor=CampusCitron,contentColor=CampusInk),shape=RoundedCornerShape(14.dp),modifier=Modifier.heightIn(min=48.dp)) { Text(accion,style=MaterialTheme.typography.labelMedium) }
        }
    }
}

@Composable fun Disponible(disponible: Boolean) {
    val bg=if(disponible) Color(0xFFE9F3D9) else Color(0xFFFFE5E1)
    val fg=if(disponible) Color(0xFF39541C) else Color(0xFF922F29)
    Surface(color=bg,shape=RoundedCornerShape(8.dp)) { Text(if(disponible) "Disponible" else "Agotado",Modifier.padding(horizontal=9.dp,vertical=4.dp),color=fg,style=MaterialTheme.typography.labelSmall) }
}
fun coloresEstado(e: EstadoPedido): Pair<Color,Color> = when(e) {
    EstadoPedido.RECIBIDO->Color(0xFFE7EDFF) to Color(0xFF244BA0)
    EstadoPedido.PREPARANDO->Color(0xFFFFEBC8) to Color(0xFF805112)
    EstadoPedido.LISTO->Color(0xFFE5F2D8) to Color(0xFF395A1A)
    EstadoPedido.ENTREGADO->Color(0xFFE9EDF2) to Color(0xFF4C5C71)
    EstadoPedido.CANCELADO->Color(0xFFFFE5E1) to Color(0xFF922F29)
}
@Composable fun EstadoChip(estado: EstadoPedido) { val (bg,fg)=coloresEstado(estado);Surface(color=bg,shape=RoundedCornerShape(8.dp)) { Text(estado.etiqueta,Modifier.padding(horizontal=9.dp,vertical=5.dp),color=fg,style=MaterialTheme.typography.labelSmall) } }
@Composable fun ProductoCard(p: Producto,click: ()->Unit) {
    val contacto=remember { MutableInteractionSource() }
    val tacto=recordarTacto(contacto)
    val pulsado by contacto.collectIsPressedAsState()
    val escala by animateFloatAsState(if(pulsado) .975f else 1f,spring(dampingRatio=1f,stiffness=600f),label="Presión del producto")
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    val altura=(304f*androidx.compose.ui.platform.LocalDensity.current.fontScale.coerceAtLeast(1f)).dp
    Surface(onClick=click,interactionSource=contacto,modifier=Modifier.fillMaxWidth().height(altura).sombraCampus(26.dp,oscuro).deformacionLiquida(tacto,.5f).testTag("catalogo_${p.id}").semantics { contentDescription="Ver ${p.nombre}" }.graphicsLayer { scaleX=escala;scaleY=escala },shape=RoundedCornerShape(26.dp),
        color=if(oscuro) Color(0xE51F324E) else Color(0xDCF4F1FA),contentColor=MaterialTheme.colorScheme.onSurface,shadowElevation=0.dp,border=BorderStroke(1.dp,if(oscuro) Color.White.copy(alpha=.22f) else Color.White.copy(alpha=.85f))) {
        Box {
            Image(painterResource(R.drawable.papel_campus),null,Modifier.matchParentSize().lenteCampus(tacto.intensidad.value,tacto.punto.value).alpha(if(oscuro) .08f else .18f),contentScale=ContentScale.Crop)


            PetalosCampus(Modifier.size(76.dp).align(Alignment.TopEnd))
            Column(Modifier.fillMaxSize().padding(14.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
                FotoLibre(p.icono,Modifier.fillMaxWidth().height(142.dp))
                Text(p.nombre,style=MaterialTheme.typography.titleMedium,minLines=2,maxLines=2,overflow=androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) { Text(p.categoria,style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant);if(!p.disponible) Icon(Icons.Default.Block,"Agotado",Modifier.size(16.dp),tint=MaterialTheme.colorScheme.error) }
                Spacer(Modifier.weight(1f))
                Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) {
                    Column(verticalArrangement=Arrangement.spacedBy(2.dp)) { Text(dinero(p.precio),style=MaterialTheme.typography.titleMedium,color=MaterialTheme.colorScheme.primary);Text("Ver detalles",style=MaterialTheme.typography.labelSmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
                    Icon(Icons.Default.NorthEast,null,Modifier.size(21.dp),tint=MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable fun Categorias(actual: String,cambiar: (String)->Unit) {
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
        listOf("Todo","Desayunos","Almuerzos","Bebidas","Snacks").forEach { FilterChip(selected=actual==it,onClick={cambiar(it)},label={Text(it)},shape=RoundedCornerShape(12.dp),colors=FilterChipDefaults.filterChipColors(selectedContainerColor=MaterialTheme.colorScheme.primary,selectedLabelColor=MaterialTheme.colorScheme.onPrimary),border=FilterChipDefaults.filterChipBorder(enabled=true,selected=actual==it,borderColor=MaterialTheme.colorScheme.outlineVariant)) }
    }
}
@Composable fun StepperEstado(estado: EstadoPedido) {
    if(estado==EstadoPedido.CANCELADO) { EstadoChip(estado);Text("Este pedido fue cancelado.",color=MaterialTheme.colorScheme.onSurfaceVariant);return }
    val pasos=listOf(EstadoPedido.RECIBIDO,EstadoPedido.PREPARANDO,EstadoPedido.LISTO,EstadoPedido.ENTREGADO)
    val notas=listOf("Tu pedido llegó a la cocina","Están cocinando tu pedido","Puedes pasar a recogerlo","¡Buen provecho!")
    pasos.forEachIndexed { i,p ->
        val tono by animateColorAsState(if(i<=estado.ordinal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,tween(220),label="Estado del pedido")
        Row(Modifier.fillMaxWidth().heightIn(min=82.dp),horizontalArrangement=Arrangement.spacedBy(16.dp)) {
            Column(horizontalAlignment=Alignment.CenterHorizontally) {
                Surface(shape=CircleShape,color=tono,modifier=Modifier.size(36.dp)) { Box(contentAlignment=Alignment.Center) { if(i<estado.ordinal) Icon(Icons.Default.Check,null,tint=MaterialTheme.colorScheme.onPrimary,modifier=Modifier.size(19.dp)) else Text("${i+1}",color=if(i<=estado.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,style=MaterialTheme.typography.labelLarge) } }
                if(i<3) Box(Modifier.width(2.dp).height(48.dp).background(if(i<estado.ordinal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant))
            }
            Column(verticalArrangement=Arrangement.spacedBy(4.dp)) { Text(p.etiqueta,style=MaterialTheme.typography.titleMedium,color=if(i<=estado.ordinal) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant);Text(notas[i],style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
        }
    }
}
@Composable fun EstadoContador(cantidad: Int,estado: EstadoPedido,modifier: Modifier=Modifier,click: ()->Unit) {
    val (bg,fg)=coloresEstado(estado)
    Surface(onClick=click,modifier=modifier,shape=RoundedCornerShape(12.dp),color=Color.Transparent,contentColor=MaterialTheme.colorScheme.onSurface) {
        Column(Modifier.padding(vertical=17.dp,horizontal=4.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(6.dp)) { Text("$cantidad",style=MaterialTheme.typography.headlineMedium);Text(if(estado==EstadoPedido.RECIBIDO) "Nuevos" else estado.etiqueta,style=MaterialTheme.typography.labelMedium) }
    }
}
@Composable fun DisponibilidadChip(disponible: Boolean,enabled: Boolean=true,click: ()->Unit) {
    FilterChip(selected=disponible,onClick=click,enabled=enabled,label={Text(if(disponible) "Disponible" else "Agotado")},shape=RoundedCornerShape(10.dp),colors=FilterChipDefaults.filterChipColors(containerColor=Color(0xFFFFE5E1),labelColor=Color(0xFF922F29),selectedContainerColor=Color(0xFFE9F3D9),selectedLabelColor=Color(0xFF39541C)))
}
@Composable fun Seccion(titulo: String,subtitulo: String?=null,modifier: Modifier=Modifier) { Column(modifier,verticalArrangement=Arrangement.spacedBy(5.dp)) { Text(titulo,style=MaterialTheme.typography.titleMedium);if(subtitulo!=null) Text(subtitulo,style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) } }

// Native text and generated cutouts share a compact contextual header.
@Composable fun EscenaCampus(titulo: String,subtitulo: String,escena: String,modifier: Modifier=Modifier) {
    val flota=FlotacionCampus()
    val densidad=androidx.compose.ui.platform.LocalDensity.current.density
    BoxWithConstraints(modifier.fillMaxWidth().heightIn(min=196.dp)) {
        Box(Modifier.matchParentSize().clip(RoundedCornerShape(topStart=28.dp,bottomEnd=42.dp)).background(CampusBlue)) {
            Image(painterResource(R.drawable.estudio_cobalto),null,Modifier.matchParentSize(),contentScale=ContentScale.Crop)
        }
        AdornoFloral(Modifier.width(94.dp).height(80.dp).align(Alignment.TopEnd).offset(x=16.dp,y=(-18).dp))
        ImagenEscena(escena,Modifier.width(maxWidth*.55f).height(192.dp).align(Alignment.BottomEnd).offset(x=14.dp,y=16.dp).graphicsLayer { translationY=flota*densidad;rotationZ=-3f })
        Column(Modifier.fillMaxWidth(.55f).padding(start=18.dp,top=22.dp,bottom=24.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Text(titulo,style=MaterialTheme.typography.headlineSmall,color=Color.White)
            Text(subtitulo,style=MaterialTheme.typography.bodySmall,color=Color.White.copy(alpha=.94f))
        }
    }
}

@Composable fun ImagenEscena(escena: String,modifier: Modifier=Modifier) {
    val recurso=when(escena) { "bolsa"->R.drawable.bolsa_foto;"seguimiento"->R.drawable.seguimiento_foto;"historial"->R.drawable.historial_foto;"perfil"->R.drawable.perfil_foto;"cocina"->R.drawable.cocina_foto;else->R.drawable.panel_foto }
    Image(painterResource(recurso),null,modifier,contentScale=ContentScale.Fit)
}

@Composable fun FondoCampus(modifier: Modifier=Modifier) {
    // Stable opaque ground. Glass is reserved for individual catalogue items.
    Box(modifier.background(MaterialTheme.colorScheme.background))
}
@Composable private fun FlotacionCampus(): Float {
    val contexto=androidx.compose.ui.platform.LocalContext.current
    val habilitado=remember(contexto) { android.provider.Settings.Global.getFloat(contexto.contentResolver,android.provider.Settings.Global.ANIMATOR_DURATION_SCALE,1f)>0f }
    if(!habilitado) return 0f
    val transicion=rememberInfiniteTransition(label="Escena viva")
    val y by transicion.animateFloat(-3f,3f,infiniteRepeatable(tween(4400,easing=FastOutSlowInEasing),RepeatMode.Reverse),label="Flotación de la escena")
    return y
}


@Composable fun BotonCristal(descripcion: String,onClick: ()->Unit,contenido: @Composable ()->Unit) {
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    val fuente=remember { MutableInteractionSource() }
    val tacto=recordarTacto(fuente)
    Box(Modifier.size(56.dp).deformacionLiquida(tacto).sombraCampus(28.dp,oscuro)) {
        Box(Modifier.matchParentSize().clip(CircleShape).background(if(oscuro) Color(0xB32C3D58) else Color(0x9FF8F5FB)).border(1.dp,Color.White.copy(alpha=.8f),CircleShape)) {
            Image(painterResource(R.drawable.papel_campus),null,Modifier.matchParentSize().lenteCampus(tacto.intensidad.value,tacto.punto.value).alpha(if(oscuro) .12f else .28f),contentScale=ContentScale.Crop)
            Box(Modifier.matchParentSize().drawBehind { val p=tacto.intensidad.value;drawCircle(androidx.compose.ui.graphics.Brush.radialGradient(listOf(Color.White.copy(alpha=.14f+.22f*p),Color.Transparent),center=tacto.punto.value,radius=size.width*.8f)) })
            IconButton(onClick=onClick,interactionSource=fuente,modifier=Modifier.matchParentSize().semantics { contentDescription=descripcion }) { contenido() }
        }
    }
}
@Composable fun VolverCampus(volver: ()->Unit) { BotonCristal("Volver",volver) { Icon(Icons.AutoMirrored.Filled.ArrowBack,null,Modifier.size(25.dp),tint=MaterialTheme.colorScheme.onSurface) } }
@Composable fun BarraCampus(content: @Composable ()->Unit) {
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    Box(Modifier.padding(horizontal=16.dp,vertical=10.dp).sombraCampus(28.dp,oscuro)) {
        Surface(shape=RoundedCornerShape(28.dp),color=if(oscuro) Color(0xED23334E) else Color(0xDFF5F1F8),border=BorderStroke(1.dp,Color.White.copy(alpha=.66f))) {
            Box {
                Image(painterResource(R.drawable.papel_campus),null,Modifier.matchParentSize().lenteCampus().alpha(if(oscuro) .06f else .12f),contentScale=ContentScale.Crop)
                content()
            }
        }
    }
}
@Composable fun AdornoFloral(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.ramita_floral),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun PetalosCampus(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.petalos_campus),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun FlorPorcelana(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.flor_porcelana),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun LazoCampus(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.lazo_lila),null,modifier,contentScale=ContentScale.Fit) }
