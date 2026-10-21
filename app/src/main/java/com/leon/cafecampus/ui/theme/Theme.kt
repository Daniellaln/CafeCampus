package com.leon.cafecampus.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.leon.cafecampus.model.Rol
@Composable fun CafeTheme(rol: Rol = Rol.CLIENTE, oscuro: Boolean = false, content: @Composable () -> Unit) {
    val admin = rol == Rol.ADMIN
    val esquema = if(oscuro) darkColorScheme(
        primary=if(admin) Color(0xFFC4B5FD) else Color(0xFFFFB783),
        onPrimary=Color(0xFF27133A), primaryContainer=if(admin) Color(0xFF37306B) else Color(0xFF653415),
        background=Color(0xFF17151B), surface=Color(0xFF211F26)
    ) else lightColorScheme(
        primary=if(admin) AdminPrimario else ClientePrimario, onPrimary=Color.White,
        primaryContainer=if(admin) Color(0xFFE0E7FF) else Color(0xFFFFEDD5),
        onPrimaryContainer=if(admin) Color(0xFF312E81) else Color(0xFF7C2D12),
        background=if(admin) AdminFondo else ClienteFondo, surface=Color.White,
        onBackground=Color(0xFF202733), onSurface=Color(0xFF202733),
        onSurfaceVariant=Color(0xFF626871), outlineVariant=Color(0xFFE3E3E8)
    )
    val view=LocalView.current
    if(!view.isInEditMode) SideEffect {
        (view.context as? Activity)?.window?.let { window ->
            WindowCompat.getInsetsController(window,view).apply {
                isAppearanceLightStatusBars=!oscuro
                isAppearanceLightNavigationBars=!oscuro
            }
        }
    }
    val colores=esquema.copy(secondary=esquema.primary, onSecondary=esquema.onPrimary,
        secondaryContainer=esquema.primary, onSecondaryContainer=esquema.onPrimary,
        surfaceVariant=if(oscuro) Color(0xFF343137) else Color(0xFFF0EEEC))
    MaterialTheme(colorScheme=colores, typography=Typography, shapes=Shapes(small=RoundedCornerShape(10.dp),medium=RoundedCornerShape(12.dp),large=RoundedCornerShape(16.dp)), content=content)
}
