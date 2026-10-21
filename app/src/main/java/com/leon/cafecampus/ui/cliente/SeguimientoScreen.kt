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

@Composable fun SeguimientoScreen(id: Int,vm: PedidosClienteViewModel,volver: ()->Unit) {
    val flow=remember(id,vm) { vm.pedido(id) }
    val pedido by flow.collectAsStateWithLifecycle()
    Column {
        AppTopBar("Seguimiento",volver)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
            val p=pedido
            if(p==null) Vacio("Cargando pedido…") else {
                Surface(color=MaterialTheme.colorScheme.primary,shape=MaterialTheme.shapes.medium) {
                    Column(Modifier.fillMaxWidth().padding(18.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
                        Text("Pedido #${p.pedido.id.toString().padStart(4,'0')}",fontWeight=FontWeight.Bold,style=MaterialTheme.typography.titleLarge)
                        Text(if(p.pedido.horaRecojo=="Ahora") "Recojo lo antes posible" else "Recojo a las ${p.pedido.horaRecojo}")
                        Text("Tiempo estimado: 10–15 min",style=MaterialTheme.typography.bodySmall)
                    }
                }
                StepperEstado(EstadoPedido.valueOf(p.pedido.estado))
                Tarjeta { Text(p.detalles.joinToString(" · ") { "${it.cantidad} × ${it.nombreProducto}" }); Text("Total ${dinero(p.pedido.total)}",color=MaterialTheme.colorScheme.primary,fontWeight=FontWeight.Bold) }
            }
        }
    }
}
