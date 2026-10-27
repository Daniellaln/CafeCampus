package com.leon.cafecampus.model
data class ProductoReporte(val productoId: Int,val nombre: String,val unidades: Int,val total: Double,val icono: String?)
data class PagoReporte(val pago: String,val total: Double,val cantidad: Int)
data class ReporteDia(val cargando: Boolean=true,val inicio: Long=0,val productos: List<ProductoReporte> = emptyList(),val pagos: List<PagoReporte> = emptyList(),val error: String?=null) {
    val total get()=pagos.sumOf { it.total }
    val cantidad get()=pagos.sumOf { it.cantidad }
}
