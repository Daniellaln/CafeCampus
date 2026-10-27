package com.leon.cafecampus.model
import java.util.Calendar
import java.util.Locale

// Opciones futuras desde el reloj local, con al menos quince minutos de margen.
fun opcionesRecojo(instante: Long=System.currentTimeMillis()): List<String> {
    val hoy=Calendar.getInstance().apply { timeInMillis=instante }
    val cita=(hoy.clone() as Calendar).apply {
        add(Calendar.MINUTE,15)
        val resto=get(Calendar.MINUTE)%30
        if(resto!=0 || get(Calendar.SECOND)>0 || get(Calendar.MILLISECOND)>0) add(Calendar.MINUTE,30-resto)
        set(Calendar.SECOND,0);set(Calendar.MILLISECOND,0)
    }
    return listOf("Ahora")+List(3) {
        val dia=if(cita.get(Calendar.YEAR)==hoy.get(Calendar.YEAR) && cita.get(Calendar.DAY_OF_YEAR)==hoy.get(Calendar.DAY_OF_YEAR)) "Hoy" else "Mañana"
        val texto=String.format(Locale.US,"%s, %02d:%02d",dia,cita.get(Calendar.HOUR_OF_DAY),cita.get(Calendar.MINUTE))
        cita.add(Calendar.MINUTE,30)
        texto
    }
}
