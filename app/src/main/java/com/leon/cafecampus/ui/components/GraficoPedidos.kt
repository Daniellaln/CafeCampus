package com.leon.cafecampus.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.animation.core.*
import androidx.compose.ui.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import com.leon.cafecampus.model.PedidosPorHora
@Composable fun GraficoPedidos(datos: List<PedidosPorHora>) {
    val maximo=(datos.maxOfOrNull { it.cantidad } ?: 1).coerceAtLeast(1)
    Tarjeta {
        Text("Pedidos por hora",style=MaterialTheme.typography.titleMedium)
        Text("Hoy · sin cancelados",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
        if(datos.all { it.cantidad==0 }) Text("Aún no hay pedidos de hoy",style=MaterialTheme.typography.bodySmall)
        Row(Modifier.fillMaxWidth().height(130.dp).semantics { contentDescription=datos.joinToString { "${it.hora} horas: ${it.cantidad} pedidos" } },horizontalArrangement=Arrangement.spacedBy(4.dp),verticalAlignment=Alignment.Bottom) {
            datos.forEach { d ->
            val alto by animateDpAsState((d.cantidad.toFloat()/maximo*78).coerceAtLeast(3f).dp,tween(280),label="Pedidos por hora")
            Column(Modifier.weight(1f),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(4.dp)) {
                if(d.cantidad>0) Text("${d.cantidad}",style=MaterialTheme.typography.labelSmall)
                Box(Modifier.fillMaxWidth().height(alto).background(if(d.cantidad>0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,RoundedCornerShape(topStart=5.dp,topEnd=5.dp)))
                Text("${d.hora}",style=MaterialTheme.typography.labelSmall)
            } }
        }
    }
}
