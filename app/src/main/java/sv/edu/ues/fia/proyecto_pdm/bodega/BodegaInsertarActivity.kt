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

class BodegaInsertarActivity : AppCompatActivity() {

    private lateinit var helper: BodegaHandler
    private lateinit var editNombreBodega: EditText
    private lateinit var editDepartamento: EditText
    private lateinit var editDireccion: EditText
    private lateinit var editCapacidad: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnLimpiar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bodega_insertar)
        
        // Ajuste para el modo EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el manejador de la base de datos
        helper = BodegaHandler(this)

        // Vincular vistas con el XML
        editNombreBodega = findViewById(R.id.editNombreBodega)
        editDepartamento = findViewById(R.id.editDepartamento)
        editDireccion = findViewById(R.id.editDireccion)
        editCapacidad = findViewById(R.id.editCapacidadBodega)
        btnGuardar = findViewById(R.id.btnGuardarBodega)
        btnLimpiar = findViewById(R.id.btnLimpiarBodega)

        // Evento del botón Guardar
        btnGuardar.setOnClickListener {
            insertarBodega()
        }

        // Evento del botón Limpiar
        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }

    private fun insertarBodega() {
        // Validar que los campos no estén vacíos
        if (editNombreBodega.text.toString().isEmpty() || editCapacidad.text.toString().isEmpty()) {
            Toast.makeText(this, "Por favor complete los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // 1. Crear el objeto con los datos de la pantalla
            val bodega = Bodega()
            bodega.nombreBodega = editNombreBodega.text.toString()
            bodega.departamento = editDepartamento.text.toString()
            bodega.direccion = editDireccion.text.toString()
            bodega.capacidadSecciones = editCapacidad.text.toString().toInt()

            // 2. Llamar al Handler para insertar en la BD
            val resultado = helper.insertar(bodega)

            // 3. Notificar al usuario
            if (resultado != -1L) {
                Toast.makeText(this, "Bodega guardada con éxito. ID Generado: $resultado", Toast.LENGTH_LONG).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al insertar bodega.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        editNombreBodega.setText("")
        editDepartamento.setText("")
        editDireccion.setText("")
        editCapacidad.setText("")
        editNombreBodega.requestFocus()
    }
}