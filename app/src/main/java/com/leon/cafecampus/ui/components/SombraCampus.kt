package com.leon.cafecampus.ui.components
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// The shadow bitmap is transparent INSIDE the shape. Native elevation was visible
// through the translucent fill and produced an unwanted rectangular centre.
fun Modifier.sombraCampus(radio: Dp=24.dp,oscuro: Boolean=false): Modifier = drawWithCache {
    val margen=30.dp.toPx().toInt()
    val bitmap=Bitmap.createBitmap((size.width+2*margen).toInt().coerceAtLeast(1),(size.height+2*margen).toInt().coerceAtLeast(1),Bitmap.Config.ARGB_8888)
    val canvas=android.graphics.Canvas(bitmap)
    val rect=RectF(margen.toFloat(),margen.toFloat(),margen+size.width,margen+size.height)
    val pintura=Paint(Paint.ANTI_ALIAS_FLAG)
    pintura.color=android.graphics.Color.BLACK
    pintura.setShadowLayer(11.dp.toPx(),6.dp.toPx(),8.dp.toPx(),if(oscuro) 0x78000000 else 0x426B7895)
    canvas.drawRoundRect(rect,radio.toPx(),radio.toPx(),pintura)
    pintura.setShadowLayer(8.dp.toPx(),(-5).dp.toPx(),(-5).dp.toPx(),if(oscuro) 0x163C5070 else 0xD9FFFFFF.toInt())
    canvas.drawRoundRect(rect,radio.toPx(),radio.toPx(),pintura)
    pintura.clearShadowLayer()
    pintura.xfermode=PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    canvas.drawRoundRect(rect,radio.toPx(),radio.toPx(),pintura)
    val imagen=bitmap.asImageBitmap()
    onDrawBehind { drawImage(imagen,topLeft=Offset(-margen.toFloat(),-margen.toFloat())) }
}
