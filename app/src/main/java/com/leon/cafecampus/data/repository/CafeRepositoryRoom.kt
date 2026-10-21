package com.leon.cafecampus.data.repository
import com.leon.cafecampus.data.local.CafeDatabase
import com.leon.cafecampus.data.local.entity.Producto
class CafeRepositoryRoom(private val db: CafeDatabase) : CafeRepository {
    override fun observarProductos() = db.productoDao().observarTodos()
    override suspend fun guardarProducto(p: Producto) { if(p.id == 0) db.productoDao().insertar(p) else db.productoDao().actualizar(p) }
    override suspend fun eliminarProducto(p: Producto) = db.productoDao().eliminar(p)
    override suspend fun cambiarDisponibilidad(id: Int, disponible: Boolean) = db.productoDao().cambiarDisponibilidad(id, disponible)
}
