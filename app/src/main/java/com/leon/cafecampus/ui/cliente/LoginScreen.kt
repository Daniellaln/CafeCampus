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

@Composable fun LoginScreen(vm: SesionViewModel,volver: ()->Unit={}) {
    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    val error by vm.error.collectAsStateWithLifecycle()
    val cargando by vm.cargando.collectAsStateWithLifecycle()
    val oscuro by vm.modoOscuro.collectAsStateWithLifecycle()
    CafeTheme(rol,oscuro) {
        Surface(color=MaterialTheme.colorScheme.background,modifier=Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(18.dp)) {
                Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(12.dp)) { VolverCampus(volver);Icon(IconosCafe.Coffee,null,tint=MaterialTheme.colorScheme.primary);Text("CafeCampus",style=MaterialTheme.typography.titleSmall) }
                HeroCampus("Tu próxima\npausa.","Tu cafetería, a un toque.",Modifier.heightIn(min=224.dp))
                Column(verticalArrangement=Arrangement.spacedBy(5.dp)) { Text("Bienvenido",style=MaterialTheme.typography.headlineLarge);Text("Ingresa para continuar",color=MaterialTheme.colorScheme.onSurfaceVariant,style=MaterialTheme.typography.bodyMedium) }
                SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) { Rol.entries.forEachIndexed { i,r -> SegmentedButton(selected=rol==r,onClick={rol=r;vm.limpiarError()},shape=SegmentedButtonDefaults.itemShape(i,2,baseShape=RoundedCornerShape(14.dp))) { Text(if(r==Rol.CLIENTE) "Estudiante" else "Administrador",style=MaterialTheme.typography.labelMedium) } } }
                CafeTextField(correo,{correo=it},"Correo",teclado=KeyboardType.Email)
                CafeTextField(clave,{clave=it},"Contraseña",secreto=true)
                ErrorTexto(error)
                CafeButton(if(cargando) "Ingresando…" else "Ingresar",!cargando) { vm.login(correo,clave,rol) }
                Row(horizontalArrangement=Arrangement.spacedBy(10.dp),verticalAlignment=Alignment.Top) {
                    Icon(IconosCafe.SwapHoriz,null,tint=MaterialTheme.colorScheme.primary,modifier=Modifier.size(20.dp))
                    Text("Modo práctica: cambia entre estudiante y administrador desde tu perfil.",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
                }
                LazoCampus(Modifier.fillMaxWidth().height(70.dp))
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
