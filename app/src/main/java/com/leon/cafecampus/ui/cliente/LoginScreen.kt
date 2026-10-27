package com.leon.cafecampus.ui.cliente
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.model.Rol
import com.leon.cafecampus.viewmodel.SesionViewModel
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.ui.theme.CafeTheme
@Composable fun LoginScreen(vm: SesionViewModel,volver: ()->Unit={}) {
    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    val error by vm.error.collectAsStateWithLifecycle()
    val cargando by vm.cargando.collectAsStateWithLifecycle()
    val oscuro by vm.modoOscuro.collectAsStateWithLifecycle()
    CafeTheme(rol,oscuro) {
        Surface(color=MaterialTheme.colorScheme.background,modifier=Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.height(24.dp))
                Text("Bienvenido",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold)
                Text("Ingresa para continuar",color=MaterialTheme.colorScheme.onSurfaceVariant)
                SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                    Rol.entries.forEachIndexed { i,r -> SegmentedButton(selected=rol==r,onClick={rol=r;vm.limpiarError()},shape=SegmentedButtonDefaults.itemShape(i,2)) { Text(if(r==Rol.CLIENTE) "Estudiante" else "Administrador") } }
                }
                CafeTextField(correo,{correo=it},"Correo",teclado=KeyboardType.Email)
                CafeTextField(clave,{clave=it},"Contraseña",secreto=true)
                ErrorTexto(error)
                CafeButton(if(cargando) "Ingresando…" else "Ingresar",!cargando) { vm.login(correo,clave,rol) }
                Surface(color=MaterialTheme.colorScheme.primaryContainer,shape=MaterialTheme.shapes.medium) {
                    Column(Modifier.padding(16.dp),verticalArrangement=Arrangement.spacedBy(6.dp)) {
                        Text("Modo práctica",fontWeight=FontWeight.Bold,color=MaterialTheme.colorScheme.onPrimaryContainer)
                        Text("Puedes cambiar de perfil desde Ajustes para probar ambos roles.",color=MaterialTheme.colorScheme.onPrimaryContainer,style=MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
