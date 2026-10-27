package com.leon.cafecampus.viewmodel
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.leon.cafecampus.data.repository.CafeRepository
import com.leon.cafecampus.data.prefs.PreferenciasStore
import com.leon.cafecampus.data.local.entity.Usuario
import com.leon.cafecampus.model.Rol
class SesionViewModel(private val repo: CafeRepository,private val prefs: PreferenciasStore,private val preparar: suspend ()->Unit) : ViewModel() {
    private val _usuarioActual=MutableStateFlow<Usuario?>(null)
    val usuarioActual=_usuarioActual.asStateFlow()
    private val _error=MutableStateFlow<String?>(null)
    val error=_error.asStateFlow()
    private val _cargando=MutableStateFlow(true)
    val cargando=_cargando.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class)
    val favoritos=usuarioActual.flatMapLatest { u -> if(u==null) flowOf(emptySet<Int>()) else prefs.favoritos(u.id) }.stateIn(viewModelScope,SharingStarted.Eagerly,emptySet())
    fun alternarFavorito(producto: Int) { val u=_usuarioActual.value ?: return;viewModelScope.launch { try { prefs.alternarFavorito(u.id,producto) } catch(e: Exception) { _error.value="No se pudo guardar el favorito" } } }
    val modoOscuro=prefs.modoOscuro.stateIn(viewModelScope,SharingStarted.Eagerly,false)
    val recibirPedidos=prefs.recibirPedidos.stateIn(viewModelScope,SharingStarted.Eagerly,true)
    init { viewModelScope.launch {
        try {
            preparar()
            val perfil=prefs.perfilActivo.first()
            if(perfil.isNotBlank()) _usuarioActual.value=cuentaPractica(Rol.valueOf(perfil))
        } catch(e: Exception) { _error.value="No se pudo restaurar la sesión. Vuelve a ingresar." }
        finally { _cargando.value=false }
    } }
    // El laboratorio solo recuerda el perfil de las dos cuentas ficticias locales.
    private suspend fun cuentaPractica(rol: Rol) = if(rol==Rol.ADMIN) repo.login("admin@cafe.test","admin") else repo.login("daniella@campus.test","1234")
    fun limpiarError() { _error.value=null }
    fun login(correo: String,clave: String,rolSeleccionado: Rol) {
        if(_cargando.value) return
        _error.value=null
        if(correo.isBlank() || clave.isBlank()) { _error.value="Completa el correo y la contraseña"; return }
        _cargando.value=true
        viewModelScope.launch {
            try {
                val u=repo.login(correo,clave)
                when {
                    u==null -> _error.value="Correo o contraseña incorrectos"
                    u.rol!=rolSeleccionado.name -> _error.value="Selecciona el perfil que corresponde a esta cuenta"
                    else -> { prefs.guardarPerfil(rolSeleccionado); _usuarioActual.value=u }
                }
            } catch(e: Exception) { _error.value="No se pudo iniciar sesión. Inténtalo de nuevo." }
            finally { _cargando.value=false }
        }
    }
    fun cambiarPerfil() { viewModelScope.launch {
        try {
            val rol=if(_usuarioActual.value?.rol==Rol.ADMIN.name) Rol.CLIENTE else Rol.ADMIN
            val u=cuentaPractica(rol) ?: error("No existe la cuenta de práctica")
            prefs.guardarPerfil(rol); _usuarioActual.value=u
        } catch(e: Exception) { _error.value="No se pudo cambiar de perfil" }
    } }
    fun cerrarSesion() { viewModelScope.launch { try { prefs.cerrarSesion(); _usuarioActual.value=null; _error.value=null } catch(e: Exception) { _error.value="No se pudo cerrar la sesión" } } }
    fun modoOscuro(b: Boolean) { viewModelScope.launch { try { prefs.guardarModoOscuro(b) } catch(e: Exception) { _error.value="No se pudo guardar la preferencia" } } }
    fun recibirPedidos(b: Boolean) { viewModelScope.launch { try { prefs.guardarRecibirPedidos(b) } catch(e: Exception) { _error.value="No se pudo guardar la preferencia" } } }
}
