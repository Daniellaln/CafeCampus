package com.leon.cafecampus.ui.admin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
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

@Composable fun MenuAdminScreen(vm: MenuAdminViewModel,editar: (Int)->Unit,nuevo: ()->Unit) {
    val productos by vm.filtrados.collectAsStateWithLifecycle()
    val categoria by vm.categoria.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val ocupado by vm.ocupado.collectAsStateWithLifecycle()
    var eliminar by remember { mutableStateOf<Producto?>(null) }
    Scaffold(containerColor=androidx.compose.ui.graphics.Color.Transparent,topBar={AppTopBar("Gestión del menú")},floatingActionButton={ExtendedFloatingActionButton(onClick=nuevo,icon={Icon(IconosCafe.Add,"Agregar producto")},text={Text("Nuevo producto")},containerColor=MaterialTheme.colorScheme.primary,contentColor=MaterialTheme.colorScheme.onPrimary)}) { padding ->
        LazyColumn(Modifier.padding(padding),contentPadding=PaddingValues(start=20.dp,end=20.dp,bottom=96.dp),verticalArrangement=Arrangement.spacedBy(14.dp)) {
            item { EscenaCampus("Catálogo","Disponibilidad y precios.","catalogo") }
            item { Categorias(categoria,vm::categoria);ErrorTexto(error) }
            if(productos.isEmpty()) item { Vacio("No hay productos en esta categoría") }
            items(productos,key={it.id}) { p -> Tarjeta {
                Row(horizontalArrangement=Arrangement.spacedBy(14.dp),verticalAlignment=Alignment.CenterVertically) {
                    ProductoIcono(p.icono,Modifier.size(90.dp))
                    Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(5.dp)) { Text(p.nombre,style=MaterialTheme.typography.titleMedium);Text(p.categoria,style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant);Text(dinero(p.precio),style=MaterialTheme.typography.titleMedium,color=MaterialTheme.colorScheme.primary) }
                }
                Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) {
                    DisponibilidadChip(p.disponible,!ocupado) { vm.alternarDisponibilidad(p) }
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick={editar(p.id)},enabled=!ocupado) { Icon(IconosCafe.Edit,"Editar ${p.nombre}",tint=MaterialTheme.colorScheme.primary) }
                    IconButton(onClick={eliminar=p},enabled=!ocupado) { Icon(IconosCafe.Delete,"Eliminar ${p.nombre}",tint=MaterialTheme.colorScheme.error) }
                }
            } }
        }
    }
    eliminar?.let { p -> AlertDialog(onDismissRequest={eliminar=null},title={Text("Eliminar producto")},text={Text("¿Eliminar ${p.nombre}? Los pedidos anteriores conservarán sus datos.")},confirmButton={TextButton(onClick={vm.eliminar(p);eliminar=null}) { Text("Eliminar") }},dismissButton={TextButton(onClick={eliminar=null}) { Text("Cancelar") }}) }
}
