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
    private val _busqueda=MutableStateFlow("")
    val busqueda=_busqueda.asStateFlow()
    fun buscar(texto: String) { _busqueda.value=texto }
    private fun normalizar(texto: String): String = java.text.Normalizer.normalize(texto,java.text.Normalizer.Form.NFD).replace("\\p{M}".toRegex(),"").lowercase(java.util.Locale.ROOT)
    private val favoritos=MutableStateFlow<Set<Int>>(emptySet())
    fun actualizarFavoritos(ids: Set<Int>) { favoritos.value=ids }
    data class EstadoMenu(val productos: List<Producto> = emptyList(),val cargando: Boolean=true)
    val estadoMenu=combine(repo.observarProductos(),categoria,busqueda,favoritos) { ps,c,b,fs ->
        EstadoMenu(ps.filter { (c=="Todo" || (c=="Favoritos" && it.id in fs) || it.categoria==c) && normalizar(it.nombre).contains(normalizar(b.trim())) },false)
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),EstadoMenu())
    val filtrados=estadoMenu.map { it.productos }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())
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
    private val _repetido=MutableStateFlow(false)
    val repetido=_repetido.asStateFlow()
    private val _repitiendo=MutableStateFlow(false)
    val repitiendo=_repitiendo.asStateFlow()
    fun consumirRepetido() { _repetido.value=false }
    fun repetir(pedido: com.leon.cafecampus.data.local.relation.PedidoConDetalle) {
        if(_repitiendo.value) return
        _repitiendo.value=true; _error.value=null
        viewModelScope.launch {
            try {
                val actuales=repo.observarProductos().first().associateBy { it.id }
                val disponibles=pedido.detalles.mapNotNull { d -> actuales[d.productoId]?.takeIf { it.disponible }?.let { ItemCarrito(it,d.cantidad.coerceIn(1,99)) } }
                require(disponibles.isNotEmpty()) { "Los productos de ese pedido ya no están disponibles" }
                _carrito.value=disponibles; recordar()
                _error.value=if(disponibles.size<pedido.detalles.size) "Se omitieron productos que ya no están disponibles" else null
                _repetido.value=true
            } catch(e: Exception) { _error.value=e.message ?: "No se pudo repetir el pedido" }
            finally { _repitiendo.value=false }
        }
    }
    fun quitar(productoId: Int) { _carrito.value=_carrito.value.filterNot { it.producto.id==productoId }; recordar() }
    fun vaciar() { _carrito.value=emptyList(); recordar() }
    fun total(): Double = _carrito.value.sumOf { it.subtotal }
}
