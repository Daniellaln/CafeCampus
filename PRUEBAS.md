# Pruebas de CafeCampus fase2 completa

Consulta VERIFICACION_FINAL.md y docs/verificacion/resultado.json para la ejecución incluida. Las19pruebas comprueban los contratos de datos, nueva cuenta, migración, reporte y recorrido real de ambos roles.

```powershell
.\gradlew.bat assembleDebug compileDebugAndroidTestKotlin
.\gradlew.bat connectedDebugAndroidTest lintDebug
```

Para repetir FlujoAceptacionTest usa una instalación de prueba sin pedidos/cuentas previos. Genera datos ficticios, modifica productos, roles y preferencias; captura cada pantalla. También cambia temporalmente fuente130%, la restaura y prueba modo oscuro y pulsación de controles. DatosAceptacionTest usa base en memoria y un archivo temporal de migración que elimina al terminar.
