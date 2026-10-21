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

@Composable fun MisPedidosScreen(vm: PedidosClienteViewModel,abrir: (Int)->Unit) {
    val historial by vm.historial.collectAsStateWithLifecycle()
    val pedidos by vm.visibles.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        AppTopBar("Mis pedidos")
        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth().padding(horizontal=16.dp)) { listOf(false to "Activos",true to "Historial").forEachIndexed { i,(b,t) -> SegmentedButton(selected=historial==b,onClick={vm.historial(b)},shape=SegmentedButtonDefaults.itemShape(i,2)) { Text(t) } } }
        LazyColumn(contentPadding=PaddingValues(16.dp),verticalArrangement=Arrangement.spacedBy(12.dp)) {
            if(pedidos.isEmpty()) item { Vacio(if(historial) "Aún no tienes pedidos en el historial" else "No tienes pedidos activos") }
            items(pedidos,key={it.pedido.id}) { p -> PedidoCard(p,abrir={abrir(p.pedido.id)}) }
        }
    }
}
