package sv.edu.ues.fia.proyecto_pdm.bodega

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

class BodegaConsultarActivity : AppCompatActivity() {

    private lateinit var helper: BodegaHandler
    private lateinit var editIdBodega: EditText
    private lateinit var viewNombreBodega: TextView
    private lateinit var viewDepartamento: TextView
    private lateinit var viewDireccion: TextView
    private lateinit var btnConsultar: Button
    private lateinit var btnLimpiar: Button

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

        editIdBodega = findViewById(R.id.editIdBodega)
        viewNombreBodega = findViewById(R.id.viewNombreBodega)
        viewDepartamento = findViewById(R.id.viewDepartamento)
        viewDireccion = findViewById(R.id.viewDireccion)
        btnConsultar = findViewById(R.id.btnConsultarBodega)
        btnLimpiar = findViewById(R.id.btnLimpiarConsultar)

        btnConsultar.setOnClickListener {
            consultarBodega()
        }

        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun consultarBodega() {
        if (editIdBodega.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingrese un ID para buscar", Toast.LENGTH_SHORT).show()
            return
        }

        val id = editIdBodega.text.toString().toInt()
        val bodega = helper.consultar(id)

        if (bodega != null) {
            viewNombreBodega.text = bodega.nombreBodega
            viewDepartamento.text = bodega.departamento
            viewDireccion.text = bodega.direccion
        } else {
            Toast.makeText(this, "Bodega con ID $id no encontrada", Toast.LENGTH_SHORT).show()
            limpiarCampos(soloResultados = true)
        }
    }

    private fun limpiarCampos(soloResultados: Boolean = false) {
        if (!soloResultados) editIdBodega.setText("")
        viewNombreBodega.text = "-"
        viewDepartamento.text = "-"
        viewDireccion.text = "-"
    }
}