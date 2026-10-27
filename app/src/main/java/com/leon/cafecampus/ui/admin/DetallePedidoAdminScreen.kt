package com.leon.cafecampus.ui.admin

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

@Composable fun DetallePedidoAdminScreen(id: Int,vm: PedidosAdminViewModel,volver: ()->Unit) {
    val flow=remember(id,vm) { vm.pedido(id) }
    val pedido by flow.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val ocupado by vm.ocupado.collectAsStateWithLifecycle()
    Column {
        AppTopBar("Detalle del pedido",volver)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
            val p=pedido
            if(p==null) Vacio("Cargando pedido…") else {
                EscenaCampus("Listo para preparar.","Cada detalle cuenta.","cocina")
                Tarjeta { Text("Pedido #${p.pedido.id.toString().padStart(4,'0')}",style=MaterialTheme.typography.headlineMedium); EstadoChip(EstadoPedido.valueOf(p.pedido.estado)); Text("Recojo ${p.pedido.horaRecojo} · ${p.pedido.pago}") }
                Tarjeta {
                    Text("Cliente",style=MaterialTheme.typography.labelMedium)
                    Text("${p.cliente.nombre} · Estudiante",fontWeight=FontWeight.Bold)
                    HorizontalDivider()
                    p.detalles.forEach { d -> Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)) { Text(d.nombreProducto,Modifier.weight(1f)); Text("×${d.cantidad}"); Text(dinero(d.cantidad*d.precioUnitario)) } }
                    Text("Total ${dinero(p.pedido.total)}",fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.primary)
                }
                if(p.pedido.nota.isNotBlank()) Surface(color=MaterialTheme.colorScheme.tertiaryContainer,shape=MaterialTheme.shapes.medium) { Column(Modifier.fillMaxWidth().padding(20.dp)) { Text("Nota del cliente",fontWeight=FontWeight.Bold); Text(p.pedido.nota) } }
                Seccion("El siguiente paso.","Actualiza el estado para que el estudiante lo vea al instante.")
                Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) { EstadoPedido.entries.filterNot { it==EstadoPedido.CANCELADO }.forEach { e -> FilterChip(selected=p.pedido.estado==e.name,onClick={vm.cambiar(p,e)},enabled=!ocupado && vm.puedeCambiar(p,e),label={Text(e.etiqueta)}) } }
                ErrorTexto(error)
                vm.siguiente(p)?.let { siguiente -> CafeButton("Pasar a ${siguiente.etiqueta}",!ocupado) { vm.avanzar(p) } }
                if(vm.puedeCancelar(p)) OutlinedButton(onClick={vm.cancelar(p)},enabled=!ocupado,modifier=Modifier.fillMaxWidth()) { Text("Cancelar pedido",color=MaterialTheme.colorScheme.error) }
            }
        }
    }
}
