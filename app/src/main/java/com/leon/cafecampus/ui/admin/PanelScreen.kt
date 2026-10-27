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

@Composable fun PanelScreen(vm: PanelAdminViewModel,pedidos: PedidosAdminViewModel,abrir: (Int)->Unit,verTodos: ()->Unit) {
    val nuevos by vm.nuevos.collectAsStateWithLifecycle()
    val preparando by vm.preparando.collectAsStateWithLifecycle()
    val listos by vm.listos.collectAsStateWithLifecycle()
    val horas by vm.porHora.collectAsStateWithLifecycle()
    val ventas by vm.ventasHoy.collectAsStateWithLifecycle()
    val recientes by vm.recientes.collectAsStateWithLifecycle()
    val error by pedidos.error.collectAsStateWithLifecycle()
    val ocupado by pedidos.ocupado.collectAsStateWithLifecycle()
    LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
        item { EscenaCampus("Buen día, campus.","Panel de control · Hoy","panel") }
        item { Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            listOf(Triple(nuevos,"Nuevos",EstadoPedido.RECIBIDO),Triple(preparando,"Preparando",EstadoPedido.PREPARANDO),Triple(listos,"Listos",EstadoPedido.LISTO)).forEach { (n,t,e) ->
                EstadoContador(n,e,Modifier.weight(1f)) { pedidos.estadoSeleccionado.value=e;verTodos() }
            }
        } }
        item { Surface(color=MaterialTheme.colorScheme.primary,shape=MaterialTheme.shapes.medium) { Row(Modifier.fillMaxWidth().padding(22.dp),verticalAlignment=Alignment.CenterVertically) { Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(8.dp)) { Text("Ventas del día",style=MaterialTheme.typography.bodyMedium); Text(dinero(ventas),style=MaterialTheme.typography.displaySmall);Text("Pedidos de hoy · sin cancelados",style=MaterialTheme.typography.bodySmall) };Icon(Icons.Default.Coffee,null,Modifier.size(52.dp),tint=MaterialTheme.colorScheme.onPrimary.copy(alpha=.7f)) } } }
        item { GraficoPedidos(horas) }
        item { Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) { Text("Nuevos pedidos",style=MaterialTheme.typography.titleMedium); TextButton(onClick={pedidos.estadoSeleccionado.value=EstadoPedido.RECIBIDO;verTodos()}) { Text("Ver todos") } }; ErrorTexto(error) }
        if(recientes.isEmpty()) item { Vacio("No hay nuevos pedidos") }
        items(recientes.take(5),key={it.pedido.id}) { p -> PedidoCard(p,admin=true,abrir={abrir(p.pedido.id)}) { Button(onClick={pedidos.avanzar(p)},enabled=!ocupado) { Text("Preparar") } } }
    }
}
