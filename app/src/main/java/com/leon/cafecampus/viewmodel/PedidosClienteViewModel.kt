package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.data.prefs.PreferenciasStore
import com.leon.cafecampus.data.local.relation.PedidoConDetalle
import com.leon.cafecampus.model.*
class PedidosClienteViewModel(private val repo: CafeRepository, private val clienteId: Int,private val prefs: PreferenciasStore) : ViewModel() {
    val misPedidos=repo.observarPedidosCliente(clienteId).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    private val observados=mutableMapOf<Int,StateFlow<PedidoConDetalle?>>()
    fun pedido(id: Int): StateFlow<PedidoConDetalle?> = observados.getOrPut(id) { repo.observarPedido(id).map { it?.takeIf { p -> p.pedido.clienteId==clienteId } }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),null) }
    private val _error=MutableStateFlow<String?>(null)
    val error=_error.asStateFlow()
    private val _enviando=MutableStateFlow(false)
    val enviando=_enviando.asStateFlow()
    private val _creado=MutableStateFlow<Long?>(null)
    val creado=_creado.asStateFlow()
    private val _historial=MutableStateFlow(false)
    val historial=_historial.asStateFlow()
    val visibles=combine(misPedidos,historial) { ps,h -> ps.filter { EstadoPedido.valueOf(it.pedido.estado).activo != h } }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    fun historial(b: Boolean) { _historial.value=b }
    fun consumirCreado() { _creado.value=null }
    fun confirmar(items: List<ItemCarrito>,horaRecojo: String,pago: String,nota: String) {
        if(_enviando.value || _creado.value!=null) return
        _enviando.value=true; _error.value=null
        viewModelScope.launch {
            try {
                require(prefs.recibirPedidos.first()) { "Cafetería cerrada. No se reciben pedidos en este momento." }
                _creado.value=repo.crearPedido(clienteId,items,horaRecojo,pago,nota)
            } catch(e: Exception) { _error.value=e.message ?: "No se pudo confirmar el pedido" }
            finally { _enviando.value=false }
        }
    }
}
