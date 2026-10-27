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
@Composable fun CafeTheme(rol: Rol=Rol.CLIENTE,oscuro: Boolean=false,content: @Composable ()->Unit) {
    val admin=rol==Rol.ADMIN
    val esquema=if(oscuro) darkColorScheme(
        primary=Color(0xFFABC1FF),onPrimary=Color(0xFF102B60),primaryContainer=Color(0xFF263F68),onPrimaryContainer=Color(0xFFE6EDFF),
        secondary=Color(0xFFDFED99),onSecondary=Color(0xFF273312),secondaryContainer=Color(0xFF364725),onSecondaryContainer=Color(0xFFF0F7CF),
        tertiary=Color(0xFFF2C178),tertiaryContainer=Color(0xFF4A3823),onTertiaryContainer=Color(0xFFFFE7C4),
        background=Color(0xFF0F1B2D),surface=Color(0xFF19283D),onBackground=Color(0xFFF1F5FC),onSurface=Color(0xFFF1F5FC),
        surfaceVariant=Color(0xFF26364D),onSurfaceVariant=Color(0xFFB9C6D9),outline=Color(0xFF72819A),outlineVariant=Color(0xFF34455D),
        error=Color(0xFFFFB4AB),errorContainer=Color(0xFF5B2826),onErrorContainer=Color(0xFFFFDAD6)
    ) else lightColorScheme(
        primary=if(admin) AdminPrimario else ClientePrimario,onPrimary=Color.White,primaryContainer=Color(0xFFE7EDFF),onPrimaryContainer=Color(0xFF183978),
        secondary=Color(0xFF80627C),onSecondary=Color.White,secondaryContainer=Color(0xFFECDCE7),onSecondaryContainer=Color(0xFF5C3B53),
        tertiary=Color(0xFF7F5113),tertiaryContainer=Color(0xFFFFE6BB),onTertiaryContainer=Color(0xFF513006),
        background=if(admin) AdminFondo else ClienteFondo,surface=Color.White,onBackground=CampusInk,onSurface=CampusInk,
        surfaceVariant=Color(0xFFECF0F6),onSurfaceVariant=Color(0xFF586A82),outline=Color(0xFF7E8C9E),outlineVariant=Color(0xFFDFE5EC),
        error=Color(0xFFAE302B),errorContainer=Color(0xFFFFE5E1),onErrorContainer=Color(0xFF7C2622)
    )
    val view=LocalView.current
    if(!view.isInEditMode) SideEffect { (view.context as? Activity)?.window?.let { w -> WindowCompat.getInsetsController(w,view).apply { isAppearanceLightStatusBars=!oscuro;isAppearanceLightNavigationBars=!oscuro } } }
    MaterialTheme(colorScheme=esquema,typography=Typography,shapes=Shapes(extraSmall=RoundedCornerShape(8.dp),small=RoundedCornerShape(12.dp),medium=RoundedCornerShape(16.dp),large=RoundedCornerShape(22.dp),extraLarge=RoundedCornerShape(28.dp)),content=content)
}
