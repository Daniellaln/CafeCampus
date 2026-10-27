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

@Composable fun MisPedidosScreen(vm: PedidosClienteViewModel,abrir: (Int)->Unit,repetir: (com.leon.cafecampus.data.local.relation.PedidoConDetalle)->Unit={},aviso: String?=null,ocupado: Boolean=false) {
    val historial by vm.historial.collectAsStateWithLifecycle()
    val pedidos by vm.visibles.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        AppTopBar("Mis pedidos")
        Column(Modifier.padding(horizontal=20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
            EscenaCampus("Tus pausas favoritas.","Cada pedido tiene su historia.","historial")
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) { listOf(false to "Activos",true to "Historial").forEachIndexed { i,(b,t) -> SegmentedButton(selected=historial==b,onClick={vm.historial(b)},shape=SegmentedButtonDefaults.itemShape(i,2,baseShape=RoundedCornerShape(14.dp))) { Text(t) } } }
            ErrorTexto(aviso)
        }
        LazyColumn(contentPadding=PaddingValues(20.dp),verticalArrangement=Arrangement.spacedBy(14.dp)) {
            if(pedidos.isEmpty()) item { Vacio(if(historial) "Aún no tienes pedidos en el historial" else "No tienes pedidos activos") }
            items(pedidos,key={it.pedido.id}) { p -> PedidoCard(p,abrir={abrir(p.pedido.id)}) {
                if(historial) OutlinedButton(onClick={repetir(p)},enabled=!ocupado,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp),shape=RoundedCornerShape(12.dp)) { Icon(IconosCafe.Replay,null,Modifier.size(18.dp));Spacer(Modifier.width(8.dp));Text(if(ocupado) "Preparando tu bolsa…" else "Repetir pedido") }
            } }
        }
    }
}
