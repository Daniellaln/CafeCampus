package com.leon.cafecampus.data.local.dao
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.leon.cafecampus.data.local.entity.Producto
@Dao interface ProductoDao {
    @Query("SELECT * FROM productos ORDER BY nombre") fun observarTodos(): Flow<List<Producto>>
    @Query("SELECT COUNT(*) FROM productos") suspend fun contar(): Int
    @Query("SELECT * FROM productos WHERE id = :id") suspend fun buscar(id: Int): Producto?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertar(p: Producto): Long
    @Update suspend fun actualizar(p: Producto)
    @Delete suspend fun eliminar(p: Producto)
    @Query("UPDATE productos SET disponible = :d WHERE id = :id") suspend fun cambiarDisponibilidad(id: Int, d: Boolean)
}
