package com.leon.cafecampus.ui.admin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.viewmodel.ReporteViewModel
import com.leon.cafecampus.ui.components.*
@Composable fun ReporteScreen(vm: ReporteViewModel,volver: ()->Unit) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        AppTopBar("Reporte del día",volver)
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(22.dp)) {
            EscenaCampus("Así va tu cafetería.","Hoy · "+java.text.SimpleDateFormat("d MMM",java.util.Locale.forLanguageTag("es-PE")).format(java.util.Date(estado.inicio)),"panel")
            if(estado.cargando) Vacio("Cargando reporte…") else if(estado.error!=null) ErrorTexto(estado.error) else if(estado.cantidad==0) Vacio("Todavía no hay pedidos hoy") else {
                Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) { Column { Text("Total del día",style=MaterialTheme.typography.bodyMedium);Text(dinero(estado.total),style=MaterialTheme.typography.headlineLarge,color=MaterialTheme.colorScheme.primary) };Text("${estado.cantidad} pedidos",style=MaterialTheme.typography.titleMedium) }
                estado.productos.firstOrNull()?.let { p ->
                    Seccion("Producto más vendido")
                    Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(16.dp)) {
                        if(p.icono!=null) ProductoIcono(p.icono,Modifier.size(100.dp)) else Icon(IconosCafe.Inventory2,null,Modifier.size(48.dp))
                        Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(6.dp)) { Text(p.nombre,style=MaterialTheme.typography.titleLarge);Text("${p.unidades} unidades",style=MaterialTheme.typography.bodyMedium);Text(dinero(p.total),color=MaterialTheme.colorScheme.primary) }
                    }
                }
                Seccion("Por forma de pago")
                listOf("CAJA" to "Caja","YAPE" to "Yape / Plin").forEach { (id,nombre) -> val p=estado.pagos.find { it.pago==id };Tarjeta { Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) { Icon(IconosCafe.Payments,null,tint=MaterialTheme.colorScheme.primary);Column(Modifier.weight(1f)) { Text(nombre,style=MaterialTheme.typography.titleMedium);Text("${p?.cantidad ?: 0} pedidos",style=MaterialTheme.typography.bodySmall) };Text(dinero(p?.total ?: 0.0),style=MaterialTheme.typography.titleLarge) } } }
                Text("Datos del día local · sin pedidos cancelados",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
