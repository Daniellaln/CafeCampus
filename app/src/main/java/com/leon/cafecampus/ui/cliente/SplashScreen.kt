package com.leon.cafecampus.ui.cliente
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.leon.cafecampus.ui.components.*
@Composable fun SplashScreen(comenzar: ()->Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.SpaceBetween) {
        Spacer(Modifier.height(16.dp))
        Column(horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Default.Coffee,null,Modifier.size(88.dp),tint=MaterialTheme.colorScheme.primary)
            Text("CafeCampus",style=MaterialTheme.typography.headlineLarge)
            Text("Pide sin hacer cola",color=MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) { listOf("cafe","plato","jugo").forEach { ProductoIcono(it,Modifier.size(88.dp)) } }
        Column { CafeButton("Comenzar",onClick=comenzar); TextButton(onClick=comenzar,modifier=Modifier.fillMaxWidth()) { Text("Ya tengo una cuenta") } }
    }
}
