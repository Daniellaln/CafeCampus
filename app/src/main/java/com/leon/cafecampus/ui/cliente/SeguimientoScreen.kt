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

@Composable fun SeguimientoScreen(id: Int,vm: PedidosClienteViewModel,volver: ()->Unit) {
    val flow=remember(id,vm) { vm.pedido(id) }
    val pedido by flow.collectAsStateWithLifecycle()
    Column {
        AppTopBar("Seguimiento",volver)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(24.dp)) {
            val p=pedido
            if(p==null) Vacio("Cargando pedido…") else {
                Surface(color=MaterialTheme.colorScheme.primary,shape=RoundedCornerShape(20.dp),contentColor=MaterialTheme.colorScheme.onPrimary) {
                    Column(Modifier.fillMaxWidth().padding(20.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
                        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) { Text("Pedido #${p.pedido.id.toString().padStart(4,'0')}",style=MaterialTheme.typography.headlineSmall);Icon(IconosCafe.ShoppingBag,null,Modifier.size(28.dp)) }
                        Text(if(p.pedido.horaRecojo=="Ahora") "Recojo lo antes posible" else "Recojo a las ${p.pedido.horaRecojo}",style=MaterialTheme.typography.bodyMedium)
                        Text("Tiempo estimado: 10–15 min",style=MaterialTheme.typography.bodySmall)
                    }
                }
                EscenaCampus("Tu pedido, paso a paso","Verás aquí cada cambio de la cafetería.","seguimiento")
                StepperEstado(EstadoPedido.valueOf(p.pedido.estado))
                Tarjeta { p.detalles.forEach { d -> Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(10.dp)) { Text("${d.cantidad} × ${d.nombreProducto}",Modifier.weight(1f),style=MaterialTheme.typography.bodyMedium);Text(dinero(d.cantidad*d.precioUnitario),style=MaterialTheme.typography.bodyMedium) } };HorizontalDivider();Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) { Text("Total",style=MaterialTheme.typography.titleMedium);Text(dinero(p.pedido.total),color=MaterialTheme.colorScheme.primary,style=MaterialTheme.typography.titleLarge) } }
            }
        }
    }
}
