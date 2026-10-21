package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.data.local.relation.PedidoConDetalle
import com.leon.cafecampus.model.EstadoPedido
class PedidosAdminViewModel(private val repo: CafeRepository) : ViewModel() {
    val estadoSeleccionado=MutableStateFlow(EstadoPedido.RECIBIDO)
    @OptIn(ExperimentalCoroutinesApi::class)
    val pedidos=estadoSeleccionado.flatMapLatest(repo::observarPedidosPorEstado).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    private val _error=MutableStateFlow<String?>(null)
    val error=_error.asStateFlow()
    private val _ocupado=MutableStateFlow(false)
    val ocupado=_ocupado.asStateFlow()
    private val observados=mutableMapOf<Int,StateFlow<PedidoConDetalle?>>()
    fun pedido(id: Int) = observados.getOrPut(id) { repo.observarPedido(id).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),null) }
    fun siguiente(p: PedidoConDetalle) = EstadoPedido.valueOf(p.pedido.estado).siguiente()
    fun puedeCancelar(p: PedidoConDetalle) = EstadoPedido.valueOf(p.pedido.estado).activo
    fun puedeCambiar(p: PedidoConDetalle,e: EstadoPedido) = e==siguiente(p)
    fun avanzar(p: PedidoConDetalle) { siguiente(p)?.let { cambiar(p,it) } }
    fun cancelar(p: PedidoConDetalle) { if(puedeCancelar(p)) cambiar(p,EstadoPedido.CANCELADO) }
    fun cambiar(p: PedidoConDetalle,e: EstadoPedido) {
        if(_ocupado.value) return
        _ocupado.value=true; _error.value=null
        viewModelScope.launch {
            try {
                val actual=repo.observarPedido(p.pedido.id).first() ?: error("Pedido no encontrado")
                require(e==siguiente(actual) || (e==EstadoPedido.CANCELADO && puedeCancelar(actual))) { "El estado ya cambió. Revisa el pedido." }
                repo.cambiarEstado(actual.pedido.id,e)
            } catch(ex: Exception) { _error.value=ex.message ?: "No se pudo cambiar el estado" }
            finally { _ocupado.value=false }
        }
    }
}
