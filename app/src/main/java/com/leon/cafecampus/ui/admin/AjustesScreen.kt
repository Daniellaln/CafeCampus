package com.leon.cafecampus.ui.admin
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.viewmodel.SesionViewModel
@Composable fun AjustesScreen(vm: SesionViewModel,cliente: Boolean=false) {
    val usuario by vm.usuarioActual.collectAsStateWithLifecycle();val oscuro by vm.modoOscuro.collectAsStateWithLifecycle()
    val recibir by vm.recibirPedidos.collectAsStateWithLifecycle();val error by vm.error.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal=20.dp),verticalArrangement=Arrangement.spacedBy(22.dp)) {
        Text(if(cliente) "Mi perfil" else "Ajustes",Modifier.padding(top=18.dp),style=MaterialTheme.typography.titleLarge)
        EscenaCampus(if(cliente) "Tu espacio." else "Tu cafetería.",if(cliente) "Una pausa que va contigo." else "Preferencias de gestión.","perfil")
        PapelInterior {
            Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(18.dp)) {
                AvatarPerfil(usuario?.nombre ?: "A",cliente && usuario?.correo=="daniella@campus.test",Modifier.size(if(cliente) 112.dp else 88.dp))
                Column(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(5.dp)) {
                    Text(usuario?.nombre ?: "",style=MaterialTheme.typography.titleLarge)
                    Text(usuario?.correo ?: "",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(if(cliente) "Estudiante" else "Administrador",style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.primary)
                }
            }
        }
        Seccion("Preferencias","Pequeños ajustes para tu día a día.")
        PapelInterior {
            Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                Icon(IconosCafe.DarkMode,null,Modifier.size(28.dp),tint=MaterialTheme.colorScheme.primary)
                Column(Modifier.weight(1f)) { Text("Modo oscuro",style=MaterialTheme.typography.titleMedium);Text("Una luz más suave para la noche",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
                Switch(checked=oscuro,onCheckedChange=vm::modoOscuro)
            }
            if(!cliente) { HorizontalDivider();Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) {
                Icon(IconosCafe.Inventory2,null,tint=MaterialTheme.colorScheme.primary)
                Column(Modifier.weight(1f)) { Text("Recibir pedidos",style=MaterialTheme.typography.titleMedium);Text(if(recibir) "La cafetería está abierta" else "La cafetería está cerrada",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) };Switch(checked=recibir,onCheckedChange=vm::recibirPedidos)
            } }
        }
        Seccion("Explora CafeCampus","En este laboratorio puedes probar los dos perfiles.")
        CafeButton("Cambiar de perfil",onClick=vm::cambiarPerfil)
        Text(if(cliente) "Modo práctica: ir a vista Administrador" else "Modo práctica: ir a vista Estudiante",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
        ErrorTexto(error)
        TextButton(onClick=vm::cerrarSesion,modifier=Modifier.fillMaxWidth().heightIn(min=48.dp)) { Icon(IconosCafe.Logout,null,tint=MaterialTheme.colorScheme.error);Spacer(Modifier.width(10.dp));Text("Cerrar sesión",color=MaterialTheme.colorScheme.error) }
        Spacer(Modifier.height(10.dp))
    }
}
