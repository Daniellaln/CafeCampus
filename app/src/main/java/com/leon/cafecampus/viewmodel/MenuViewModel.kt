package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
class MenuViewModel(private val repo: CafeRepository) : ViewModel() {
    val productos = repo.observarProductos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _categoria = MutableStateFlow("Todo")
    val categoria = _categoria.asStateFlow()
    val filtrados = combine(productos, categoria) { ps, c -> ps.filter { c == "Todo" || it.categoria == c } }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun categoria(c: String) { _categoria.value = c }
}
