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

@Composable fun PedidosAdminScreen(vm: PedidosAdminViewModel,abrir: (Int)->Unit) {
    val estado by vm.estadoSeleccionado.collectAsStateWithLifecycle()
    val pedidos by vm.pedidos.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val ocupado by vm.ocupado.collectAsStateWithLifecycle()
    Column {
        AppTopBar("Pedidos")
        EscenaCampus("Todo en su momento.","Organiza cada pedido, desde recibido hasta entregado.","cocina",Modifier.padding(horizontal=20.dp,vertical=12.dp))
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal=16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            EstadoPedido.entries.forEach { e -> FilterChip(selected=estado==e,onClick={vm.estadoSeleccionado.value=e},label={Text(if(e==EstadoPedido.RECIBIDO) "Nuevos" else e.etiqueta)}) }
        }
        ErrorTexto(error)
        LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
            if(pedidos.isEmpty()) item { Vacio("No hay pedidos en este estado") }
            items(pedidos,key={it.pedido.id}) { p -> PedidoCard(p,admin=true,abrir={abrir(p.pedido.id)}) {
                Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                    vm.siguiente(p)?.let { siguiente -> Button(onClick={vm.avanzar(p)},enabled=!ocupado,modifier=Modifier.weight(1f)) { Text("Pasar a ${siguiente.etiqueta}") } }
                    if(vm.puedeCancelar(p)) TextButton(onClick={vm.cancelar(p)},enabled=!ocupado) { Text("Cancelar",color=MaterialTheme.colorScheme.error) }
                }
            } }
        }
    }
}
