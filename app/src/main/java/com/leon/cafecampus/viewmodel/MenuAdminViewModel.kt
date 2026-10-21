package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.data.local.entity.Producto
class MenuAdminViewModel(private val repo: CafeRepository) : ViewModel() {
    val productos=repo.observarProductos().stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    private val _categoria=MutableStateFlow("Todo")
    val categoria=_categoria.asStateFlow()
    val filtrados=combine(productos,categoria) { ps,c -> ps.filter { c=="Todo" || it.categoria==c } }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    private val _error=MutableStateFlow<String?>(null)
    val error=_error.asStateFlow()
    private val _ocupado=MutableStateFlow(false)
    val ocupado=_ocupado.asStateFlow()
    private val _guardado=MutableStateFlow(false)
    val guardado=_guardado.asStateFlow()
    fun categoria(c: String) { _categoria.value=c }
    fun prepararFormulario() { _error.value=null; _guardado.value=false }
    private fun ejecutar(accion: suspend ()->Unit) {
        if(_ocupado.value) return
        _ocupado.value=true; _error.value=null
        viewModelScope.launch { try { accion() } catch(e: Exception) { _error.value=e.message ?: "No se pudo guardar el cambio" } finally { _ocupado.value=false } }
    }
    fun guardar(producto: Producto) {
        if(producto.nombre.isBlank()) { _error.value="Escribe el nombre del producto"; return }
        if(!producto.precio.isFinite() || producto.precio<=0) { _error.value="El precio debe ser mayor que 0"; return }
        ejecutar { repo.guardarProducto(producto.copy(nombre=producto.nombre.trim(),descripcion=producto.descripcion.trim())); _guardado.value=true }
    }
    fun eliminar(producto: Producto) = ejecutar { repo.eliminarProducto(producto) }
    fun alternarDisponibilidad(producto: Producto) = ejecutar { repo.cambiarDisponibilidad(producto.id,!producto.disponible) }
}
