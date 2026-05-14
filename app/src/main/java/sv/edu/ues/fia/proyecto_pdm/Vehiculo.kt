package sv.edu.ues.fia.proyecto_pdm

data class Vehiculo(
    var idVehiculo: Int? = null,
    var marca: String? = null,
    var modelo: String? = null,
    var anio: Int? = null,
    var estado: String = "DISPONIBLE", // DISPONIBLE o VENDIDO
    var idUbicacion: Int? = null,
    var idImportacion: Int? = null
)
