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

@Composable fun SplashScreen(comenzar: ()->Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val aire=maxHeight*.25f
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal=24.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
            Row(Modifier.padding(top=22.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(10.dp)) { Icon(IconosCafe.Coffee,null,tint=MaterialTheme.colorScheme.primary,modifier=Modifier.size(28.dp));Text("CafeCampus",style=MaterialTheme.typography.titleLarge) }
            Spacer(Modifier.height(aire))
            Text("Tu pausa,\nbien elegida.",style=MaterialTheme.typography.displayMedium)
            Text("Elige lo que te gusta. Pide desde aquí y recoge en tu cafetería.",style=MaterialTheme.typography.bodyLarge,color=MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            CafeButton("Comenzar",onClick=comenzar)
            TextButton(onClick=comenzar,modifier=Modifier.fillMaxWidth()) { Text("Ya tengo una cuenta") }
            Spacer(Modifier.height(16.dp))
        }
    }
}

