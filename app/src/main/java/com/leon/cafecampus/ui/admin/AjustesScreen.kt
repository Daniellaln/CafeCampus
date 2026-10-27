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
import androidx.compose.ui.graphics.luminance
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
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(22.dp)) {
            EscenaCampus(if(cliente) "Tu espacio." else "A tu manera.","Una pausa que va contigo.",if(cliente) "perfil" else "panel")
            Tarjeta {
                Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(16.dp)) {
                    Surface(modifier=Modifier.sombraCampus(32.dp,MaterialTheme.colorScheme.background.luminance()<.2f),shape=CircleShape,color=MaterialTheme.colorScheme.secondaryContainer) { Box(Modifier.size(64.dp),contentAlignment=Alignment.Center) { Text(usuario?.nombre?.take(1) ?: "C",style=MaterialTheme.typography.headlineMedium,color=MaterialTheme.colorScheme.onSecondaryContainer) } }
                    Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(4.dp)) { if(cliente) FlorPorcelana(Modifier.size(32.dp));Text(usuario?.nombre ?: "",style=MaterialTheme.typography.titleLarge);Text(usuario?.correo ?: "",style=MaterialTheme.typography.bodySmall);Text(if(cliente) "Estudiante" else "Administrador",style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.primary) }
                }
            }
            Seccion("Preferencias","Pequeños ajustes para tu día a día.")
            Tarjeta {
                Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                    Icon(IconosCafe.DarkMode,null,tint=MaterialTheme.colorScheme.primary)
                    Column(Modifier.weight(1f)) { Text("Modo oscuro",style=MaterialTheme.typography.titleMedium);Text("Una luz más suave para la noche",style=MaterialTheme.typography.bodySmall) }
                    Switch(checked=oscuro,onCheckedChange=vm::modoOscuro)
                }
                if(!cliente) { HorizontalDivider();Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                    Icon(IconosCafe.Inventory2,null,tint=MaterialTheme.colorScheme.primary)
                    Column(Modifier.weight(1f)) { Text("Recibir pedidos",style=MaterialTheme.typography.titleMedium);Text(if(recibir) "La cafetería está abierta" else "La cafetería está cerrada",style=MaterialTheme.typography.bodySmall) };Switch(checked=recibir,onCheckedChange=vm::recibirPedidos)
                } }
            }
            Seccion("Explora CafeCampus","En este laboratorio puedes probar los dos perfiles.")
            OutlinedButton(onClick=vm::cambiarPerfil,modifier=Modifier.fillMaxWidth().heightIn(min=54.dp),shape=RoundedCornerShape(16.dp)) { Icon(IconosCafe.SwapHoriz,null);Spacer(Modifier.width(10.dp));Text("Cambiar de perfil") }
            Text(if(cliente) "Modo práctica: ir a vista Administrador" else "Modo práctica: ir a vista Estudiante",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            ErrorTexto(error)
            TextButton(onClick=vm::cerrarSesion,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)) { Icon(IconosCafe.Logout,null,tint=MaterialTheme.colorScheme.error);Spacer(Modifier.width(10.dp));Text("Cerrar sesión",color=MaterialTheme.colorScheme.error) }
            Text("Tus preferencias se guardan en este dispositivo.",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
