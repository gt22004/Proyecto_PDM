package sv.edu.ues.fia.proyecto_pdm.bodega

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.BaseActivity
import sv.edu.ues.fia.proyecto_pdm.R

class BodegaConsultarActivity : BaseActivity() {

    private lateinit var helper: BodegaHandler
    private lateinit var editId: EditText
    private lateinit var textResultado: TextView
    private lateinit var btnConsultar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bodega_consultar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = BodegaHandler(this)
        editId = findViewById(R.id.editConsultarIdBodega)
        textResultado = findViewById(R.id.textResultadoBodega)
        btnConsultar = findViewById(R.id.btnConsultarBodega)

        btnConsultar.setOnClickListener {
            val id = editId.text.toString().toIntOrNull()
            if (id != null) {
                val bodega = helper.consultar(id)
                if (bodega != null) {
                    textResultado.text = "ID: ${bodega.idBodega}\n" +
                            "Nombre: ${bodega.nombreBodega}\n" +
                            "Depto: ${bodega.departamento}\n" +
                            "Dir: ${bodega.direccion}\n" +
                            "Capacidad: ${bodega.capacidadSecciones}"
                } else {
                    textResultado.text = "No se encontró la bodega con ID $id"
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
