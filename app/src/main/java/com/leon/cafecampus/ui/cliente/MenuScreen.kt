package com.leon.cafecampus.ui.cliente
import androidx.compose.foundation.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.*
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.animation.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.ui.theme.*
import com.leon.cafecampus.viewmodel.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MenuScreen(vm: MenuViewModel,onProducto: (Int)->Unit,nombre: String="Daniella",cantidad: Int=0,abierto: Boolean=true,bolsa: ()->Unit={},favoritos: Set<Int> = emptySet(),alternarFavorito: (Int)->Unit={}) {
    val estadoMenu by vm.estadoMenu.collectAsStateWithLifecycle()
    val productos=estadoMenu.productos
    val categoria by vm.categoria.collectAsStateWithLifecycle()
    val busqueda by vm.busqueda.collectAsStateWithLifecycle()
    var buscar by rememberSaveable { mutableStateOf(false) }
    var categorias by rememberSaveable { mutableStateOf(false) }
    Column(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth().padding(horizontal=20.dp).padding(top=6.dp,bottom=14.dp),verticalArrangement=Arrangement.spacedBy(3.dp)) {
            BoxWithConstraints(Modifier.fillMaxWidth().height(72.dp)) {
                val animar=movimientoLiquido()
                val apertura by animateFloatAsState(if(buscar) 1f else 0f,if(animar) spring(dampingRatio=.72f,stiffness=350f) else snap(),label="Lupa a buscador")
                val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
                Row(Modifier.align(Alignment.CenterStart).graphicsLayer { alpha=(1f-apertura*1.5f).coerceIn(0f,1f) },verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(6.dp)) {
                    FlorPorcelana(Modifier.size(30.dp))
                    Text(androidx.compose.ui.text.buildAnnotatedString { append("Cafe");pushStyle(androidx.compose.ui.text.SpanStyle(color=MaterialTheme.colorScheme.primary));append("Campus");pop() },style=MaterialTheme.typography.titleLarge)
                }
                Box(Modifier.align(Alignment.CenterEnd).offset(x=(-68).dp).width(56.dp+(maxWidth-124.dp)*apertura.coerceIn(0f,1f)).height(56.dp).sombraCampus(28.dp,oscuro)) {
                    Box(Modifier.matchParentSize().clip(CircleShape).background(if(oscuro) Color.White.copy(alpha=.04f) else Color.White.copy(alpha=.025f)).reflejosRosa(28.dp,apertura)) {
                        MaterialDeFondo(Modifier.matchParentSize(),apertura)
                        Row(Modifier.fillMaxSize(),verticalAlignment=Alignment.CenterVertically) {
                            IconButton(onClick={buscar=!buscar;if(!buscar) vm.buscar("")},modifier=Modifier.size(56.dp)) { Icon(if(buscar) IconosCafe.Close else IconosCafe.Search,if(buscar) "Cerrar búsqueda" else "Buscar productos",tint=MaterialTheme.colorScheme.primary) }
                            if(buscar && apertura>.65f) BasicTextField(value=busqueda,onValueChange=vm::buscar,singleLine=true,textStyle=MaterialTheme.typography.bodyMedium.copy(color=MaterialTheme.colorScheme.onSurface),cursorBrush=androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary),modifier=Modifier.weight(1f).semantics { contentDescription="Buscar en el menú" },decorationBox={ campo -> Box { if(busqueda.isEmpty()) Text("Buscar en el menú",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant);campo() } })
                            if(buscar && apertura>.65f) IconButton(onClick={vm.buscar("")},modifier=Modifier.size(48.dp)) { Icon(IconosCafe.Close,"Limpiar búsqueda",Modifier.size(19.dp),tint=MaterialTheme.colorScheme.primary) }
                        }
                    }
                }
                Box(Modifier.align(Alignment.CenterEnd)) {
                    BadgedBox(badge={if(cantidad>0) Badge(containerColor=MaterialTheme.colorScheme.secondaryContainer,contentColor=MaterialTheme.colorScheme.onSecondaryContainer) { AnimatedContent(targetState=cantidad,label="Contador de la bolsa") { Text("$it") } }}) {
                        BotonCristal("Mi bolsa",bolsa) { Icon(IconosCafe.ShoppingBag,null,tint=MaterialTheme.colorScheme.primary) }
                    }
                }
            }
            Text("¡Hola, ${nombre.substringBefore(' ')}!",style=MaterialTheme.typography.headlineLarge,maxLines=1)
            Text("Una pausa que va contigo.",style=MaterialTheme.typography.bodyLarge,color=MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(Modifier.fillMaxWidth().padding(start=20.dp,end=20.dp,bottom=10.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(14.dp)) {
            Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(3.dp)) { Text(if(busqueda.isBlank()) "La carta de hoy" else "Resultados",style=MaterialTheme.typography.headlineSmall);Text(if(estadoMenu.cargando) "Cargando…" else "${productos.size} opciones",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
            CristalRosa(Modifier.height(48.dp).widthIn(min=112.dp),24.dp,"Elegir categoría",accion={categorias=true}) {
                Row(Modifier.height(48.dp).padding(horizontal=16.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)) { Icon(IconosCafe.Tune,null,Modifier.size(20.dp),tint=MaterialTheme.colorScheme.primary);Text(categoria,style=MaterialTheme.typography.labelLarge,color=MaterialTheme.colorScheme.primary);Icon(IconosCafe.ExpandMore,null,Modifier.size(20.dp),tint=MaterialTheme.colorScheme.primary) }
            }
        }
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(start=20.dp,end=20.dp,bottom=10.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            listOf(Triple("Todo",IconosCafe.Coffee,"Todo"),Triple("Bebidas",IconosCafe.LocalDrink,"Bebidas"),Triple("Snacks",IconosCafe.BakeryDining,"Snacks"),Triple("Favoritos",IconosCafe.FavoriteBorder,"Favoritos")).forEach { (texto,icono,valor) ->
                CristalRosa(Modifier.height(44.dp),22.dp,"Mostrar $texto",seleccionado=categoria==valor,accion={vm.categoria(valor)}) {
                    Row(Modifier.height(44.dp).padding(horizontal=15.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(7.dp)) { val tinta=MaterialTheme.colorScheme.primary;Icon(icono,null,Modifier.size(21.dp),tint=tinta);Text(texto,style=MaterialTheme.typography.labelMedium,color=tinta) }
                }
            }
        }
        if(!abierto) Box(Modifier.padding(horizontal=20.dp,vertical=6.dp)) { ErrorTexto("Cafetería cerrada") }
        LazyVerticalGrid(columns=GridCells.Fixed(2),modifier=Modifier.weight(1f),contentPadding=PaddingValues(start=20.dp,end=20.dp,top=8.dp,bottom=128.dp),horizontalArrangement=Arrangement.spacedBy(10.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            if(estadoMenu.cargando) item(span={GridItemSpan(maxLineSpan)}) { Vacio("Cargando menú…") }
            else if(productos.isEmpty()) item(span={GridItemSpan(maxLineSpan)}) { Vacio(if(categoria=="Favoritos") "Guarda tus favoritos tocando el corazón de un producto." else "No encontramos productos. Prueba otra búsqueda.") }
            items(productos,key={it.id}) { p -> Box(Modifier.animateItem()) { ProductoCard(p,click={onProducto(p.id)},favorito=p.id in favoritos,alternarFavorito={alternarFavorito(p.id)}) } }
        }
    }
    if(categorias) ModalBottomSheet(sheetState=rememberModalBottomSheetState(skipPartiallyExpanded=true),onDismissRequest={categorias=false},containerColor=MaterialTheme.colorScheme.background,shape=RoundedCornerShape(topStart=30.dp,topEnd=30.dp)) {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(start=24.dp,end=24.dp,bottom=28.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text("Elige tu pausa",style=MaterialTheme.typography.headlineSmall);Text("Filtra la carta a tu gusto.",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) };FlorPorcelana(Modifier.size(58.dp)) }
            listOf("Todo" to "cafe","Desayunos" to "pan","Almuerzos" to "plato","Bebidas" to "jugo","Snacks" to "dulce").forEach { (c,foto) ->
                Row(Modifier.fillMaxWidth().background(if(categoria==c) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,RoundedCornerShape(16.dp)).semantics { contentDescription="Filtrar por $c" }.clickable { vm.categoria(c);categorias=false }.padding(10.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(14.dp)) {
                    FotoLibre(foto,Modifier.size(54.dp));Text(c,Modifier.weight(1f),style=MaterialTheme.typography.titleMedium);if(categoria==c) Icon(IconosCafe.CheckCircle,null,tint=MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
