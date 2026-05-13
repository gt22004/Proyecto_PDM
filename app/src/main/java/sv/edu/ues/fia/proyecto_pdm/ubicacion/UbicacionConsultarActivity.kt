package sv.edu.ues.fia.proyecto_pdm.ubicacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class UbicacionConsultarActivity : AppCompatActivity() {

    private lateinit var helper: UbicacionHandler
    private lateinit var editId: EditText
    private lateinit var textResultado: TextView
    private lateinit var btnConsultar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ubicacion_consultar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = UbicacionHandler(this)
        editId = findViewById(R.id.editConsultarIdUbicacion)
        textResultado = findViewById(R.id.textResultadoUbicacion)
        btnConsultar = findViewById(R.id.btnConsultarUbicacion)

        btnConsultar.setOnClickListener {
            val id = editId.text.toString().toIntOrNull()
            if (id != null) {
                val ubicacion = helper.consultar(id)
                if (ubicacion != null) {
                    textResultado.text = "ID: ${ubicacion.idUbicacion}\nSección: ${ubicacion.idSeccion}\nFecha: ${ubicacion.fechaAsignacion}\nActiva: ${if (ubicacion.activa) "SÍ" else "NO"}"
                } else {
                    textResultado.text = "No se encontró la ubicación con ID $id"
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
