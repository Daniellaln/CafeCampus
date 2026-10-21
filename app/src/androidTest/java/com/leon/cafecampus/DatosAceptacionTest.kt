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
}
