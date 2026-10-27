package com.leon.cafecampus.ui.cliente

import androidx.compose.foundation.*
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

@Composable fun ConfirmarPedidoScreen(menu: MenuViewModel,vm: PedidosClienteViewModel,abierto: Boolean,volver: ()->Unit,seguir: (Long)->Unit) {
    val items by menu.carrito.collectAsStateWithLifecycle()
    val avisoBolsa by menu.error.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val enviando by vm.enviando.collectAsStateWithLifecycle()
    val creado by vm.creado.collectAsStateWithLifecycle()
    var reloj by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val horarios=remember(reloj) { opcionesRecojo(reloj) }
    var avisoHorario by remember { mutableStateOf<String?>(null) }
    var hora by rememberSaveable { mutableStateOf("Ahora") }
    var pago by rememberSaveable { mutableStateOf("CAJA") }
    var nota by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(Unit) { while(true) { kotlinx.coroutines.delay(30000);reloj=System.currentTimeMillis() } }
    LaunchedEffect(horarios) { if(hora !in horarios) { hora="Ahora";avisoHorario="Actualizamos los horarios disponibles. Elige cuándo recoger." } }
    LaunchedEffect(creado) { creado?.let { menu.vaciar();seguir(it);vm.consumirCreado() } }
    Scaffold(containerColor=androidx.compose.ui.graphics.Color.Transparent,topBar={AppTopBar("Confirmar pedido",volver)},bottomBar={ Surface(color=MaterialTheme.colorScheme.surface,shape=RoundedCornerShape(topStart=28.dp,topEnd=28.dp),shadowElevation=8.dp) {
        Column(Modifier.padding(horizontal=20.dp,vertical=12.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) { Text("Total a pagar",style=MaterialTheme.typography.titleMedium);Text(dinero(menu.total()),style=MaterialTheme.typography.titleLarge,color=MaterialTheme.colorScheme.primary) }
            CafeButton(if(enviando) "Confirmando…" else "Confirmar pedido",items.isNotEmpty() && abierto && !enviando) { if(hora in opcionesRecojo()) vm.confirmar(items,hora,pago,nota) else { reloj=System.currentTimeMillis();hora="Ahora";avisoHorario="La hora seleccionada ya pasó. Elige otra hora." } }
            TextButton(onClick=volver,enabled=!enviando,modifier=Modifier.fillMaxWidth()) { Text("Seguir pidiendo") }
        }
    }}) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).imePadding().verticalScroll(rememberScrollState()).padding(horizontal=20.dp),verticalArrangement=Arrangement.spacedBy(22.dp)) {
            EscenaCampus("Tu bolsa","Lo que elegiste para esta pausa.","bolsa")
            if(items.isEmpty()) Vacio("Tu bolsa está vacía") else PapelInterior {
                items.forEachIndexed { index,item ->
                    if(index>0) HorizontalDivider(color=MaterialTheme.colorScheme.outlineVariant)
                    Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(10.dp)) {
                        FotoProductoInterior(item.producto.icono,Modifier.size(64.dp))
                        Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(3.dp)) { Text(item.producto.nombre,style=MaterialTheme.typography.titleSmall);Text("${item.cantidad} ${if(item.cantidad==1) "unidad" else "unidades"} · ${dinero(item.subtotal)}",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
                        IconButton(onClick={menu.quitar(item.producto.id)},enabled=!enviando) { Icon(IconosCafe.Close,"Quitar ${item.producto.nombre}",tint=MaterialTheme.colorScheme.onSurfaceVariant) }
                    }
                }
            }
            PapelInterior {
                Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) { Icon(IconosCafe.AccessTime,null,tint=MaterialTheme.colorScheme.primary);Column { Seccion("Hora de recojo","Elige cuándo pasar por tu pedido.") } }
                Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) { horarios.forEach { OpcionCristal(it,hora==it,onClick={hora=it;avisoHorario=null}) } }
            }
            PapelInterior {
                Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) { Icon(IconosCafe.Payments,null,tint=MaterialTheme.colorScheme.primary);Seccion("Forma de pago") }
                Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)) { listOf("CAJA" to "Pagar en caja","YAPE" to "Yape / Plin").forEach { (id,t) -> OpcionCristal(t,pago==id,Modifier.weight(1f),onClick={pago=id}) } }
            }
            CafeTextField(nota,{nota=it},"Nota para la cocina",icono=IconosCafe.ReceiptLong)
            Tarjeta { Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) { Text("Subtotal",color=MaterialTheme.colorScheme.onSurfaceVariant);Text(dinero(menu.total())) };Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) { Text("Servicio",color=MaterialTheme.colorScheme.onSurfaceVariant);Text("S/ 0.00") } }
            if(!abierto) ErrorTexto("Cafetería cerrada")
            ErrorTexto(avisoHorario);ErrorTexto(avisoBolsa);ErrorTexto(error);Spacer(Modifier.height(8.dp))
        }
    }
}
