package com.leon.cafecampus.ui.cliente
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.viewmodel.MenuViewModel
import com.leon.cafecampus.ui.components.*
@Composable fun MenuScreen(vm: MenuViewModel, onProducto: (Int)->Unit, nombre: String="Daniella", cantidad: Int=0, abierto: Boolean=true, bolsa: ()->Unit={}) {
    val productos by vm.filtrados.collectAsStateWithLifecycle()
    val categoria by vm.categoria.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().padding(horizontal=16.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
        Row(Modifier.fillMaxWidth().padding(top=16.dp),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically) {
            Column { Text("¡Hola, ${nombre.substringBefore(' ')}!",style=MaterialTheme.typography.headlineSmall); Text("¿Qué se te antoja hoy?",color=MaterialTheme.colorScheme.onSurfaceVariant) }
            BadgedBox(badge={if(cantidad>0) Badge { Text("$cantidad") }}) { IconButton(onClick=bolsa) { Icon(Icons.Default.ShoppingBag,"Mi bolsa") } }
        }
        Categorias(categoria,vm::categoria)
        if(!abierto) Text("Cafetería cerrada",color=MaterialTheme.colorScheme.error)
        Text("Populares hoy",style=MaterialTheme.typography.titleMedium)
        if(productos.isEmpty()) Vacio("No hay productos en esta categoría")
        LazyVerticalGrid(columns=GridCells.Fixed(2),contentPadding=PaddingValues(bottom=16.dp),horizontalArrangement=Arrangement.spacedBy(10.dp),verticalArrangement=Arrangement.spacedBy(10.dp)) {
            items(productos,key={it.id}) { p -> ProductoCard(p) { onProducto(p.id) } }
        }
    }
}
