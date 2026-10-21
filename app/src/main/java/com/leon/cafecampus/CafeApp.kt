package com.leon.cafecampus
import android.app.Application
import com.leon.cafecampus.data.local.*
import com.leon.cafecampus.data.repository.*
import kotlinx.coroutines.*
class CafeApp : Application() {
    val db by lazy { CafeDatabase.obtener(this) }
    val repository: CafeRepository by lazy { CafeRepositoryRoom(db) }
    val inicializacion = CompletableDeferred<Unit>()
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try { DatosIniciales.sembrar(db); inicializacion.complete(Unit) }
            catch(e: Exception) { inicializacion.completeExceptionally(e) }
        }
    }
}
