package com.leon.cafecampus.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.viewmodel.*
import com.leon.cafecampus.model.*
import com.leon.cafecampus.data.local.entity.*

@Composable fun FormularioProductoScreen(id: Int?,vm: MenuAdminViewModel,volver: ()->Unit) {
    val productos by vm.productos.collectAsStateWithLifecycle()
    val p=productos.find { it.id==id }
    val guardado by vm.guardado.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val ocupado by vm.ocupado.collectAsStateWithLifecycle()
    var nombre by rememberSaveable(id) { mutableStateOf("") }
    var categoria by rememberSaveable(id) { mutableStateOf("Snacks") }
    var precio by rememberSaveable(id) { mutableStateOf("") }
    var descripcion by rememberSaveable(id) { mutableStateOf("") }
    var disponible by rememberSaveable(id) { mutableStateOf(true) }
    var icono by rememberSaveable(id) { mutableStateOf("pan") }
    var cargado by rememberSaveable(id) { mutableStateOf(false) }
    LaunchedEffect(id) { vm.prepararFormulario() }
    LaunchedEffect(p) { if(p!=null && !cargado) { nombre=p.nombre;categoria=p.categoria;precio=p.precio.toString();descripcion=p.descripcion;disponible=p.disponible;icono=p.icono;cargado=true } }
    LaunchedEffect(guardado) { if(guardado) { vm.prepararFormulario(); volver() } }
    Column {
        AppTopBar(if(id==null) "Nuevo producto" else "Editar producto",volver)
        Column(Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(16.dp),verticalArrangement=Arrangement.spacedBy(14.dp)) {
            if(id!=null && p==null) Vacio("Cargando producto…") else {
                ProductoIcono(icono,Modifier.fillMaxWidth().height(90.dp))
                Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) { listOf("cafe","plato","jugo","pan","botella","dulce").forEach { i -> FilterChip(selected=icono==i,onClick={icono=i},label={Text(i)}) } }
                CafeTextField(nombre,{nombre=it},"Nombre del producto")
                Text("Categoría",style=MaterialTheme.typography.labelMedium)
                Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(8.dp)) { listOf("Desayunos","Almuerzos","Bebidas","Snacks").forEach { c -> FilterChip(selected=categoria==c,onClick={categoria=c},label={Text(c)}) } }
                CafeTextField(precio,{precio=it},"Precio (S/)",teclado=KeyboardType.Decimal)
                OutlinedTextField(value=descripcion,onValueChange={descripcion=it},label={Text("Descripción")},modifier=Modifier.fillMaxWidth(),minLines=2,shape=MaterialTheme.shapes.medium)
                Tarjeta { Row(Modifier.fillMaxWidth(),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.SpaceBetween) { Text("Disponible en el menú",Modifier.weight(1f)); Switch(checked=disponible,onCheckedChange={disponible=it}) } }
                ErrorTexto(error)
                CafeButton(if(ocupado) "Guardando…" else "Guardar producto",!ocupado) { vm.guardar(Producto(id=id ?: 0,nombre=nombre,descripcion=descripcion,categoria=categoria,precio=precio.replace(',','.').toDoubleOrNull() ?: 0.0,disponible=disponible,icono=icono)) }
                OutlinedButton(onClick=volver,enabled=!ocupado,modifier=Modifier.fillMaxWidth()) { Text("Cancelar") }
            }
        }
    }
}
