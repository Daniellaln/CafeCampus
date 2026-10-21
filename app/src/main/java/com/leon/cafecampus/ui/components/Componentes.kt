package com.leon.cafecampus.ui.components
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import com.leon.cafecampus.data.local.entity.Producto
import com.leon.cafecampus.model.EstadoPedido
import java.util.Locale
fun dinero(n: Double) = "S/ %.2f".format(Locale.US, n)
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun AppTopBar(titulo: String, volver: (() -> Unit)? = null, acciones: @Composable RowScope.() -> Unit = {}) {
    CenterAlignedTopAppBar(title={ Text(titulo, style=MaterialTheme.typography.titleMedium, fontWeight=FontWeight.Bold) },
        navigationIcon={ if(volver != null) IconButton(onClick=volver) { Icon(Icons.AutoMirrored.Filled.ArrowBack,"Volver") } },
        actions=acciones, colors=TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor=MaterialTheme.colorScheme.background))
}
@Composable fun CafeButton(texto: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(onClick=onClick, enabled=enabled, modifier=Modifier.fillMaxWidth().heightIn(min=50.dp), shape=RoundedCornerShape(10.dp)) { Text(texto, fontWeight=FontWeight.Bold) }
}
@Composable fun CafeTextField(valor: String, cambio: (String)->Unit, etiqueta: String, secreto: Boolean=false, teclado: KeyboardType=KeyboardType.Text) {
    OutlinedTextField(value=valor,onValueChange=cambio,label={Text(etiqueta)},modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp),singleLine=true,
        visualTransformation=if(secreto) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions=androidx.compose.foundation.text.KeyboardOptions(keyboardType=teclado))
}
@Composable fun Tarjeta(modifier: Modifier=Modifier, content: @Composable ColumnScope.()->Unit) {
    Surface(modifier=modifier.fillMaxWidth(),shape=RoundedCornerShape(12.dp), color=MaterialTheme.colorScheme.surface, border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.padding(14.dp), verticalArrangement=Arrangement.spacedBy(8.dp),content=content)
    }
}
@Composable fun ErrorTexto(texto: String?) { if(texto != null) Text(texto,color=MaterialTheme.colorScheme.error,style=MaterialTheme.typography.bodyMedium) }
@Composable fun Vacio(texto: String) { Column(Modifier.fillMaxWidth().padding(vertical=36.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(12.dp)) { Icon(Icons.Default.ReceiptLong,null,Modifier.size(40.dp),tint=MaterialTheme.colorScheme.onSurfaceVariant); Text(texto, color=MaterialTheme.colorScheme.onSurfaceVariant) } }
@Composable fun ProductoIcono(icono: String, modifier: Modifier=Modifier) { ProductoIlustracion(icono,modifier) }
@Composable fun Disponible(disponible: Boolean) {
    Surface(color=if(disponible) Color(0xFFDCFCE7) else Color(0xFFFEE2E2), shape=RoundedCornerShape(20.dp)) {
        Text(if(disponible) "Disponible" else "Agotado",Modifier.padding(horizontal=8.dp,vertical=3.dp),color=if(disponible) Color(0xFF166534) else Color(0xFF991B1B),style=MaterialTheme.typography.labelSmall)
    }
}
@Composable fun EstadoChip(estado: EstadoPedido) {
    val (bg,fg)=when(estado) {
        EstadoPedido.RECIBIDO -> Color(0xFFDBEAFE) to Color(0xFF1E40AF)
        EstadoPedido.PREPARANDO -> Color(0xFFFEF3C7) to Color(0xFF92400E)
        EstadoPedido.LISTO -> Color(0xFFDCFCE7) to Color(0xFF166534)
        EstadoPedido.ENTREGADO -> Color(0xFFE5E7EB) to Color(0xFF374151)
        EstadoPedido.CANCELADO -> Color(0xFFFEE2E2) to Color(0xFF991B1B)
    }
    Surface(color=bg,shape=RoundedCornerShape(20.dp)) { Text(estado.etiqueta,Modifier.padding(horizontal=8.dp,vertical=4.dp),color=fg,style=MaterialTheme.typography.labelSmall) }
}
@Composable fun ProductoCard(p: Producto, click: ()->Unit) {
    Surface(onClick=click,shape=RoundedCornerShape(12.dp),color=MaterialTheme.colorScheme.surface,border=BorderStroke(1.dp,MaterialTheme.colorScheme.outlineVariant)) {
        Column(Modifier.padding(8.dp),verticalArrangement=Arrangement.spacedBy(8.dp)) {
            ProductoIcono(p.icono,Modifier.fillMaxWidth().height(86.dp))
            Text(p.nombre,style=MaterialTheme.typography.titleSmall, minLines=2)
            if(!p.disponible) Disponible(false)
            Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) {
                Text(dinero(p.precio),color=MaterialTheme.colorScheme.primary,fontWeight=FontWeight.Bold,style=MaterialTheme.typography.bodyMedium)
                FilledIconButton(onClick=click,shape=RoundedCornerShape(10.dp)) { Icon(Icons.Default.Add,"Ver ${p.nombre}") }
            }
        }
    }
}
@Composable fun Categorias(actual: String, cambiar: (String)->Unit) {
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
        listOf("Todo","Desayunos","Almuerzos","Bebidas","Snacks").forEach { FilterChip(selected=actual==it,onClick={cambiar(it)},label={Text(it)}) }
    }
}
@Composable fun StepperEstado(estado: EstadoPedido) {
    if(estado == EstadoPedido.CANCELADO) { EstadoChip(estado); Text("Este pedido fue cancelado."); return }
    val pasos=listOf(EstadoPedido.RECIBIDO,EstadoPedido.PREPARANDO,EstadoPedido.LISTO,EstadoPedido.ENTREGADO)
    val notas=listOf("Tu pedido llegó a la cocina", "Están cocinando tu pedido", "Puedes pasar a recogerlo", "¡Buen provecho!")
    pasos.forEachIndexed { index,p ->
        Row(Modifier.fillMaxWidth().heightIn(min=86.dp),horizontalArrangement=Arrangement.spacedBy(16.dp)) {
            Column(horizontalAlignment=Alignment.CenterHorizontally) {
                Surface(shape=CircleShape,color=if(index<=estado.ordinal) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,modifier=Modifier.size(32.dp)) {
                    Box(contentAlignment=Alignment.Center) { if(index<estado.ordinal) Icon(Icons.Default.Check,null,tint=MaterialTheme.colorScheme.onPrimary) else Text("${index+1}",color=if(index<=estado.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant) }
                }
                if(index<3) Box(Modifier.width(2.dp).height(50.dp).background(MaterialTheme.colorScheme.outlineVariant))
            }
            Column(verticalArrangement=Arrangement.spacedBy(4.dp)) { Text(p.etiqueta,fontWeight=FontWeight.Bold,color=if(index<=estado.ordinal) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant); Text(notas[index],style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant) }
        }
    }
}

@Composable fun EstadoContador(cantidad: Int,estado: EstadoPedido,modifier: Modifier=Modifier,click: ()->Unit) {
    val (bg,fg)=when(estado) {
        EstadoPedido.RECIBIDO -> Color(0xFFDBEAFE) to Color(0xFF1E40AF)
        EstadoPedido.PREPARANDO -> Color(0xFFFEF3C7) to Color(0xFF92400E)
        else -> Color(0xFFDCFCE7) to Color(0xFF166534)
    }
    Surface(onClick=click,modifier=modifier,shape=MaterialTheme.shapes.medium,color=bg,contentColor=fg) {
        Column(Modifier.padding(vertical=16.dp,horizontal=4.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(8.dp)) {
            Text("$cantidad",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold)
            Text(if(estado==EstadoPedido.RECIBIDO) "Nuevos" else estado.etiqueta,style=MaterialTheme.typography.labelMedium)
        }
    }
}
@Composable fun DisponibilidadChip(disponible: Boolean,enabled: Boolean=true,click: ()->Unit) {
    FilterChip(selected=disponible,onClick=click,enabled=enabled,label={Text(if(disponible) "Disponible" else "Agotado")},
        colors=FilterChipDefaults.filterChipColors(containerColor=Color(0xFFFEE2E2),labelColor=Color(0xFF991B1B),selectedContainerColor=Color(0xFFDCFCE7),selectedLabelColor=Color(0xFF166534)))
}
