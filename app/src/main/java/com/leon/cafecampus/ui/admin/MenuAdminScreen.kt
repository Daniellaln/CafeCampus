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

@Composable fun MenuAdminScreen(vm: MenuAdminViewModel,editar: (Int)->Unit,nuevo: ()->Unit) {
    val productos by vm.filtrados.collectAsStateWithLifecycle()
    val categoria by vm.categoria.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val ocupado by vm.ocupado.collectAsStateWithLifecycle()
    var eliminar by remember { mutableStateOf<Producto?>(null) }
    Scaffold(containerColor=MaterialTheme.colorScheme.background,topBar={AppTopBar("Gestión del menú")},floatingActionButton={FloatingActionButton(onClick=nuevo) { Icon(Icons.Default.Add,"Agregar producto") }}) { padding ->
        Column(Modifier.padding(padding).padding(horizontal=16.dp)) {
            Categorias(categoria,vm::categoria)
            ErrorTexto(error)
            LazyColumn(verticalArrangement=Arrangement.spacedBy(12.dp),contentPadding=PaddingValues(bottom=88.dp)) {
                if(productos.isEmpty()) item { Vacio("No hay productos en esta categoría") }
                items(productos,key={it.id}) { p -> Tarjeta {
                    Row(horizontalArrangement=Arrangement.spacedBy(8.dp),verticalAlignment=Alignment.CenterVertically) {
                        ProductoIcono(p.icono,Modifier.size(56.dp))
                        Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) { Text(p.nombre,fontWeight=FontWeight.Bold); Text("${p.categoria} · ${dinero(p.precio)}",style=MaterialTheme.typography.bodySmall); DisponibilidadChip(p.disponible,!ocupado) { vm.alternarDisponibilidad(p) } }
                        IconButton(onClick={editar(p.id)},enabled=!ocupado) { Icon(Icons.Default.Edit,"Editar ${p.nombre}",tint=MaterialTheme.colorScheme.primary) }
                        IconButton(onClick={eliminar=p},enabled=!ocupado) { Icon(Icons.Default.Delete,"Eliminar ${p.nombre}",tint=MaterialTheme.colorScheme.error) }
                    }

                } }
            }
        }
    }
    eliminar?.let { p -> AlertDialog(onDismissRequest={eliminar=null},title={Text("Eliminar producto")},text={Text("¿Eliminar ${p.nombre}? Los pedidos anteriores conservarán sus datos.")},confirmButton={TextButton(onClick={vm.eliminar(p);eliminar=null}) { Text("Eliminar") }},dismissButton={TextButton(onClick={eliminar=null}) { Text("Cancelar") }}) }
}
