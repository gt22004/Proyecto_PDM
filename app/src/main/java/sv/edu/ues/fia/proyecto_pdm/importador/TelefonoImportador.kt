package sv.edu.ues.fia.proyecto_pdm.importador

data class TelefonoImportador(
    val idTelefono: Int? = null,
    val nui: String,
    val numero: String,
    val tipoTelefono: String? = null
)