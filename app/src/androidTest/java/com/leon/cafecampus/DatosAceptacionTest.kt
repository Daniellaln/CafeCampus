package com.leon.cafecampus
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.room.Room
import com.leon.cafecampus.data.local.*
import com.leon.cafecampus.data.local.entity.*
import com.leon.cafecampus.data.repository.*
import com.leon.cafecampus.model.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class DatosAceptacionTest {
    private lateinit var db: CafeDatabase
    private lateinit var repo: CafeRepository
    @Before fun abrir() { db=Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,CafeDatabase::class.java).build();repo=CafeRepositoryRoom(db);runBlocking { DatosIniciales.sembrar(db) } }
    @After fun cerrar() { db.close() }
    private suspend fun items() = repo.observarProductos().first().take(2).map { ItemCarrito(it,2,"Sin ají") }
    @Test fun semillaIdempotenteYLogin() = runBlocking {
        DatosIniciales.sembrar(db)
        assertEquals(6,db.productoDao().contar());assertEquals(2,db.usuarioDao().contar())
        assertNull(repo.login("daniella@campus.test","incorrecta"))
        assertEquals("CLIENTE",repo.login("daniella@campus.test","1234")?.rol)
        assertEquals("ADMIN",repo.login("admin@cafe.test","admin")?.rol)
        val id=repo.login("daniella@campus.test","1234")!!.id
        db.openHelper.writableDatabase.execSQL("UPDATE usuarios SET nombre = 'Juan Pérez', correo = 'juan@campus.test' WHERE id = ?",arrayOf(id))
        DatosIniciales.sembrar(db)
        assertEquals(id,repo.login("daniella@campus.test","1234")!!.id)
        assertEquals("Daniella",repo.login("daniella@campus.test","1234")!!.nombre)
        assertEquals(2,db.usuarioDao().contar())
    }
    @Test fun pedidoConDosProductosYRelacion() = runBlocking {
        val items=items();val id=repo.crearPedido(1,items,"10:30","CAJA","Gracias").toInt()
        val p=repo.observarPedido(id).first()!!
        assertEquals("RECIBIDO",p.pedido.estado);assertEquals(2,p.detalles.size);assertEquals("Daniella",p.cliente.nombre)
        assertEquals(items.sumOf { it.subtotal },p.pedido.total,0.001)
        assertTrue(p.pedido.nota.contains("Sin ají"))
    }
    @Test fun flujoReflejaEstadoYSumaExcluyeCancelados() = runBlocking {
        assertEquals(0.0,repo.observarVentasHoy(0).first(),0.001)
        val a=repo.crearPedido(1,items(),"Ahora","YAPE","").toInt()
        val b=repo.crearPedido(1,items(),"Ahora","CAJA","").toInt()
        repo.cambiarEstado(a,EstadoPedido.PREPARANDO)
        assertEquals("PREPARANDO",repo.observarPedidosCliente(1).first().find { it.pedido.id==a }!!.pedido.estado)
        repo.cambiarEstado(b,EstadoPedido.CANCELADO)
        assertEquals(1,repo.observarCantidadPorEstado(EstadoPedido.PREPARANDO).first())
        assertEquals(repo.observarPedido(a).first()!!.pedido.total,repo.observarVentasHoy(0).first(),0.001)
    }
    @Test fun precioHistoricoTrasEditarYEliminarProducto() = runBlocking {
        val p=repo.observarProductos().first().first()
        val id=repo.crearPedido(1,listOf(ItemCarrito(p,2)),"Ahora","CAJA","").toInt()
        repo.guardarProducto(p.copy(nombre="Nombre cambiado",precio=99.0));repo.eliminarProducto(p)
        val historico=repo.observarPedido(id).first()!!
        assertEquals(p.nombre,historico.detalles.single().nombreProducto)
        assertEquals(p.precio,historico.detalles.single().precioUnitario,0.001)
        assertEquals(p.precio*2,historico.pedido.total,0.001)
    }
    @Test fun agotadoNoCreaPedido() = runBlocking {
        val items=items();repo.cambiarDisponibilidad(items.first().producto.id,false)
        try { repo.crearPedido(1,items,"Ahora","CAJA","");fail("Debió rechazar el producto agotado") } catch(e: IllegalArgumentException) { assertTrue(e.message!!.contains("disponible")) }
        assertTrue(repo.observarPedidosCliente(1).first().isEmpty())
    }
    @Test fun detallesInvalidosHacenRollbackCompleto() = runBlocking {
        try {
            db.pedidoDao().crearPedido(Pedido(clienteId=1,horaRecojo="Ahora",pago="CAJA",nota="",total=1.0),listOf(DetallePedido(id=8,productoId=1,nombreProducto="Café",cantidad=1,precioUnitario=1.0),DetallePedido(id=8,productoId=1,nombreProducto="Café",cantidad=1,precioUnitario=1.0)))
            fail("Debió fallar por clave duplicada")
        } catch(e: android.database.sqlite.SQLiteConstraintException) { assertTrue(db.pedidoDao().observarPorCliente(1).first().isEmpty()) }
    }
    @Test fun eliminarPedidoBorraSusDetalles() = runBlocking {
        val id=repo.crearPedido(1,items(),"Ahora","CAJA","")
        db.openHelper.writableDatabase.execSQL("DELETE FROM pedidos WHERE id = ?",arrayOf(id))
        db.openHelper.readableDatabase.query("SELECT COUNT(*) FROM detalle_pedido").use { c -> c.moveToFirst();assertEquals(0,c.getInt(0)) }
    }
    @Test fun ordenDescendenteYDatosPersistidos() = runBlocking {
        val ids=(1..2).map { repo.crearPedido(1,items(),"Ahora","CAJA","").toInt().also { id -> db.openHelper.writableDatabase.execSQL("UPDATE pedidos SET fecha = ? WHERE id = ?",arrayOf(it*1000,id)) } }
        assertEquals(ids.reversed(),repo.observarPedidosCliente(1).first().map { it.pedido.id })
    }
    @Test fun graficoAgrupaHoraYRespetaLimitesYCancelados() = runBlocking {
        val inicio=java.util.Calendar.getInstance().apply { set(2026,8,17,0,0,0);set(java.util.Calendar.MILLISECOND,0) }.timeInMillis
        val fin=inicio+24*60*60*1000
        suspend fun pedido(fecha: Long,cancelado: Boolean=false) {
            val id=repo.crearPedido(1,items(),"Ahora","CAJA","").toInt()
            db.openHelper.writableDatabase.execSQL("UPDATE pedidos SET fecha = ? WHERE id = ?",arrayOf(fecha,id))
            if(cancelado) repo.cambiarEstado(id,EstadoPedido.CANCELADO)
        }
        pedido(inicio);pedido(inicio+9*3600000);pedido(inicio+9*3600000+1000)
        pedido(inicio+9*3600000,true);pedido(inicio-1);pedido(fin)
        val datos=repo.observarPedidosPorHora(inicio,fin).first()
        assertEquals(listOf(PedidosPorHora(0,1),PedidosPorHora(9,2)),datos)
    }

    @Test fun busquedaIgnoraTildesYCombinaCategoria() = runBlocking {
        lateinit var vm: com.leon.cafecampus.viewmodel.MenuViewModel
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm=com.leon.cafecampus.viewmodel.MenuViewModel(repo,androidx.lifecycle.SavedStateHandle());vm.buscar("CAFE") }
        val resultado=kotlinx.coroutines.withTimeout(5000) { vm.filtrados.first { it.isNotEmpty() } }
        assertEquals(listOf("Café americano"),resultado.map { it.nombre })
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm.categoria("Snacks") }
        kotlinx.coroutines.withTimeout(5000) { vm.filtrados.first { it.isEmpty() } }
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm.buscar("") }
        val categoria=kotlinx.coroutines.withTimeout(5000) { vm.filtrados.first { it.isNotEmpty() } }
        assertTrue(categoria.all { it.categoria=="Snacks" })
    }
    @Test fun repetirUsaPrecioActualYOmitidosSinCrearCompra() = runBlocking {
        val ps=repo.observarProductos().first().take(2)
        val id=repo.crearPedido(1,ps.map { ItemCarrito(it,2) },"Ahora","CAJA","").toInt()
        val anterior=repo.observarPedido(id).first()!!
        repo.guardarProducto(ps[0].copy(precio=23.0));repo.cambiarDisponibilidad(ps[1].id,false)
        lateinit var vm: com.leon.cafecampus.viewmodel.MenuViewModel
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm=com.leon.cafecampus.viewmodel.MenuViewModel(repo,androidx.lifecycle.SavedStateHandle());vm.repetir(anterior) }
        kotlinx.coroutines.withTimeout(5000) { vm.repetido.first { it } }
        assertEquals(1,vm.carrito.value.size);assertEquals(46.0,vm.total(),.001)
        assertTrue(vm.error.value!!.contains("omitieron"))
        assertEquals(1,repo.observarPedidosCliente(1).first().size)
        repo.cambiarDisponibilidad(ps[0].id,false)
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm.consumirRepetido();vm.repetir(anterior) }
        kotlinx.coroutines.withTimeout(5000) { vm.repitiendo.first { !it } }
        assertFalse(vm.repetido.value);assertTrue(vm.error.value!!.contains("ya no están disponibles"))
    }

    @Test fun cargaNoSeConfundeConCatalogoVacio() = runBlocking {
        val original=repo
        val lento=object: CafeRepository by original {
            override fun observarProductos()=kotlinx.coroutines.flow.flow { kotlinx.coroutines.delay(250);emit(original.observarProductos().first()) }
        }
        lateinit var vm: com.leon.cafecampus.viewmodel.MenuViewModel
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm=com.leon.cafecampus.viewmodel.MenuViewModel(lento,androidx.lifecycle.SavedStateHandle()) }
        assertTrue(vm.estadoMenu.value.cargando)
        val listo=kotlinx.coroutines.withTimeout(5000) { vm.estadoMenu.first { !it.cargando } }
        assertEquals(6,listo.productos.size)
        InstrumentationRegistry.getInstrumentation().runOnMainSync { vm.buscar("producto inexistente") }
        val vacio=kotlinx.coroutines.withTimeout(5000) { vm.estadoMenu.first { !it.cargando && it.productos.isEmpty() } }
        assertFalse(vacio.cargando)
    }
    @Test fun favoritosPersistenSeparadosPorUsuario() = runBlocking {
        val contexto=InstrumentationRegistry.getInstrumentation().targetContext
        val prefs=com.leon.cafecampus.data.prefs.PreferenciasStore(contexto)
        if(7 in prefs.favoritos(991001).first()) prefs.alternarFavorito(991001,7)
        prefs.alternarFavorito(991001,7)
        assertEquals(setOf(7),prefs.favoritos(991001).first())
        assertFalse(7 in prefs.favoritos(991002).first())
        val otraInstancia=com.leon.cafecampus.data.prefs.PreferenciasStore(contexto)
        assertEquals(setOf(7),otraInstancia.favoritos(991001).first())
        otraInstancia.alternarFavorito(991001,7)
        assertTrue(prefs.favoritos(991001).first().isEmpty())
    }

    @Test fun horariosFuturosIncluyenDiaAlCruzarMedianoche() {
        val reloj=java.util.Calendar.getInstance().apply { set(2026,9,21,12,31,0);set(java.util.Calendar.MILLISECOND,0) }
        assertEquals(listOf("Ahora","Hoy, 13:00","Hoy, 13:30","Hoy, 14:00"),opcionesRecojo(reloj.timeInMillis))
        reloj.set(2026,11,31,23,50,0)
        assertEquals(listOf("Ahora","Mañana, 00:30","Mañana, 01:00","Mañana, 01:30"),opcionesRecojo(reloj.timeInMillis))
    }
    @Test fun registroNormalizaCorreoYRechazaDuplicados() = runBlocking {
        val u=repo.registrarEstudiante("Daniella Nueva","  NUEVA@CAMPUS.TEST  ","123456")
        assertEquals("nueva@campus.test",u.correo);assertEquals("CLIENTE",u.rol)
        assertEquals(u.id,repo.login("NUEVA@CAMPUS.TEST","123456")!!.id)
        assertEquals(u,repo.obtenerUsuario(u.id))
        try { repo.registrarEstudiante("Otra","Nueva@campus.test","abcdef");fail("Correo duplicado") } catch(e: IllegalArgumentException) { assertTrue(e.message!!.contains("registrado")) }
        try { repo.registrarEstudiante("Daniella","invalido","123456");fail("Correo inválido") } catch(e: IllegalArgumentException) { assertTrue(e.message!!.contains("correo")) }
        assertEquals(3,db.usuarioDao().contar())
    }
    @Test fun reporteUsaUnidadesPreciosHistoricosYPagosDelDia() = runBlocking {
        val productos=repo.observarProductos().first();val cafe=productos.first { it.icono=="cafe" };val chicha=productos.first { it.icono=="botella" }
        val inicio=java.util.Calendar.getInstance().apply { set(java.util.Calendar.HOUR_OF_DAY,0);set(java.util.Calendar.MINUTE,0);set(java.util.Calendar.SECOND,0);set(java.util.Calendar.MILLISECOND,0) }.timeInMillis
        val fin=inicio+86400000L
        repo.crearPedido(1,listOf(ItemCarrito(cafe,2)),"Ahora","CAJA","")
        repo.crearPedido(1,listOf(ItemCarrito(cafe,1),ItemCarrito(chicha,4)),"Ahora","YAPE","")
        val cancelado=repo.crearPedido(1,listOf(ItemCarrito(cafe,99)),"Ahora","YAPE","").toInt();repo.cambiarEstado(cancelado,EstadoPedido.CANCELADO)
        val anterior=repo.crearPedido(1,listOf(ItemCarrito(cafe,99)),"Ahora","CAJA","")
        db.openHelper.writableDatabase.execSQL("UPDATE pedidos SET fecha=? WHERE id=?",arrayOf(inicio-1,anterior))
        repo.guardarProducto(cafe.copy(precio=99.0))
        val ranking=repo.observarReporteProductos(inicio,fin).first();val pagos=repo.observarReportePagos(inicio,fin).first()
        assertEquals(chicha.id,ranking.first().productoId);assertEquals(4,ranking.first().unidades)
        assertEquals(8.0,pagos.first { it.pago=="CAJA" }.total,0.001);assertEquals(18.0,pagos.first { it.pago=="YAPE" }.total,0.001)
        assertEquals(2,pagos.sumOf { it.cantidad });assertEquals(26.0,pagos.sumOf { it.total },0.001)
        assertTrue(repo.observarReportePagos(fin,fin+86400000L).first().isEmpty())
    }
}
