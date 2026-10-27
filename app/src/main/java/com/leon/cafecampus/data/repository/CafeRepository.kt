package com.leon.cafecampus.data.repository
import kotlinx.coroutines.flow.Flow
import com.leon.cafecampus.data.local.entity.*
import com.leon.cafecampus.data.local.relation.PedidoConDetalle
import com.leon.cafecampus.model.*
interface CafeRepository {
    suspend fun registrarEstudiante(nombre: String,correo: String,clave: String): Usuario
    suspend fun obtenerUsuario(id: Int): Usuario?
    suspend fun login(correo: String,clave: String): Usuario?
    fun observarProductos(): Flow<List<Producto>>
    suspend fun guardarProducto(p: Producto)
    suspend fun eliminarProducto(p: Producto)
    suspend fun cambiarDisponibilidad(id: Int,disponible: Boolean)
    suspend fun crearPedido(clienteId: Int,items: List<ItemCarrito>,horaRecojo: String,pago: String,nota: String): Long
    fun observarPedidosCliente(clienteId: Int): Flow<List<PedidoConDetalle>>
    fun observarPedidosPorEstado(estado: EstadoPedido): Flow<List<PedidoConDetalle>>
    fun observarPedido(id: Int): Flow<PedidoConDetalle?>
    suspend fun cambiarEstado(pedidoId: Int,estado: EstadoPedido)
    fun observarVentasHoy(inicioDia: Long): Flow<Double>
    fun observarPedidosPorHora(inicio: Long,fin: Long): Flow<List<PedidosPorHora>>
    fun observarCantidadPorEstado(estado: EstadoPedido): Flow<Int>
}
