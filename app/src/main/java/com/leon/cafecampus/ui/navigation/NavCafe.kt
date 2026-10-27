package com.leon.cafecampus.ui.navigation
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.*
import com.leon.cafecampus.CafeApp
import com.leon.cafecampus.model.Rol
import com.leon.cafecampus.data.prefs.PreferenciasStore
import com.leon.cafecampus.viewmodel.*
import com.leon.cafecampus.ui.cliente.*
import com.leon.cafecampus.ui.admin.*
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.ui.theme.CafeTheme
@Composable fun NavCafe(app: CafeApp) {
    // Punto de composición: inyecta dependencias; las pantallas solo reciben ViewModels.
    val prefs=remember { PreferenciasStore(app) }
    val sesion: SesionViewModel=viewModel(factory=CafeViewModelFactory { SesionViewModel(app.repository,prefs) { app.inicializacion.await() } })
    val menu: MenuViewModel=viewModel(factory=viewModelFactory { initializer { MenuViewModel(app.repository,createSavedStateHandle()) } })
    val usuario by sesion.usuarioActual.collectAsStateWithLifecycle()
    val cargando by sesion.cargando.collectAsStateWithLifecycle()
    val oscuro by sesion.modoOscuro.collectAsStateWithLifecycle()
    val abierto by sesion.recibirPedidos.collectAsStateWithLifecycle()
    val repetido by menu.repetido.collectAsStateWithLifecycle()
    val avisoRepetir by menu.error.collectAsStateWithLifecycle()
    val repitiendo by menu.repitiendo.collectAsStateWithLifecycle()
    val carrito by menu.carrito.collectAsStateWithLifecycle()
    val admin=usuario?.rol==Rol.ADMIN.name
    val nav=rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val ruta=entry?.destination?.route
    val cliente: PedidosClienteViewModel=viewModel(key="pedidos_cliente_${usuario?.id ?: 0}",factory=CafeViewModelFactory { PedidosClienteViewModel(app.repository,usuario?.id ?: 0,prefs) })
    val panel: PanelAdminViewModel=viewModel(factory=CafeViewModelFactory { PanelAdminViewModel(app.repository) })
    val pedidosAdmin: PedidosAdminViewModel=viewModel(factory=CafeViewModelFactory { PedidosAdminViewModel(app.repository) })
    val menuAdmin: MenuAdminViewModel=viewModel(factory=CafeViewModelFactory { MenuAdminViewModel(app.repository) })
    fun raiz(destino: String) { nav.navigate(destino) { popUpTo(nav.graph.id) { inclusive=true }; launchSingleTop=true } }
    var identidadAnterior by androidx.compose.runtime.saveable.rememberSaveable { mutableStateOf<Int?>(null) }
    LaunchedEffect(usuario?.id, entry != null, cargando) {
        if(entry != null && !cargando && identidadAnterior != usuario?.id) {
            identidadAnterior=usuario?.id
            if(usuario!=null) raiz(if(admin) Rutas.PANEL else Rutas.MENU)
            else { menu.vaciar(); raiz(Rutas.LOGIN) }
        }
    }
    LaunchedEffect(repetido) { if(repetido) { nav.navigate(Rutas.CONFIRMAR);menu.consumirRepetido() } }
    CafeTheme(if(admin) Rol.ADMIN else Rol.CLIENTE,oscuro) {
        val destinos=if(admin) listOf(Triple(Rutas.PANEL,"Panel",Icons.Default.BarChart),Triple(Rutas.ADMIN_PEDIDOS,"Pedidos",Icons.Default.ReceiptLong),Triple(Rutas.ADMIN_MENU,"Menú",Icons.Default.Coffee),Triple(Rutas.AJUSTES,"Ajustes",Icons.Default.Settings))
            else listOf(Triple(Rutas.MENU,"Menú",Icons.Default.Home),Triple(Rutas.PEDIDOS,"Pedidos",Icons.Default.ReceiptLong),Triple(Rutas.PERFIL,"Perfil",Icons.Default.PersonOutline))
        val plano=rememberGraphicsLayer()
        var origen by remember { mutableStateOf(Offset.Zero) }
        val barra=usuario!=null && ruta in listOf(Rutas.MENU,Rutas.PEDIDOS,Rutas.PERFIL,Rutas.SEGUIMIENTO,Rutas.PANEL,Rutas.ADMIN_PEDIDOS,Rutas.ADMIN_MENU,Rutas.AJUSTES)
        Box(Modifier.fillMaxSize()) {
        FondoCampus(Modifier.matchParentSize())
        Scaffold(containerColor=androidx.compose.ui.graphics.Color.Transparent,contentColor=MaterialTheme.colorScheme.onBackground,bottomBar={ if(barra) NavegacionLiquida(destinos.map { it.second to it.third },destinos.indexOfFirst { ruta==it.first || (it.first==Rutas.PEDIDOS && ruta==Rutas.SEGUIMIENTO) }.coerceAtLeast(0),{ indice ->
            nav.navigate(destinos[indice].first) { popUpTo(if(admin) Rutas.PANEL else Rutas.MENU) { inclusive=false };launchSingleTop=true }
        },plano,origen) }) { padding ->
            NavHost(nav,startDestination=Rutas.SPLASH,modifier=Modifier.padding(padding)) {
                composable(Rutas.SPLASH) { if(cargando) Box(Modifier.fillMaxSize(),contentAlignment=Alignment.Center) { CircularProgressIndicator() } else SplashScreen { nav.navigate(Rutas.LOGIN) } }
                composable(Rutas.LOGIN) { LoginScreen(sesion) { if(!nav.popBackStack()) nav.navigate(Rutas.SPLASH) { popUpTo(Rutas.LOGIN) { inclusive=true } } } }
                composable(Rutas.MENU) { MenuScreen(menu,{nav.navigate("producto/$it")},usuario?.nombre ?: "Estudiante",carrito.sumOf { it.cantidad },abierto) { nav.navigate(Rutas.CONFIRMAR) } }
                composable(Rutas.PRODUCTO,arguments=listOf(navArgument("id") { type=NavType.IntType })) { DetalleProductoScreen(it.arguments?.getInt("id") ?: 0,menu) { nav.popBackStack() } }
                composable(Rutas.CONFIRMAR) { ConfirmarPedidoScreen(menu,cliente,abierto,{nav.popBackStack()}) { nav.navigate("seguimiento/$it") { popUpTo(Rutas.CONFIRMAR) { inclusive=true } } } }
                composable(Rutas.SEGUIMIENTO,arguments=listOf(navArgument("id") { type=NavType.IntType })) { SeguimientoScreen(it.arguments?.getInt("id") ?: 0,cliente) { if(!nav.popBackStack()) raiz(Rutas.MENU) } }
                composable(Rutas.PEDIDOS) { MisPedidosScreen(cliente,abrir={nav.navigate("seguimiento/$it")},repetir={menu.repetir(it)},aviso=avisoRepetir,ocupado=repitiendo) }
                composable(Rutas.PERFIL) { AjustesScreen(sesion,cliente=true) }
                composable(Rutas.PANEL) { PanelScreen(panel,pedidosAdmin,{nav.navigate("admin_detalle/$it")},{nav.navigate(Rutas.ADMIN_PEDIDOS)}) }
                composable(Rutas.ADMIN_PEDIDOS) { PedidosAdminScreen(pedidosAdmin) { nav.navigate("admin_detalle/$it") } }
                composable(Rutas.ADMIN_DETALLE,arguments=listOf(navArgument("id") { type=NavType.IntType })) { DetallePedidoAdminScreen(it.arguments?.getInt("id") ?: 0,pedidosAdmin) { nav.popBackStack() } }
                composable(Rutas.ADMIN_MENU) { MenuAdminScreen(menuAdmin,{nav.navigate("formulario?id=$it")},{nav.navigate("formulario")}) }
                composable(Rutas.FORMULARIO,arguments=listOf(navArgument("id") { type=NavType.IntType;defaultValue=0 })) { FormularioProductoScreen((it.arguments?.getInt("id") ?: 0).takeIf { id -> id>0 },menuAdmin) { nav.popBackStack() } }
                composable(Rutas.AJUSTES) { AjustesScreen(sesion) }
            }
        }
        }
    }
}
