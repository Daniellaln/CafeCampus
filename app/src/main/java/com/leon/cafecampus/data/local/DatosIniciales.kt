package com.leon.cafecampus.data.local
import androidx.room.withTransaction
import com.leon.cafecampus.data.local.entity.Producto
object DatosIniciales {
    suspend fun sembrar(db: CafeDatabase) = db.withTransaction {
        if (db.productoDao().contar() == 0) productos().forEach { db.productoDao().insertar(it) }
    }
    fun productos() = listOf(
    Producto(nombre="Café americano", descripcion="Café recién preparado, intenso y aromático.", categoria="Bebidas", precio=4.0, icono="cafe"),
    Producto(nombre="Sándwich de pollo", descripcion="Pan fresco con pollo deshilachado y verduras.", categoria="Desayunos", precio=9.0, icono="pan"),
    Producto(nombre="Menú del día", descripcion="Entrada + segundo + refresco. Cambia cada día según el menú de cocina.", categoria="Almuerzos", precio=12.0, icono="plato"),
    Producto(nombre="Jugo de naranja", descripcion="Jugo de naranja natural, preparado al momento.", categoria="Bebidas", precio=5.0, icono="jugo"),
    Producto(nombre="Empanada", descripcion="Empanada horneada rellena de carne.", categoria="Snacks", precio=4.5, icono="dulce"),
    Producto(nombre="Chicha morada", descripcion="Refresco de maíz morado con frutas y especias.", categoria="Bebidas", precio=3.5, icono="botella")
)
}
