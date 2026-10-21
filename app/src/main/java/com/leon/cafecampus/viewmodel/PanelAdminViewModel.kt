package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.model.EstadoPedido
import java.util.Calendar
class PanelAdminViewModel(repo: CafeRepository) : ViewModel() {
    private val inicioDia=flow {
        while(true) {
            val dia=Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY,0);set(Calendar.MINUTE,0);set(Calendar.SECOND,0);set(Calendar.MILLISECOND,0) }
            emit(dia.timeInMillis)
            val manana=(dia.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR,1) }.timeInMillis
            delay((manana-System.currentTimeMillis()).coerceAtLeast(1000))
        }
    }.distinctUntilChanged()
    val nuevos=repo.observarCantidadPorEstado(EstadoPedido.RECIBIDO).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),0)
    val preparando=repo.observarCantidadPorEstado(EstadoPedido.PREPARANDO).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),0)
    val listos=repo.observarCantidadPorEstado(EstadoPedido.LISTO).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),0)
    @OptIn(ExperimentalCoroutinesApi::class)
    val ventasHoy=inicioDia.flatMapLatest(repo::observarVentasHoy).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),0.0)
    val recientes=repo.observarPedidosPorEstado(EstadoPedido.RECIBIDO).stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
}
