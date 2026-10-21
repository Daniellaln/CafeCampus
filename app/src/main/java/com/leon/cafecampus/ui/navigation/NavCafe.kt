package com.leon.cafecampus.ui.navigation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.leon.cafecampus.CafeApp
import com.leon.cafecampus.viewmodel.*
import com.leon.cafecampus.ui.cliente.*
import com.leon.cafecampus.ui.components.*
import com.leon.cafecampus.ui.theme.CafeTheme
@Composable fun NavCafe(app: CafeApp) {
    val nav=rememberNavController()
    val menu: MenuViewModel=viewModel(factory=CafeViewModelFactory { MenuViewModel(app.repository) })
    CafeTheme {
        Scaffold { padding -> NavHost(nav,startDestination=Rutas.SPLASH,modifier=Modifier.padding(padding)) {
            composable(Rutas.SPLASH) { SplashScreen { nav.navigate(Rutas.MENU) } }
            composable(Rutas.MENU) { MenuScreen(menu,onProducto={}) }
        } }
    }
}
