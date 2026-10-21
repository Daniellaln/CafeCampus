package com.leon.cafecampus.data.repository
import com.leon.cafecampus.data.local.entity.Producto
import kotlinx.coroutines.flow.Flow
interface CafeRepository {
    fun observarProductos(): Flow<List<Producto>>
    suspend fun guardarProducto(p: Producto)
    suspend fun eliminarProducto(p: Producto)
    suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean)
}
