package com.leon.cafecampus.data.prefs
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import java.io.IOException
import com.leon.cafecampus.model.Rol
val Context.dataStore by preferencesDataStore("ajustes")
class PreferenciasStore(context: Context) {
    private val store=context.applicationContext.dataStore
    private val datos=store.data.catch { if(it is IOException) emit(emptyPreferences()) else throw it }
    private val perfil=stringPreferencesKey("perfilActivo")
    private val usuario=intPreferencesKey("usuarioActivoId")
    val usuarioActivoId=datos.map { it[usuario] }
    suspend fun guardarSesion(id: Int,rol: Rol) { store.edit { it[usuario]=id;it[perfil]=rol.name } }
    private val oscuro=booleanPreferencesKey("modoOscuro")
    private val recibir=booleanPreferencesKey("recibirPedidos")
    val perfilActivo=datos.map { it[perfil] ?: "" }
    val modoOscuro=datos.map { it[oscuro] ?: false }
    val recibirPedidos=datos.map { it[recibir] ?: true }
    suspend fun guardarPerfil(rol: Rol) { store.edit { it[perfil]=rol.name } }
    suspend fun guardarModoOscuro(b: Boolean) { store.edit { it[oscuro]=b } }
    suspend fun guardarRecibirPedidos(b: Boolean) { store.edit { it[recibir]=b } }
    fun favoritos(usuario: Int): Flow<Set<Int>> = datos.map { p -> p[stringSetPreferencesKey("favoritos_$usuario")]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet() }
    suspend fun alternarFavorito(usuario: Int,producto: Int) {
        val clave=stringSetPreferencesKey("favoritos_$usuario")
        store.edit { p -> val ids=p[clave] ?: emptySet();p[clave]=if(producto.toString() in ids) ids-producto.toString() else ids+producto.toString() }
    }
    suspend fun cerrarSesion() { store.edit { it.remove(perfil);it.remove(usuario) } }
}
