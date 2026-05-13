package sv.edu.ues.fia.proyecto_pdm.seccion

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

class SeccionConsultarActivity : AppCompatActivity() {

    private lateinit var helper: SeccionHandler
    private lateinit var editId: EditText
    private lateinit var textResultado: TextView
    private lateinit var btnConsultar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seccion_consultar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = SeccionHandler(this)
        editId = findViewById(R.id.editConsultarIdSeccion)
        textResultado = findViewById(R.id.textResultadoSeccion)
        btnConsultar = findViewById(R.id.btnConsultarSeccion)

        btnConsultar.setOnClickListener {
            val id = editId.text.toString().toIntOrNull()
            if (id != null) {
                val seccion = helper.consultar(id)
                if (seccion != null) {
                    textResultado.text = "ID: ${seccion.idSeccion}\nID Bodega: ${seccion.idBodega}\nNivel: ${seccion.nivel}\nCapacidad Max: ${seccion.capacidadMax}"
                } else {
                    textResultado.text = "No se encontró la sección con ID $id"
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
