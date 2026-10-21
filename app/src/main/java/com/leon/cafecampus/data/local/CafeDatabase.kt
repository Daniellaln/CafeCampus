package com.leon.cafecampus.data.local
import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leon.cafecampus.data.local.entity.*
import com.leon.cafecampus.data.local.dao.*
@Database(entities=[Producto::class,Usuario::class,Pedido::class,DetallePedido::class],version=2,exportSchema=false)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao
    companion object {
        val MIGRACION_1_2 = object: Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre TEXT NOT NULL, correo TEXT NOT NULL, clave TEXT NOT NULL, rol TEXT NOT NULL)")
                db.execSQL("CREATE TABLE IF NOT EXISTS pedidos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, clienteId INTEGER NOT NULL, estado TEXT NOT NULL, horaRecojo TEXT NOT NULL, pago TEXT NOT NULL, nota TEXT NOT NULL, total REAL NOT NULL, fecha INTEGER NOT NULL, FOREIGN KEY(clienteId) REFERENCES usuarios(id) ON UPDATE NO ACTION ON DELETE NO ACTION)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_pedidos_clienteId ON pedidos(clienteId)")
                db.execSQL("CREATE TABLE IF NOT EXISTS detalle_pedido (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, pedidoId INTEGER NOT NULL, productoId INTEGER NOT NULL, nombreProducto TEXT NOT NULL, cantidad INTEGER NOT NULL, precioUnitario REAL NOT NULL, FOREIGN KEY(pedidoId) REFERENCES pedidos(id) ON UPDATE NO ACTION ON DELETE CASCADE)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_detalle_pedido_pedidoId ON detalle_pedido(pedidoId)")
            }
        }
        @Volatile private var INSTANCE: CafeDatabase?=null
        fun obtener(context: Context): CafeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext,CafeDatabase::class.java,"cafecampus.db")
                .addMigrations(MIGRACION_1_2).build().also { INSTANCE=it }
        }
    }
}
