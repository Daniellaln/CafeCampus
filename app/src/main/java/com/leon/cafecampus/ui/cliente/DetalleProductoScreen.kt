package com.leon.cafecampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.viewmodel.*
import com.leon.cafecampus.model.*
import com.leon.cafecampus.data.local.entity.*

@Composable fun DetalleProductoScreen(id: Int,vm: MenuViewModel,volver: ()->Unit) {
    val productos by vm.productos.collectAsStateWithLifecycle()
    val p=productos.find { it.id==id }
    var cantidad by rememberSaveable(id) { mutableIntStateOf(1) }
    var nota by rememberSaveable(id) { mutableStateOf("") }
    val error by vm.error.collectAsStateWithLifecycle()
    Column {
        AppTopBar("Detalle del producto",volver)
        if(p==null) Vacio("Producto no disponible") else Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
            ProductoIcono(p.icono,Modifier.fillMaxWidth().height(180.dp))
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)) { Text(p.nombre,Modifier.weight(1f),style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold); Text(dinero(p.precio),color=MaterialTheme.colorScheme.primary,fontWeight=FontWeight.Bold) }
            Row(horizontalArrangement=Arrangement.spacedBy(24.dp),verticalAlignment=Alignment.CenterVertically) { Disponible(p.disponible); Text("10–15 min",color=MaterialTheme.colorScheme.onSurfaceVariant) }
            Text(p.descripcion,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {
                Text("Cantidad",fontWeight=FontWeight.Bold)
                Row(verticalAlignment=Alignment.CenterVertically) { IconButton(onClick={cantidad--},enabled=cantidad>1) { Icon(Icons.Default.Remove,"Disminuir cantidad") }; Text("$cantidad"); IconButton(onClick={cantidad++},enabled=cantidad<99) { Icon(Icons.Default.Add,"Aumentar cantidad") } }
            }
            CafeTextField(nota,{nota=it},"Nota para la cocina")
            Tarjeta { Text("Total",style=MaterialTheme.typography.labelMedium); Text(dinero(p.precio*cantidad),color=MaterialTheme.colorScheme.primary,fontWeight=FontWeight.Bold) }
            ErrorTexto(error)
            CafeButton("Agregar al pedido",p.disponible) { if(vm.agregar(p,cantidad,nota)) volver() }
        }
    }
}
