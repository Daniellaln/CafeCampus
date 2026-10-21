package com.leon.cafecampus.ui.components
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
@Composable fun ProductoIlustracion(icono: String,modifier: Modifier=Modifier) {
    val fondo=when(icono) { "cafe"->Color(0xFFE7D7C4); "pan"->Color(0xFFFFF3C3); "jugo"->Color(0xFFFFE5CC); else->Color(0xFFFFEDD5) }
    Surface(modifier=modifier,shape=RoundedCornerShape(12.dp),color=fondo) {
        Canvas(Modifier.fillMaxSize().padding(6.dp)) {
            val lado=size.minDimension
            withTransform({ translate((size.width-lado)/2,(size.height-lado)/2);scale(lado/100f,lado/100f,pivot=Offset.Zero) }) {
                when(icono) {
                    "plato" -> {
                        drawCircle(Color.White,43f,Offset(50f,50f))
                        drawCircle(Color(0xFFFFEDD5),32f,Offset(50f,50f))
                        drawCircle(Color(0xFFF59E0B),12f,Offset(42f,45f))
                        drawCircle(Color(0xFFEF4444),9f,Offset(60f,34f))
                        drawCircle(Color(0xFF16A34A),11f,Offset(63f,62f))
                    }
                    "cafe" -> {
                        drawOval(Color.White,Offset(70f,42f),Size(24f,28f))
                        drawOval(fondo,Offset(74f,47f),Size(14f,18f))
                        drawRoundRect(Color.White,Offset(17f,32f),Size(58f,42f),CornerRadius(7f))
                        drawRoundRect(Color(0xFF78461F),Offset(21f,36f),Size(50f,12f),CornerRadius(4f))
                        drawRoundRect(Color.White,Offset(10f,76f),Size(74f,6f),CornerRadius(3f))
                    }
                    "pan" -> {
                        drawRoundRect(Color.White,Offset(14f,32f),Size(72f,40f),CornerRadius(15f))
                        drawRoundRect(Color(0xFFF59E0B),Offset(17f,35f),Size(66f,13f),CornerRadius(9f))
                        drawRect(Color(0xFF16A34A),Offset(17f,48f),Size(66f,7f))
                        drawRect(Color(0xFFFBBF24),Offset(17f,55f),Size(66f,5f))
                        drawRoundRect(Color(0xFFF59E0B),Offset(17f,60f),Size(66f,10f),CornerRadius(5f))
                    }
                    "jugo" -> {
                        drawLine(Color(0xFF16A34A),Offset(58f,37f),Offset(71f,10f),5f)
                        val vaso=Path().apply { moveTo(28f,32f);lineTo(73f,32f);lineTo(64f,87f);lineTo(38f,87f);close() }
                        drawPath(vaso,Color.White)
                        val jugo=Path().apply { moveTo(32f,50f);lineTo(69f,50f);lineTo(63f,83f);lineTo(39f,83f);close() }
                        drawPath(jugo,Color(0xFFF97316))
                    }
                    "botella" -> {
                        drawRoundRect(Color(0xFF7E22CE),Offset(36f,29f),Size(30f,57f),CornerRadius(8f))
                        drawRect(Color(0xFF9333EA),Offset(43f,16f),Size(16f,21f))
                        drawRoundRect(Color(0xFF22C55E),Offset(41f,13f),Size(20f,7f),CornerRadius(2f))
                        drawRect(Color(0xFFFAE8FF),Offset(37f,49f),Size(28f,19f))
                    }
                    else -> {
                        drawRoundRect(Color.White,Offset(14f,33f),Size(74f,40f),CornerRadius(10f))
                        drawRoundRect(Color(0xFFB45309),Offset(19f,38f),Size(64f,29f),CornerRadius(8f))
                        listOf(31f,50f,69f).forEach { drawCircle(Color(0xFFFCD34D),3.5f,Offset(it,52f)) }
                    }
                }
            }
        }
    }
}
