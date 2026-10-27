package com.leon.cafecampus.ui.components
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.leon.cafecampus.R
@Composable fun PapelInterior(modifier: Modifier=Modifier,content: @Composable ColumnScope.()->Unit) {
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    Surface(modifier.fillMaxWidth().sombraCampus(22.dp,oscuro,.45f),shape=RoundedCornerShape(22.dp),color=if(oscuro) MaterialTheme.colorScheme.surface else Color(0xFFFDFDFC).copy(alpha=.96f)) {
        Column(Modifier.padding(18.dp),verticalArrangement=Arrangement.spacedBy(14.dp),content=content)
    }
}
@Composable fun OpcionCristal(texto: String,seleccionado: Boolean,modifier: Modifier=Modifier,icono: ImageVector?=null,onClick: ()->Unit) {
    CristalRosa(modifier.height(48.dp),24.dp,seleccionado=seleccionado,accion=onClick) {
        Row(Modifier.height(48.dp).padding(horizontal=16.dp),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            if(icono!=null) Icon(icono,null,Modifier.size(21.dp),tint=MaterialTheme.colorScheme.primary)
            Text(texto,style=MaterialTheme.typography.labelLarge,color=if(seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,maxLines=1)
        }
    }
}
@Composable fun FotoProductoInterior(icono: String,modifier: Modifier=Modifier) { Image(painterResource(fotoProducto(icono)),null,modifier.clip(RoundedCornerShape(14.dp)),contentScale=ContentScale.Crop) }
@Composable fun AvatarPerfil(nombre: String,fotoDaniella: Boolean,modifier: Modifier=Modifier) {
    val oscuro=MaterialTheme.colorScheme.background.luminance()<.2f
    Box(modifier.sombraCampus(60.dp,oscuro).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer).border(3.dp,Color.White.copy(alpha=.9f),CircleShape),contentAlignment=Alignment.Center) {
        if(fotoDaniella) Image(painterResource(R.drawable.daniella_perfil),"Foto de perfil de Daniella",Modifier.fillMaxSize().padding(4.dp).clip(CircleShape),contentScale=ContentScale.Crop,alignment=Alignment.CenterStart)
        else Text(nombre.take(1),style=MaterialTheme.typography.displayMedium,color=MaterialTheme.colorScheme.onSecondaryContainer)
    }
}
