package com.leon.cafecampus.ui.cliente

import androidx.compose.foundation.*
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.ui.theme.*
import com.leon.cafecampus.viewmodel.*
import com.leon.cafecampus.model.*

@Composable fun DetalleProductoScreen(id: Int,vm: MenuViewModel,volver: ()->Unit) {
    val productos by vm.productos.collectAsStateWithLifecycle()
    val p=productos.find { it.id==id }
    var cantidad by rememberSaveable(id) { mutableIntStateOf(1) }
    var nota by rememberSaveable(id) { mutableStateOf("") }
    val error by vm.error.collectAsStateWithLifecycle()
    Scaffold(containerColor=MaterialTheme.colorScheme.background,topBar={AppTopBar("Detalle del producto",volver)},bottomBar={if(p!=null) Surface(color=MaterialTheme.colorScheme.surface,shadowElevation=6.dp) {
        Column(Modifier.fillMaxWidth().padding(horizontal=20.dp,vertical=14.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) { Text("Total",style=MaterialTheme.typography.bodyMedium,color=MaterialTheme.colorScheme.onSurfaceVariant);Text(dinero(p.precio*cantidad),style=MaterialTheme.typography.titleLarge,color=MaterialTheme.colorScheme.primary) }
            CafeButton("Agregar al pedido",p.disponible) { if(vm.agregar(p,cantidad,nota)) volver() }
        }
    }}) { padding ->
        if(p==null) Vacio("Producto no disponible") else Column(Modifier.fillMaxSize().padding(padding).imePadding().verticalScroll(rememberScrollState()).padding(horizontal=20.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
            Box(Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(22.dp)).background(MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.7f)),contentAlignment=Alignment.Center) {
                androidx.compose.foundation.Image(androidx.compose.ui.res.painterResource(com.leon.cafecampus.R.drawable.estudio_cobalto),null,Modifier.matchParentSize(),contentScale=ContentScale.Crop)
                Citricos(Modifier.fillMaxSize());FotoLibre(p.icono,Modifier.fillMaxSize().padding(8.dp))
            }
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(12.dp),verticalAlignment=Alignment.Top) { Text(p.nombre,Modifier.weight(1f),style=MaterialTheme.typography.headlineMedium);Text(dinero(p.precio),style=MaterialTheme.typography.titleLarge,color=MaterialTheme.colorScheme.primary) }
            Row(horizontalArrangement=Arrangement.spacedBy(18.dp),verticalAlignment=Alignment.CenterVertically) { Disponible(p.disponible);Icon(Icons.Default.Schedule,null,tint=MaterialTheme.colorScheme.onSurfaceVariant,modifier=Modifier.size(17.dp));Text("10–15 min",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
            Text(p.descripcion,style=MaterialTheme.typography.bodyLarge,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {
                Text("Cantidad",style=MaterialTheme.typography.titleMedium)
                Surface(color=MaterialTheme.colorScheme.surface,shape=RoundedCornerShape(14.dp)) { Row(verticalAlignment=Alignment.CenterVertically) {
                    IconButton(onClick={cantidad--},enabled=cantidad>1) { Icon(Icons.Default.Remove,"Disminuir cantidad") };AnimatedContent(targetState=cantidad,label="Cantidad elegida") { valor -> Text("$valor",style=MaterialTheme.typography.titleMedium,modifier=Modifier.padding(horizontal=12.dp)) };IconButton(onClick={cantidad++},enabled=cantidad<99) { Icon(Icons.Default.Add,"Aumentar cantidad",tint=MaterialTheme.colorScheme.primary) }
                } }
            }
            CafeTextField(nota,{nota=it},"Nota para la cocina")
            ErrorTexto(error);Spacer(Modifier.height(8.dp))
        }
    }
}
