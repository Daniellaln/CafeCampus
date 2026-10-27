package com.leon.cafecampus.data.local.dao
import androidx.room.*
import com.leon.cafecampus.data.local.entity.Usuario
@Dao interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1") suspend fun login(correo: String, clave: String): Usuario?
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1") suspend fun buscar(id: Int): Usuario?
    @Query("SELECT EXISTS(SELECT 1 FROM usuarios WHERE correo = :correo COLLATE NOCASE)") suspend fun existeCorreo(correo: String): Boolean
    @Insert suspend fun insertar(u: Usuario): Long
    @Query("SELECT COUNT(*) FROM usuarios") suspend fun contar(): Int
}
