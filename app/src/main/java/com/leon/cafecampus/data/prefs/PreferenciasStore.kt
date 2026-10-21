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
    private val oscuro=booleanPreferencesKey("modoOscuro")
    private val recibir=booleanPreferencesKey("recibirPedidos")
    val perfilActivo=datos.map { it[perfil] ?: "" }
    val modoOscuro=datos.map { it[oscuro] ?: false }
    val recibirPedidos=datos.map { it[recibir] ?: true }
    suspend fun guardarPerfil(rol: Rol) { store.edit { it[perfil]=rol.name } }
    suspend fun guardarModoOscuro(b: Boolean) { store.edit { it[oscuro]=b } }
    suspend fun guardarRecibirPedidos(b: Boolean) { store.edit { it[recibir]=b } }
    suspend fun cerrarSesion() { store.edit { it.remove(perfil) } }
}
