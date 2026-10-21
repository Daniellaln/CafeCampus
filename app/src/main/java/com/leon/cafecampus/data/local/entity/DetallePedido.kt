package com.leon.cafecampus.data.local.entity
import androidx.room.*
@Entity(tableName="detalle_pedido",foreignKeys=[ForeignKey(entity=Pedido::class,parentColumns=["id"],childColumns=["pedidoId"],onDelete=ForeignKey.CASCADE)],indices=[Index("pedidoId")])
data class DetallePedido(@PrimaryKey(autoGenerate=true) val id: Int=0, val pedidoId: Int=0,
    val productoId: Int, val nombreProducto: String, val cantidad: Int, val precioUnitario: Double)
