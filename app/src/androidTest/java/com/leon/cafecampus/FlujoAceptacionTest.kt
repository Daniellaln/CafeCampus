package com.leon.cafecampus
import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.*
import org.junit.runner.RunWith
import java.io.File
@RunWith(AndroidJUnit4::class)
class FlujoAceptacionTest {
    @get:Rule val ui=createAndroidComposeRule<MainActivity>()
    private fun esperar(t: String) { android.util.Log.i("CafePrueba","Buscando: $t"); ui.waitUntil(15000) { ui.onAllNodesWithText(t).fetchSemanticsNodes().isNotEmpty() };ui.waitForIdle() }
    private fun click(t: String) { esperar(t);(if(t=="Confirmar pedido") ui.onAllNodesWithText(t).onLast() else ui.onAllNodesWithText(t).onFirst()).performClick();ui.waitForIdle() }
    private fun foto(nombre: String) {
        ui.waitForIdle()
        Thread.sleep(900) // Espera el fotograma nativo después de la transición.
        val instrument=InstrumentationRegistry.getInstrumentation()
        val dir=File(InstrumentationRegistry.getArguments().getString("additionalTestOutputDir") ?: File(instrument.targetContext.getExternalFilesDir(null),"capturas").absolutePath).apply { mkdirs() }
        instrument.uiAutomation.takeScreenshot().let { bmp -> File(dir,"$nombre.png").outputStream().use { bmp.compress(Bitmap.CompressFormat.PNG,100,it) };bmp.recycle() }
    }
    private fun campo(etiqueta: String,valor: String) { runCatching { ui.onNodeWithText(etiqueta).performScrollTo() };(if(etiqueta=="Buscar en el menú") ui.onNodeWithContentDescription(etiqueta) else ui.onNodeWithText(etiqueta)).performTextReplacement(valor);ui.activity.runOnUiThread { (ui.activity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager).hideSoftInputFromWindow(ui.activity.window.decorView.windowToken,0) };ui.waitForIdle() }
    private fun subir(t: String) { val n=ui.onAllNodesWithText(t).onLast();runCatching { n.assertIsDisplayed() }.onFailure { n.performScrollTo() };ui.waitForIdle() }
    private fun producto(nombre: String) { ui.onNode(hasScrollToNodeAction()).performScrollToNode(hasContentDescription("Ver $nombre"));ui.onNodeWithContentDescription("Ver $nombre").performClick();ui.waitForIdle() }
    @Test fun faseUnoCompleta() {
        esperar("Comenzar");foto("00_bienvenida");click("Comenzar");esperar("Bienvenido");ui.onNodeWithContentDescription("Volver").performClick();esperar("Comenzar");click("Comenzar");esperar("Bienvenido");foto("01_login_cliente");click("Administrador");foto("01b_login_admin");click("Estudiante")
        campo("Correo","daniella@campus.test");campo("Contraseña","mala");click("Ingresar")
        esperar("Correo o contraseña incorrectos")
        campo("Contraseña","1234");click("Ingresar");esperar("¡Hola, Daniella!");esperar("6 opciones");foto("02_menu_cliente")
        producto("Café americano")
        esperar("Detalle del producto");foto("03_detalle_producto")
        ui.onNodeWithContentDescription("Aumentar cantidad").performClick()
        subir("Agregar al pedido");click("Agregar al pedido")
        esperar("¡Hola, Daniella!")
        producto("Chicha morada")
        subir("Agregar al pedido");click("Agregar al pedido")
        // El carrito debe sobrevivir a la recreación de Activity.
        ui.activityRule.scenario.recreate();esperar("¡Hola, Daniella!")
        ui.onNodeWithContentDescription("Mi bolsa").performClick();esperar("Confirmar pedido");foto("04_confirmar_pedido")
        subir("Confirmar pedido");click("Confirmar pedido");esperar("Seguimiento");foto("05_seguimiento")
        click("Pedidos");esperar("Mis pedidos");foto("06_mis_pedidos");click("Historial");foto("06b_estado_vacio");click("Activos")
        click("Perfil");esperar("Mi perfil");subir("Cambiar de perfil");click("Cambiar de perfil")
        esperar("Panel de control · Hoy");ui.waitUntil(10000) { ui.onAllNodesWithText("S/ 11.50").fetchSemanticsNodes().size>=2 };foto("07_panel_admin")
        click("Pedidos");esperar("Nuevos");foto("08_pedidos_admin")
        click("Pasar a Preparando");click("Preparando")
        click("Pedido #0001");esperar("Detalle del pedido");esperar("Cliente");foto("09_detalle_pedido_admin")
        ui.onNodeWithContentDescription("Volver").performClick()
        click("Menú");esperar("Gestión del menú");foto("10_menu_admin")
        ui.onNodeWithContentDescription("Editar Café americano").performClick();esperar("Editar producto");foto("11_formulario_producto")
        campo("Precio (S/)","7.00");subir("Guardar producto");click("Guardar producto");esperar("Gestión del menú")
        // Cambia a agotado y comprueba que el cliente no pueda agregarlo.
        ui.onAllNodesWithText("Disponible").onFirst().performClick()
        ui.onNodeWithContentDescription("Agregar producto").performClick();esperar("Nuevo producto")
        foto("11b_nuevo_producto");campo("Nombre del producto","Tostada de prueba");campo("Precio (S/)","6.00");subir("Guardar producto");click("Guardar producto");esperar("Gestión del menú")
        click("Ajustes");esperar("Recibir pedidos");foto("12_ajustes_admin")
        // Modo oscuro y cierre de cafetería persisten al recrear.
        ui.onAllNodes(isToggleable()).onFirst().performClick()
        ui.onAllNodes(isToggleable()).onLast().performClick()
        ui.activityRule.scenario.recreate();esperar("Recibir pedidos")
        click("Ajustes");foto("13_modo_oscuro")
        subir("Cambiar de perfil");click("Cambiar de perfil");esperar("¡Hola, Daniella!");esperar("Cafetería cerrada")
        producto("Café americano")
        subir("Agregar al pedido");ui.onNodeWithText("Agregar al pedido").assertIsNotEnabled()
        ui.onNodeWithContentDescription("Volver").performClick()
        ui.onNodeWithContentDescription("Elegir categoría").performClick();esperar("Elige tu pausa");ui.onNodeWithContentDescription("Filtrar por Snacks").performClick()
        ui.waitUntil(10000) { ui.onAllNodes(hasScrollToNodeAction()).fetchSemanticsNodes().isNotEmpty() }
        ui.onNode(hasScrollToNodeAction()).performScrollToNode(hasText("Tostada de prueba"));esperar("Tostada de prueba")
        click("Pedidos");click("Pedido #0001");esperar("Seguimiento")
        ui.onNodeWithText("Preparando").assertExists();foto("14_seguimiento_actualizado")
        click("Perfil");subir("Cambiar de perfil");click("Cambiar de perfil");esperar("Panel de control · Hoy")
        click("Pedidos");click("Preparando");click("Cancelar")
        click("Ajustes")
        ui.onAllNodes(isToggleable()).onFirst().performClick()
        ui.onAllNodes(isToggleable()).onLast().performClick()
        subir("Cambiar de perfil");click("Cambiar de perfil");esperar("¡Hola, Daniella!")
        click("Pedidos");click("Historial");esperar("Cancelado");foto("15_historial_cancelado")
        click("Repetir pedido");esperar("Confirmar pedido");esperar("Se omitieron productos que ya no están disponibles");foto("16_repetir_pedido")
        ui.onNodeWithText("Total a pagar").assertExists()
        click("Confirmar pedido");esperar("Seguimiento")
        click("Perfil");esperar("Mi perfil");subir("Cambiar de perfil");click("Cambiar de perfil");esperar("Panel de control · Hoy");foto("19_grafico_actualizado");click("Ajustes");subir("Cambiar de perfil");click("Cambiar de perfil");esperar("¡Hola, Daniella!")
        ui.onNodeWithContentDescription("Elegir categoría").performClick();esperar("Elige tu pausa");ui.onNodeWithContentDescription("Filtrar por Todo").performClick()
        ui.onNodeWithContentDescription("Buscar productos").performClick();esperar("Buscar en el menú")
        campo("Buscar en el menú","CAFE");esperar("Resultados");ui.onNodeWithContentDescription("Ver Café americano").assertExists();foto("17_busqueda")
        ui.onNodeWithContentDescription("Limpiar búsqueda").performClick()
        click("Perfil");esperar("Mi perfil");foto("18_perfil")
        // Direct manipulation must navigate, not just play a decorative animation.
        ui.onNodeWithTag("navegacion_liquida").performTouchInput { swipe(start=androidx.compose.ui.geometry.Offset(width*.84f,height*.5f),end=androidx.compose.ui.geometry.Offset(width*.12f,height*.5f),durationMillis=380) }
        esperar("¡Hola, Daniella!")
        ui.onNodeWithContentDescription("Cerrar búsqueda").performClick();esperar("7 opciones")
        ui.onNodeWithContentDescription("Elegir categoría").performClick();esperar("Elige tu pausa");foto("20_categorias")
    }
}
