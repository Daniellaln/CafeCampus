package com.leon.cafecampus.ui.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
// Custom Android material. Geometry and light respond together; native text is outside this layer.
@Composable fun Modifier.lenteCampus(presion: Float=0f,punto: Offset=Offset.Zero): Modifier {
    if(Build.VERSION.SDK_INT<33) return this
    return this.then(lenteAndroid(presion,punto))
}
@RequiresApi(33)
@Composable private fun lenteAndroid(presion: Float,punto: Offset): Modifier {
    var tam by remember { mutableStateOf(IntSize(1,1)) }
    val shader=remember { android.graphics.RuntimeShader("""
        uniform shader contenido;
        uniform float2 tam;
        uniform float fuerza;
        uniform float2 toque;
        half4 main(float2 p) {
            float2 uv=p/tam;float2 centro=uv-float2(.5);
            float2 delta=p-toque;
            float radio=max(min(tam.x,tam.y)*.62,1.0);
            float onda=exp(-dot(delta,delta)/(radio*radio));
            float borde=pow(max(abs(centro.x*2.0),abs(centro.y*2.0)),6.0);
            float2 desvio=centro*borde*(10.0+fuerza*12.0)+delta*onda*fuerza*.09;
            float2 q=clamp(p-desvio,float2(0.0),tam-float2(1.0));
            half4 c=contenido.eval(q);
            float luz=pow(clamp(1.0-abs(uv.x+uv.y*.35-.18-fuerza*.11)*10.0,0.0,1.0),5.0)*(.12+.18*fuerza)*borde;
            float br=onda*fuerza*.08;
            return half4(min(c.rgb+half3((luz+br)*c.a),half3(c.a)),c.a);
        }
    """) }
    return Modifier.onSizeChanged { tam=it }.graphicsLayer {
        shader.setFloatUniform("tam",tam.width.coerceAtLeast(1).toFloat(),tam.height.coerceAtLeast(1).toFloat())
        shader.setFloatUniform("fuerza",presion)
        shader.setFloatUniform("toque",if(punto==Offset.Zero) tam.width*.5f else punto.x,if(punto==Offset.Zero) tam.height*.5f else punto.y)
        renderEffect=android.graphics.RenderEffect.createRuntimeShaderEffect(shader,"contenido").asComposeRenderEffect()
    }
}
