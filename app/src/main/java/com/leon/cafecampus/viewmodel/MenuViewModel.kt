package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.data.local.entity.Producto
import com.leon.cafecampus.model.ItemCarrito
class MenuViewModel(private val repo: CafeRepository, private val saved: SavedStateHandle) : ViewModel() {
    val productos=repo.observarProductos().stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    private val _categoria=MutableStateFlow("Todo")
    val categoria=_categoria.asStateFlow()
    val filtrados=combine(productos,categoria) { ps,c -> ps.filter { c=="Todo" || it.categoria==c } }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
    private val _carrito=MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito=_carrito.asStateFlow()
    private val _error=MutableStateFlow<String?>(null)
    val error=_error.asStateFlow()
    init {
        // SavedStateHandle conserva también el carrito ante recreación del proceso.
        val ids=saved.get<IntArray>("ids") ?: intArrayOf()
        val cantidades=saved.get<IntArray>("cantidades") ?: intArrayOf()
        val notas=saved.get<ArrayList<String>>("notas") ?: arrayListOf()
        if(ids.isNotEmpty()) viewModelScope.launch {
            val ps=repo.observarProductos().first { it.isNotEmpty() }
            _carrito.value=ids.toList().mapIndexedNotNull { i,id -> ps.find { it.id==id }?.let { ItemCarrito(it,cantidades.getOrElse(i){1},notas.getOrElse(i){""}) } }
        }
    }
    private fun recordar() {
        saved["ids"]=_carrito.value.map { it.producto.id }.toIntArray()
        saved["cantidades"]=_carrito.value.map { it.cantidad }.toIntArray()
        saved["notas"]=ArrayList(_carrito.value.map { it.nota })
    }
    fun categoria(c: String) { _categoria.value=c }
    fun agregar(producto: Producto,cantidad: Int,nota: String): Boolean {
        _error.value=null
        if(!producto.disponible) { _error.value="Producto agotado"; return false }
        val anterior=_carrito.value.find { it.producto.id==producto.id }
        if(cantidad<1 || cantidad+(anterior?.cantidad ?: 0)>99) { _error.value="Puedes pedir de 1 a 99 unidades"; return false }
        _carrito.value=if(anterior==null) _carrito.value+ItemCarrito(producto,cantidad,nota) else _carrito.value.map { if(it.producto.id==producto.id) it.copy(cantidad=it.cantidad+cantidad,nota=listOf(it.nota,nota).filter { n -> n.isNotBlank() }.distinct().joinToString("; ")) else it }
        recordar(); return true
    }
    fun quitar(productoId: Int) { _carrito.value=_carrito.value.filterNot { it.producto.id==productoId }; recordar() }
    fun vaciar() { _carrito.value=emptyList(); recordar() }
    fun total(): Double = _carrito.value.sumOf { it.subtotal }
}
