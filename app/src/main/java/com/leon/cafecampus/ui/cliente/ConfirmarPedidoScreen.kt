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

@Composable fun ConfirmarPedidoScreen(menu: MenuViewModel,vm: PedidosClienteViewModel,abierto: Boolean,volver: ()->Unit,seguir: (Long)->Unit) {
    val items by menu.carrito.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val enviando by vm.enviando.collectAsStateWithLifecycle()
    val creado by vm.creado.collectAsStateWithLifecycle()
    var hora by rememberSaveable { mutableStateOf("Ahora") }
    var pago by rememberSaveable { mutableStateOf("CAJA") }
    var nota by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(creado) { creado?.let { menu.vaciar(); seguir(it); vm.consumirCreado() } }
    Column {
        AppTopBar("Confirmar pedido",volver)
        Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
            if(items.isEmpty()) Vacio("Tu bolsa está vacía") else Tarjeta {
                items.forEach { item ->
                    Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                        ProductoIcono(item.producto.icono,Modifier.size(48.dp))
                        Column(Modifier.weight(1f)) { Text(item.producto.nombre,style=MaterialTheme.typography.titleSmall); Text("×${item.cantidad}",style=MaterialTheme.typography.bodySmall) }
                        Text(dinero(item.subtotal),style=MaterialTheme.typography.bodySmall)
                        IconButton(onClick={menu.quitar(item.producto.id)},enabled=!enviando) { Icon(Icons.Default.Close,"Quitar ${item.producto.nombre}") }
                    }
                }
            }
            Text("Hora de recojo",fontWeight=FontWeight.Bold)
            Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) { listOf("Ahora","10:30","11:00","11:30").forEach { FilterChip(selected=hora==it,onClick={hora=it},label={Text(it)}) } }
            Text("Forma de pago",fontWeight=FontWeight.Bold)
            Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) { listOf("CAJA" to "Pagar en caja","YAPE" to "Yape / Plin").forEach { (id,t) -> FilterChip(selected=pago==id,onClick={pago=id},label={Text(t)}) } }
            CafeTextField(nota,{nota=it},"Nota para la cocina")
            Tarjeta { Text("Subtotal: ${dinero(menu.total())}"); Text("Servicio: S/ 0.00"); Text("Total: ${dinero(menu.total())}",fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary) }
            if(!abierto) ErrorTexto("Cafetería cerrada")
            ErrorTexto(error)
            CafeButton(if(enviando) "Confirmando…" else "Confirmar pedido",items.isNotEmpty() && abierto && !enviando) { vm.confirmar(items,hora,pago,nota) }
            OutlinedButton(onClick=volver,enabled=!enviando,modifier=Modifier.fillMaxWidth()) { Text("Seguir pidiendo") }
        }
    }
}
