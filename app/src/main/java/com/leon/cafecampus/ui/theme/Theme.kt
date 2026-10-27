package com.leon.cafecampus.ui.theme
import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.leon.cafecampus.model.Rol
val LocalAdminCampus=staticCompositionLocalOf { false }
@Composable fun CafeTheme(rol: Rol=Rol.CLIENTE,oscuro: Boolean=false,content: @Composable ()->Unit) {
    val admin=rol==Rol.ADMIN
    val esquema=if(oscuro) darkColorScheme(
        primary=if(admin) Color(0xFFABC1FF) else Color(0xFFFFB0CF),onPrimary=if(admin) Color(0xFF11274B) else Color(0xFF590C30),primaryContainer=if(admin) Color(0xFF253E6C) else Color(0xFF6C2448),onPrimaryContainer=Color(0xFFFFE6F0),
        secondary=if(admin) Color(0xFFABC1FF) else Color(0xFFFFB8D5),onSecondary=Color(0xFF5E1639),secondaryContainer=if(admin) Color(0xFF263A56) else Color(0xFF63354E),onSecondaryContainer=if(admin) Color(0xFFD6E5FF) else Color(0xFFFFE4EF),
        tertiary=Color(0xFFF2C178),tertiaryContainer=Color(0xFF4A3823),onTertiaryContainer=Color(0xFFFFE7C4),
        background=if(admin) Color(0xFF101B2D) else Color(0xFF211B20),surface=if(admin) Color(0xFF1A2940) else Color(0xFF342334),onBackground=if(admin) Color(0xFFE5EEFF) else Color(0xFFFFF0F6),onSurface=if(admin) Color(0xFFE5EEFF) else Color(0xFFFFF0F6),
        surfaceVariant=if(admin) Color(0xFF283B55) else Color(0xFF493345),onSurfaceVariant=if(admin) Color(0xFFB6C7DE) else Color(0xFFD9B6C9),outline=if(admin) Color(0xFF829DBE) else Color(0xFF9E748D),outlineVariant=if(admin) Color(0xFF405675) else Color(0xFF65465B),
        error=Color(0xFFFFB4AB),errorContainer=Color(0xFF5B2826),onErrorContainer=Color(0xFFFFDAD6)
    ) else lightColorScheme(
        primary=if(admin) AdminPrimario else ClientePrimario,onPrimary=Color.White,primaryContainer=if(admin) Color(0xFFE3EBFF) else Color(0xFFFFE1ED),onPrimaryContainer=if(admin) Color(0xFF193B69) else Color(0xFF7E1949),
        secondary=if(admin) Color(0xFF365B8C) else Color(0xFF8B385D),onSecondary=Color.White,secondaryContainer=if(admin) Color(0xFFE8EFF9) else Color(0xFFF9E7EF),onSecondaryContainer=if(admin) Color(0xFF193B69) else Color(0xFF721D45),
        tertiary=Color(0xFF7F5113),tertiaryContainer=Color(0xFFFFE6BB),onTertiaryContainer=Color(0xFF513006),
        background=if(admin) AdminFondo else ClienteFondo,surface=Color.White,onBackground=CampusInk,onSurface=CampusInk,
        surfaceVariant=if(admin) Color(0xFFEAF0F8) else Color(0xFFF6F1F3),onSurfaceVariant=if(admin) Color(0xFF4A607B) else Color(0xFF6C526C),outline=if(admin) Color(0xFF7489A4) else Color(0xFFA87F96),outlineVariant=if(admin) Color(0xFFD1DCEA) else Color(0xFFE3DADF),
        error=Color(0xFFAE302B),errorContainer=Color(0xFFFFE5E1),onErrorContainer=Color(0xFF7C2622)
    )
    val view=LocalView.current
    if(!view.isInEditMode) SideEffect { (view.context as? Activity)?.window?.let { w -> WindowCompat.getInsetsController(w,view).apply { isAppearanceLightStatusBars=!oscuro;isAppearanceLightNavigationBars=!oscuro } } }
    CompositionLocalProvider(LocalAdminCampus provides admin) { MaterialTheme(colorScheme=esquema,typography=Typography,shapes=Shapes(extraSmall=RoundedCornerShape(8.dp),small=RoundedCornerShape(12.dp),medium=RoundedCornerShape(16.dp),large=RoundedCornerShape(22.dp),extraLarge=RoundedCornerShape(28.dp)),content=content) }
}
