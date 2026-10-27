package com.leon.cafecampus.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.leon.cafecampus.R
val CampusFont=FontFamily(Font(R.font.manrope_regular,FontWeight.Normal),Font(R.font.manrope_semibold,FontWeight.SemiBold),Font(R.font.manrope_bold,FontWeight.Bold))
private fun tipo(size: Int,line: Int,weight: FontWeight=FontWeight.Normal)=TextStyle(fontFamily=CampusFont,fontSize=size.sp,lineHeight=line.sp,fontWeight=weight,letterSpacing=0.sp)
val Typography=Typography(
    displayLarge=tipo(48,54,FontWeight.Bold),displayMedium=tipo(42,48,FontWeight.Bold),displaySmall=tipo(36,42,FontWeight.Bold),
    headlineLarge=tipo(32,39,FontWeight.Bold),headlineMedium=tipo(28,35,FontWeight.Bold),headlineSmall=tipo(24,31,FontWeight.Bold),
    titleLarge=tipo(21,28,FontWeight.Bold),titleMedium=tipo(16,23,FontWeight.Bold),titleSmall=tipo(14,20,FontWeight.SemiBold),
    bodyLarge=tipo(16,25),bodyMedium=tipo(14,22),bodySmall=tipo(12,18),
    labelLarge=tipo(14,20,FontWeight.Bold),labelMedium=tipo(12,18,FontWeight.SemiBold),labelSmall=tipo(11,16,FontWeight.SemiBold)
)
