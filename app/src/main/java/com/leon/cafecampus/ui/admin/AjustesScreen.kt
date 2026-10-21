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

@Composable fun AjustesScreen(vm: SesionViewModel,cliente: Boolean=false) {
    val usuario by vm.usuarioActual.collectAsStateWithLifecycle()
    val oscuro by vm.modoOscuro.collectAsStateWithLifecycle()
    val recibir by vm.recibirPedidos.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    Column {
        AppTopBar(if(cliente) "Mi perfil" else "Ajustes")
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
            Tarjeta { Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(14.dp)) {
                Icon(Icons.Default.AccountCircle,null,Modifier.size(48.dp),tint=MaterialTheme.colorScheme.primary)
                Column { Text(usuario?.nombre ?: "",fontWeight=FontWeight.Bold); Text(usuario?.correo ?: "",style=MaterialTheme.typography.bodySmall); Text("Perfil activo",color=MaterialTheme.colorScheme.primary,style=MaterialTheme.typography.labelSmall) }
            } }
            CafeButton("Cambiar de perfil") { vm.cambiarPerfil() }
            Text(if(cliente) "Modo práctica: ir a vista Administrador" else "Modo práctica: ir a vista Estudiante",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Preferencias",style=MaterialTheme.typography.titleMedium)
            Tarjeta {
                Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text("Modo oscuro",fontWeight=FontWeight.Bold); Text("Cambia el tema de toda la app",style=MaterialTheme.typography.bodySmall) }; Switch(checked=oscuro,onCheckedChange=vm::modoOscuro) }
                if(!cliente) {
                    HorizontalDivider()
                    Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text("Recibir pedidos",fontWeight=FontWeight.Bold); Text("Abrir o cerrar la cafetería",style=MaterialTheme.typography.bodySmall) }; Switch(checked=recibir,onCheckedChange=vm::recibirPedidos) }
                }
            }
            Text("Tus preferencias se guardan en este dispositivo.",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            ErrorTexto(error)
            OutlinedButton(onClick=vm::cerrarSesion,modifier=Modifier.fillMaxWidth()) { Text("Cerrar sesión",color=MaterialTheme.colorScheme.error) }
        }
    }
}
