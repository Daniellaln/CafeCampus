package com.leon.cafecampus.data.repository
import androidx.room.withTransaction
import com.leon.cafecampus.data.local.CafeDatabase
import com.leon.cafecampus.data.local.entity.*
import com.leon.cafecampus.model.*
import java.math.BigDecimal
import java.math.RoundingMode
class CafeRepositoryRoom(private val db: CafeDatabase) : CafeRepository {
    override suspend fun login(correo: String,clave: String) = db.usuarioDao().login(correo.trim(),clave)
    override fun observarProductos() = db.productoDao().observarTodos()
    override suspend fun guardarProducto(p: Producto) {
        require(p.nombre.isNotBlank() && p.precio.isFinite() && p.precio>0) { "Nombre y precio válidos son obligatorios" }
        if(p.id==0) db.productoDao().insertar(p) else db.productoDao().actualizar(p)
    }
    override suspend fun eliminarProducto(p: Producto) = db.productoDao().eliminar(p)
    override suspend fun cambiarDisponibilidad(id: Int,disponible: Boolean) = db.productoDao().cambiarDisponibilidad(id,disponible)
    override suspend fun crearPedido(clienteId: Int,items: List<ItemCarrito>,horaRecojo: String,pago: String,nota: String): Long = db.withTransaction {
        require(items.isNotEmpty()) { "Agrega productos al pedido" }
        require(horaRecojo.isNotBlank() && pago in listOf("CAJA","YAPE")) { "Selecciona hora y forma de pago" }
        val detalles=items.map { item ->
            require(item.cantidad in 1..99) { "La cantidad debe estar entre 1 y 99" }
            val p=db.productoDao().buscar(item.producto.id)
            require(p!=null && p.disponible) { "${item.producto.nombre} ya no está disponible" }
            DetallePedido(productoId=p.id,nombreProducto=p.nombre,cantidad=item.cantidad,precioUnitario=p.precio)
        }
        val total=detalles.fold(BigDecimal.ZERO) { suma,d -> suma+BigDecimal.valueOf(d.precioUnitario).multiply(BigDecimal.valueOf(d.cantidad.toLong())) }.setScale(2,RoundingMode.HALF_UP).toDouble()
        val notas=(listOf(nota.trim())+items.filter { it.nota.isNotBlank() }.map { "${it.producto.nombre}: ${it.nota.trim()}" }).filter { it.isNotBlank() }.joinToString(" · ")
        db.pedidoDao().crearPedido(Pedido(clienteId=clienteId,horaRecojo=horaRecojo,pago=pago,nota=notas,total=total),detalles)
    }
    override fun observarPedidosCliente(clienteId: Int) = db.pedidoDao().observarPorCliente(clienteId)
    override fun observarPedidosPorEstado(estado: EstadoPedido) = db.pedidoDao().observarPorEstado(estado.name)
    override fun observarPedido(id: Int) = db.pedidoDao().observarUno(id)
    override suspend fun cambiarEstado(pedidoId: Int,estado: EstadoPedido) = db.pedidoDao().cambiarEstado(pedidoId,estado.name)
    override fun observarVentasHoy(inicioDia: Long) = db.pedidoDao().ventasDesde(inicioDia)
    override fun observarCantidadPorEstado(estado: EstadoPedido) = db.pedidoDao().cantidadPorEstado(estado.name)
}
