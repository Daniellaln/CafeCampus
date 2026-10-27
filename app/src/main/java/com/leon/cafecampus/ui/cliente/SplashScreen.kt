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
    BoxWithConstraints(Modifier.fillMaxSize().background(CampusBlue)) {
        val heroHeight=(maxHeight*0.46f).coerceAtMost(380.dp)
        FotoCampus(Modifier.fillMaxWidth().height(heroHeight).align(Alignment.TopCenter))
        AdornoFloral(Modifier.width(160.dp).height(180.dp).align(Alignment.TopEnd).offset(x=20.dp,y=heroHeight-60.dp))
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal=24.dp),verticalArrangement=Arrangement.spacedBy(18.dp)) {
            Row(Modifier.padding(top=18.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)) { Icon(Icons.Default.Coffee,null,tint=CampusBlue,modifier=Modifier.size(22.dp));Text("CafeCampus",style=MaterialTheme.typography.titleMedium,color=CampusInk) }
            Spacer(Modifier.height((heroHeight-90.dp).coerceAtLeast(100.dp)))
            Text("Tu pausa,\nbien elegida.",style=MaterialTheme.typography.displayMedium,color=Color.White)
            Text("Elige lo que te gusta. Pide desde aquí y recoge en tu cafetería.",style=MaterialTheme.typography.bodyLarge,color=Color.White.copy(alpha=.9f))
            Row(horizontalArrangement=Arrangement.spacedBy(16.dp),verticalAlignment=Alignment.CenterVertically) {
                Surface(color=MaterialTheme.colorScheme.secondaryContainer,shape=RoundedCornerShape(10.dp)) { Icon(Icons.Default.Restaurant,null,Modifier.padding(10.dp).size(20.dp),tint=MaterialTheme.colorScheme.onSecondaryContainer) }
                Text("Tu campus. Tu cafetería. Tu momento.",style=MaterialTheme.typography.bodySmall,color=Color.White.copy(alpha=.9f))
            }
            Button(onClick=comenzar,modifier=Modifier.fillMaxWidth().heightIn(min=56.dp),shape=RoundedCornerShape(18.dp),colors=ButtonDefaults.buttonColors(containerColor=CampusCitron,contentColor=CampusInk)) { Text("Comenzar",style=MaterialTheme.typography.labelLarge) }
            TextButton(onClick=comenzar,modifier=Modifier.fillMaxWidth()) { Text("Ya tengo una cuenta",color=Color.White) }
            Spacer(Modifier.height(12.dp))
        }
    }
}
