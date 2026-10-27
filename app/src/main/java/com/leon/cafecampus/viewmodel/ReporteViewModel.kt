package com.leon.cafecampus.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.model.ReporteDia
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.Calendar
class ReporteViewModel(repo: CafeRepository): ViewModel() {
    private val dias=flow {
        while(true) {
            val inicio=Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY,0);set(Calendar.MINUTE,0);set(Calendar.SECOND,0);set(Calendar.MILLISECOND,0) }
            val fin=(inicio.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR,1) }.timeInMillis
            emit(inicio.timeInMillis to fin)
            delay((fin-System.currentTimeMillis()).coerceAtLeast(1000))
        }
    }.distinctUntilChanged()
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val estado=dias.flatMapLatest { (inicio,fin) ->
        combine(repo.observarReporteProductos(inicio,fin),repo.observarReportePagos(inicio,fin)) { productos,pagos -> ReporteDia(false,inicio,productos,pagos) }
            .onStart { emit(ReporteDia(inicio=inicio)) }
            .catch { emit(ReporteDia(false,inicio,error="No se pudo cargar el reporte")) }
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),ReporteDia())
}
