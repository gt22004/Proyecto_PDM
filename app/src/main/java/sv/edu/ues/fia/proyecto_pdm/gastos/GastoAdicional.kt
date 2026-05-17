package sv.edu.ues.fia.proyecto_pdm.gastos

data class GastoAdicional(
    var idGasto: Int? = null,
    var idVehiculo: Int,
    var concepto: String,
    var monto: Double,
    var fechaGasto: String
)
