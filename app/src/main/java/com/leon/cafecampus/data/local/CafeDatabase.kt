package com.leon.cafecampus.data.local
import android.content.Context
import androidx.room.*
import com.leon.cafecampus.data.local.entity.Producto
import com.leon.cafecampus.data.local.dao.ProductoDao
@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    companion object {
        @Volatile private var INSTANCE: CafeDatabase? = null
        fun obtener(context: Context): CafeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, CafeDatabase::class.java, "cafecampus.db").build().also { INSTANCE = it }
        }
    }
}
