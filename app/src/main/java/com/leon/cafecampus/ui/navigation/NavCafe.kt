package com.leon.cafecampus.ui.navigation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.navigation.compose.*
import com.leon.cafecampus.CafeApp
import com.leon.cafecampus.model.Rol
import com.leon.cafecampus.data.prefs.PreferenciasStore
import com.leon.cafecampus.viewmodel.*
import com.leon.cafecampus.ui.cliente.*
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.ui.theme.CafeTheme
@Composable fun NavCafe(app: CafeApp) {
    val prefs=remember { PreferenciasStore(app) }
    val sesion: SesionViewModel=viewModel(factory=CafeViewModelFactory { SesionViewModel(app.repository,prefs) { app.inicializacion.await() } })
    val menu: MenuViewModel=viewModel(factory=viewModelFactory { initializer { MenuViewModel(app.repository,createSavedStateHandle()) } })
    val carrito by menu.carrito.collectAsStateWithLifecycle()
    val usuario by sesion.usuarioActual.collectAsStateWithLifecycle()
    val cargando by sesion.cargando.collectAsStateWithLifecycle()
    val oscuro by sesion.modoOscuro.collectAsStateWithLifecycle()
    CafeTheme(usuario?.rol?.let { Rol.valueOf(it) } ?: Rol.CLIENTE,oscuro) {
        val nav=rememberNavController()
        LaunchedEffect(usuario?.id) { if(usuario!=null) nav.navigate(if(usuario?.rol==Rol.ADMIN.name) Rutas.PANEL else Rutas.MENU) { popUpTo(0) { inclusive=true } } }
        Scaffold { padding ->
            NavHost(nav,startDestination=Rutas.SPLASH,modifier=Modifier.padding(padding)) {
                composable(Rutas.SPLASH) { if(cargando) Box(Modifier.fillMaxSize(),contentAlignment=Alignment.Center) { CircularProgressIndicator() } else SplashScreen { nav.navigate(Rutas.LOGIN) } }
                composable(Rutas.LOGIN) { LoginScreen(sesion) }
                composable(Rutas.PANEL) { Column { AppTopBar("Panel de control"); Text("Administrador · Datos y sesión listos"); CafeButton("Cambiar al estudiante") { sesion.cambiarPerfil() } } }
                composable(Rutas.MENU) { MenuScreen(menu,onProducto={ nav.navigate("producto/$it") },nombre=usuario?.nombre ?: "Daniella",cantidad=carrito.sumOf { it.cantidad }) }
                composable(Rutas.PRODUCTO) { DetalleProductoScreen(it.arguments?.getString("id")?.toIntOrNull() ?: 0,menu) { nav.popBackStack() } }
            }
        }
    }
}
