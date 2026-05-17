package sv.edu.ues.fia.proyecto_pdm.bodega

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.BaseActivity
import sv.edu.ues.fia.proyecto_pdm.R

class BodegaActualizarActivity : BaseActivity() {

    private lateinit var helper: BodegaHandler
    private lateinit var editId: EditText
    private lateinit var editNombre: EditText
    private lateinit var editDepto: EditText
    private lateinit var editDir: EditText
    private lateinit var editCapacidad: EditText
    private lateinit var btnCargar: Button
    private lateinit var btnActualizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bodega_actualizar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = BodegaHandler(this)
        editId = findViewById(R.id.editActualizarIdBodega)
        editNombre = findViewById(R.id.editActualizarNombreBodega)
        editDepto = findViewById(R.id.editActualizarDepartamento)
        editDir = findViewById(R.id.editActualizarDireccion)
        editCapacidad = findViewById(R.id.editActualizarCapacidad)
        btnCargar = findViewById(R.id.btnCargarBodega)
        btnActualizar = findViewById(R.id.btnActualizarBodega)

        btnCargar.setOnClickListener { cargarBodega() }
        btnActualizar.setOnClickListener { actualizarBodega() }
    }

    private fun cargarBodega() {
        val id = editId.text.toString().toIntOrNull()
        if (id != null) {
            val bodega = helper.consultar(id)
            if (bodega != null) {
                editNombre.setText(bodega.nombreBodega)
                editDepto.setText(bodega.departamento)
                editDir.setText(bodega.direccion)
                editCapacidad.setText(bodega.capacidadSecciones.toString())
                Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se encontró la bodega", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarBodega() {
        val id = editId.text.toString().toIntOrNull()
        val nombre = editNombre.text.toString()
        val depto = editDepto.text.toString()
        val dir = editDir.text.toString()
        val capacidad = editCapacidad.text.toString().toIntOrNull()

        if (id != null && nombre.isNotEmpty() && capacidad != null) {
            val bodega = Bodega().apply {
                idBodega = id
                nombreBodega = nombre
                departamento = depto
                direccion = dir
                capacidadSecciones = capacidad
            }
            val res = helper.actualizar(bodega)
            if (res > 0) {
                Toast.makeText(this, "Bodega actualizada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
