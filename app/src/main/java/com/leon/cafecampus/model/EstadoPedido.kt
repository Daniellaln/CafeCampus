package com.leon.cafecampus.model
enum class EstadoPedido(val etiqueta: String) {
    RECIBIDO("Recibido"), PREPARANDO("Preparando"), LISTO("Listo"), ENTREGADO("Entregado"), CANCELADO("Cancelado");
    fun siguiente(): EstadoPedido? = when(this) {
        RECIBIDO -> PREPARANDO; PREPARANDO -> LISTO; LISTO -> ENTREGADO; else -> null
    }
    val activo: Boolean get() = this in listOf(RECIBIDO, PREPARANDO, LISTO)
}
