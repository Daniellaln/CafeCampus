package com.leon.cafecampus.ui.components

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

import com.leon.cafecampus.data.local.relation.PedidoConDetalle
@Composable fun PedidoCard(p: PedidoConDetalle,admin: Boolean=false,abrir: ()->Unit,acciones: @Composable ()->Unit = {}) {
    Tarjeta(Modifier.clickable(onClick=abrir)) {
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {
            Text("Pedido #${p.pedido.id.toString().padStart(4,'0')}",style=MaterialTheme.typography.titleMedium)
            EstadoChip(EstadoPedido.valueOf(p.pedido.estado))
        }
        if(admin) Text(p.cliente.nombre,fontWeight=FontWeight.Medium)
        Text(p.detalles.joinToString(" · ") { "${it.cantidad} × ${it.nombreProducto}" },style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
        HorizontalDivider(color=MaterialTheme.colorScheme.outlineVariant.copy(alpha=.6f))
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween) {
            Row(horizontalArrangement=Arrangement.spacedBy(6.dp),verticalAlignment=Alignment.CenterVertically) { Icon(IconosCafe.AccessTime,null,Modifier.size(16.dp),tint=MaterialTheme.colorScheme.primary);Text("Recojo ${p.pedido.horaRecojo}",style=MaterialTheme.typography.bodySmall) }
            Text(dinero(p.pedido.total),color=MaterialTheme.colorScheme.primary,fontWeight=FontWeight.Bold)
        }
        if(admin && p.pedido.nota.isNotBlank()) Text("Nota: ${p.pedido.nota}",style=MaterialTheme.typography.bodySmall)
        acciones()
    }
}
