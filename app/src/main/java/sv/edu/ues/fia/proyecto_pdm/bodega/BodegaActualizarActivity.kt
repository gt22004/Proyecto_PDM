package sv.edu.ues.fia.proyecto_pdm.bodega

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class BodegaActualizarActivity : AppCompatActivity() {

    private lateinit var helper: BodegaHandler
    private lateinit var editIdBodega: EditText
    private lateinit var editNombreBodega: EditText
    private lateinit var editDepartamento: EditText
    private lateinit var editDireccion: EditText
    private lateinit var btnCargar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnLimpiar: Button

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

        editIdBodega = findViewById(R.id.editIdBodega)
        editNombreBodega = findViewById(R.id.editNombreBodega)
        editDepartamento = findViewById(R.id.editDepartamento)
        editDireccion = findViewById(R.id.editDireccion)
        btnCargar = findViewById(R.id.btnCargarBodega)
        btnActualizar = findViewById(R.id.btnActualizarBodega)
        btnLimpiar = findViewById(R.id.btnLimpiarActualizar)

        btnCargar.setOnClickListener {
            cargarBodega()
        }

        btnActualizar.setOnClickListener {
            actualizarBodega()
        }

        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun cargarBodega() {
        val idStr = editIdBodega.text.toString()
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese ID para cargar", Toast.LENGTH_SHORT).show()
            return
        }

        val bodega = helper.consultar(idStr.toInt())
        if (bodega != null) {
            editNombreBodega.setText(bodega.nombreBodega)
            editDepartamento.setText(bodega.departamento)
            editDireccion.setText(bodega.direccion)
            
            // Habilitar campos para edición
            setEdicionHabilitada(true)
            Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No se encontró la bodega", Toast.LENGTH_SHORT).show()
            setEdicionHabilitada(false)
        }
    }

    private fun actualizarBodega() {
        val bodega = Bodega().apply {
            idBodega = editIdBodega.text.toString().toInt()
            nombreBodega = editNombreBodega.text.toString()
            departamento = editDepartamento.text.toString()
            direccion = editDireccion.text.toString()
        }

        val filas = helper.actualizar(bodega)
        if (filas > 0) {
            Toast.makeText(this, "Bodega actualizada", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setEdicionHabilitada(habilitar: Boolean) {
        editNombreBodega.isEnabled = habilitar
        editDepartamento.isEnabled = habilitar
        editDireccion.isEnabled = habilitar
        btnActualizar.isEnabled = habilitar
        // El ID no se debe editar una vez cargado para no perder la referencia
        editIdBodega.isEnabled = !habilitar 
    }

    private fun limpiarCampos() {
        editIdBodega.setText("")
        editNombreBodega.setText("")
        editDepartamento.setText("")
        editDireccion.setText("")
        setEdicionHabilitada(false)
        editIdBodega.isEnabled = true
        editIdBodega.requestFocus()
    }
}