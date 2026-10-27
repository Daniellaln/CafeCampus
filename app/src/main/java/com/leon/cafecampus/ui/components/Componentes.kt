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
        windowInsets=WindowInsets(0),colors=TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor=Color.Transparent,titleContentColor=MaterialTheme.colorScheme.onSurface,navigationIconContentColor=MaterialTheme.colorScheme.primary,actionIconContentColor=MaterialTheme.colorScheme.primary))
}
@Composable fun CafeButton(texto: String,enabled: Boolean=true,onClick: ()->Unit) {
    CristalRosa(Modifier.fillMaxWidth().height(56.dp),28.dp,enabled=enabled,accion=onClick) {
        Row(Modifier.fillMaxWidth().alpha(if(enabled) 1f else .38f).padding(horizontal=20.dp,vertical=17.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) { Text(texto,style=MaterialTheme.typography.labelLarge,color=MaterialTheme.colorScheme.primary);Icon(IconosCafe.ArrowForward,null,Modifier.size(22.dp),tint=MaterialTheme.colorScheme.primary) }
    }
}
@Composable fun CafeTextField(valor: String,cambio: (String)->Unit,etiqueta: String,secreto: Boolean=false,teclado: KeyboardType=KeyboardType.Text,icono: androidx.compose.ui.graphics.vector.ImageVector?=null) {
    OutlinedTextField(value=valor,onValueChange=cambio,leadingIcon=if(icono!=null) ({ Icon(icono,null,tint=MaterialTheme.colorScheme.onSurfaceVariant) }) else null,label={Text(etiqueta)},modifier=Modifier.fillMaxWidth(),singleLine=true,shape=RoundedCornerShape(14.dp),
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
        else Box(Modifier.size(76.dp).background(MaterialTheme.colorScheme.secondaryContainer,CircleShape),contentAlignment=Alignment.Center) { Icon(IconosCafe.Coffee,null,Modifier.size(34.dp),tint=MaterialTheme.colorScheme.primary) }
        Text(texto,style=MaterialTheme.typography.titleSmall,color=MaterialTheme.colorScheme.onSurfaceVariant,textAlign=androidx.compose.ui.text.style.TextAlign.Center)
    }
}
fun fotoProducto(icono: String): Int = when(icono) { "cafe"->R.drawable.cafe_rosa;"plato"->R.drawable.plato_rosa;"jugo"->R.drawable.jugo_rosa;"pan"->R.drawable.pan_rosa;"botella"->R.drawable.chicha_rosa;else->R.drawable.dulce_rosa }
@Composable fun ProductoIcono(icono: String,modifier: Modifier=Modifier) {
    Box(modifier.clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.55f)),contentAlignment=Alignment.Center) {
        Image(painterResource(fotoProducto(icono)),null,Modifier.fillMaxSize().padding(5.dp),contentScale=ContentScale.Fit)
    }
}
@Composable fun FotoLibre(icono: String,modifier: Modifier=Modifier) { Image(painterResource(fotoProducto(icono)),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun FotoCampus(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.campus_fresco_hero),null,modifier,contentScale=ContentScale.Crop) }
@Composable fun Citricos(modifier: Modifier=Modifier) { Image(painterResource(R.drawable.citrus_particulas),null,modifier,contentScale=ContentScale.Fit) }
@Composable fun HeroCampus(titulo: String,subtitulo: String,modifier: Modifier=Modifier,accion: String?=null,onAccion: ()->Unit={}) {
    EscenaCampus(titulo,subtitulo,"acceso",modifier)
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
@Composable fun ProductoCard(p: Producto,click: ()->Unit,favorito: Boolean=false,alternarFavorito: ()->Unit={}) {
    val fuente=remember { MutableInteractionSource() };val tacto=recordarTacto(fuente)
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    val altura=(260f*androidx.compose.ui.platform.LocalDensity.current.fontScale.coerceAtLeast(1f)).dp
    val fondo=recordarFondoCristal()
    val papel=if(oscuro) Color(0xFF342C33) else Color(0xFFFDFCFC)
    Surface(onClick=click,interactionSource=fuente,modifier=Modifier.fillMaxWidth().height(altura).deformacionLiquida(tacto,.45f).sombraCampus(20.dp,oscuro).testTag("catalogo_${p.id}").semantics { contentDescription="Ver ${p.nombre}" },shape=RoundedCornerShape(20.dp),color=papel,shadowElevation=0.dp,border=BorderStroke(1.2.dp,Color.White.copy(alpha=if(oscuro) .35f else .95f))) {
        Box {
            Column(Modifier.matchParentSize().capturarFondo(fondo)) { Image(painterResource(fotoProducto(p.icono)),null,Modifier.fillMaxWidth().height(154.dp),contentScale=ContentScale.Crop);Box(Modifier.fillMaxWidth().weight(1f).background(papel)) }
            CompositionLocalProvider(LocalFondoCristal provides fondo) {
                Column(Modifier.fillMaxSize()) {
                    Box(Modifier.fillMaxWidth().height(154.dp)) {
                        Box(Modifier.align(Alignment.TopEnd).padding(5.dp)) {
                            CristalRosa(Modifier.size(48.dp).testTag("favorito_${p.id}"),24.dp,if(favorito) "Quitar ${p.nombre} de favoritos" else "Guardar ${p.nombre} en favoritos",accion=alternarFavorito) { Icon(if(favorito) IconosCafe.Favorite else IconosCafe.FavoriteBorder,null,Modifier.align(Alignment.Center).size(24.dp),tint=MaterialTheme.colorScheme.primary) }
                        }
                    }
                    Column(Modifier.fillMaxSize().padding(horizontal=12.dp,vertical=7.dp)) {
                        Text(p.nombre,style=MaterialTheme.typography.titleMedium,maxLines=2,overflow=androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        Spacer(Modifier.weight(1f))
                        Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) {
                            Column { Text(dinero(p.precio),style=MaterialTheme.typography.titleLarge,color=MaterialTheme.colorScheme.primary);if(!p.disponible) Text("Agotado",style=MaterialTheme.typography.labelSmall,color=MaterialTheme.colorScheme.error) }
                            CristalRosa(Modifier.size(48.dp),24.dp,"Ver detalles de ${p.nombre}",accion=click) { Icon(IconosCafe.ArrowForward,null,Modifier.align(Alignment.Center).size(25.dp),tint=MaterialTheme.colorScheme.primary) }
                        }
                    }
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
                Surface(shape=CircleShape,color=tono,modifier=Modifier.size(36.dp)) { Box(contentAlignment=Alignment.Center) { if(i<estado.ordinal) Icon(IconosCafe.Check,null,tint=MaterialTheme.colorScheme.onPrimary,modifier=Modifier.size(19.dp)) else Text("${i+1}",color=if(i<=estado.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,style=MaterialTheme.typography.labelLarge) } }
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

// The photography is one composed background per task, never a floating cutout.
@Composable fun EscenaCampus(titulo: String,subtitulo: String,escena: String,modifier: Modifier=Modifier) {
    val admin=LocalAdminCampus.current
    Column(modifier.fillMaxWidth().heightIn(min=if(admin) 116.dp else 132.dp).padding(top=12.dp,bottom=18.dp),verticalArrangement=Arrangement.Center) {
        Text(titulo,Modifier.fillMaxWidth(if(admin) .72f else .70f),style=if(admin) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text(subtitulo,Modifier.fillMaxWidth(.70f),style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
@Composable fun FondoCampus(modifier: Modifier=Modifier,ruta: String="") {
    val fondo=LocalFondoCristal.current
    val admin=LocalAdminCampus.current
    val recurso=when {
        ruta=="perfil" -> R.drawable.ambiente_perfil
        ruta=="confirmar" -> R.drawable.ambiente_bolsa
        ruta.startsWith("seguimiento") || ruta=="pedidos" -> R.drawable.ambiente_pedidos
        ruta=="splash" || ruta=="registro" || (ruta=="login" && !admin) -> R.drawable.ambiente_acceso
        admin && (ruta=="admin_pedidos" || ruta.startsWith("admin_detalle")) -> R.drawable.ambiente_despacho
        admin && (ruta=="admin_menu" || ruta.startsWith("formulario")) -> R.drawable.ambiente_catalogo
        admin -> R.drawable.ambiente_gestor
        else -> R.drawable.fondo_cafe_blanco
    }
    Box(modifier.then(if(fondo!=null) Modifier.capturarFondo(fondo) else Modifier).background(MaterialTheme.colorScheme.background)) {
        Image(painterResource(recurso),null,Modifier.matchParentSize().alpha(if(MaterialTheme.colorScheme.background.luminance()<.2f) .12f else 1f),contentScale=ContentScale.Crop)
    }
}
@Composable private fun FlotacionCampus(): Float {
    val contexto=androidx.compose.ui.platform.LocalContext.current
    val habilitado=remember(contexto) { android.provider.Settings.Global.getFloat(contexto.contentResolver,android.provider.Settings.Global.ANIMATOR_DURATION_SCALE,1f)>0f }
    if(!habilitado) return 0f
    val transicion=rememberInfiniteTransition(label="Escena viva")
    val y by transicion.animateFloat(-3f,3f,infiniteRepeatable(tween(4400,easing=FastOutSlowInEasing),RepeatMode.Reverse),label="Flotación de la escena")
    return y
}


@Composable fun BotonCristal(descripcion: String,onClick: ()->Unit,sombra: Boolean=true,contenido: @Composable ()->Unit) {
    CristalRosa(Modifier.size(56.dp),28.dp,descripcion,sombra=sombra,accion=onClick) { Box(Modifier.fillMaxSize(),contentAlignment=Alignment.Center) { contenido() } }
}
@Composable fun VolverCampus(volver: ()->Unit) { BotonCristal("Volver",volver,sombra=false) { Icon(IconosCafe.ArrowBack,null,Modifier.size(25.dp),tint=MaterialTheme.colorScheme.onSurface) } }
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
