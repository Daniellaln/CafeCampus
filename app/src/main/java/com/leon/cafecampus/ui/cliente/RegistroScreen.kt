package com.leon.cafecampus.ui.cliente
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.viewmodel.SesionViewModel
@Composable fun RegistroScreen(vm: SesionViewModel,volver: ()->Unit) {
    var nombre by rememberSaveable { mutableStateOf("") };var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") };var confirmar by rememberSaveable { mutableStateOf("") }
    val error by vm.error.collectAsStateWithLifecycle();val cargando by vm.cargando.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        AppTopBar("Crear cuenta",volver={vm.limpiarError();volver()})
        Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(24.dp),verticalArrangement=Arrangement.spacedBy(18.dp)) {
            Text("Tu lugar en el campus.",style=MaterialTheme.typography.headlineLarge)
            Text("Crea tu cuenta de estudiante.",style=MaterialTheme.typography.bodyLarge,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(28.dp))
            CafeTextField(nombre,{nombre=it},"Nombre")
            CafeTextField(correo,{correo=it},"Correo",teclado=KeyboardType.Email)
            CafeTextField(clave,{clave=it},"Contraseña",secreto=true)
            Text("Mínimo 6 caracteres",style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            CafeTextField(confirmar,{confirmar=it},"Repetir contraseña",secreto=true)
            ErrorTexto(error)
            CafeButton(if(cargando) "Creando cuenta…" else "Crear mi cuenta",!cargando) { vm.registrar(nombre,correo,clave,confirmar) }
            TextButton(onClick=volver,modifier=Modifier.fillMaxWidth()) { Text("Ya tengo una cuenta") }
        }
    }
}
