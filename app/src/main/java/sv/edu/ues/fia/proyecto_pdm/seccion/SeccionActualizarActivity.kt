package sv.edu.ues.fia.proyecto_pdm.seccion

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R
import sv.edu.ues.fia.proyecto_pdm.bodega.Bodega
import sv.edu.ues.fia.proyecto_pdm.bodega.BodegaHandler

class SeccionActualizarActivity : AppCompatActivity() {

    private lateinit var seccionHandler: SeccionHandler
    private lateinit var bodegaHandler: BodegaHandler
    
    private lateinit var editId: EditText
    private lateinit var editNivel: EditText
    private lateinit var editCapacidad: EditText
    private lateinit var spinnerBodegas: Spinner
    private lateinit var btnCargar: Button
    private lateinit var btnActualizar: Button
    
    private lateinit var listaBodegas: List<Bodega>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seccion_actualizar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        seccionHandler = SeccionHandler(this)
        bodegaHandler = BodegaHandler(this)

        editId = findViewById(R.id.editActualizarIdSeccion)
        editNivel = findViewById(R.id.editActualizarNivel)
        editCapacidad = findViewById(R.id.editActualizarCapacidadSeccion)
        spinnerBodegas = findViewById(R.id.spinnerActualizarBodegas)
        btnCargar = findViewById(R.id.btnCargarSeccion)
        btnActualizar = findViewById(R.id.btnActualizarSeccion)

        cargarBodegas()

        btnCargar.setOnClickListener { cargarSeccion() }
        btnActualizar.setOnClickListener { actualizarSeccion() }
    }

    private fun cargarBodegas() {
        listaBodegas = bodegaHandler.obtenerTodas()
        val nombresBodegas = listaBodegas.map { it.nombreBodega }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresBodegas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBodegas.adapter = adapter
    }

    private fun cargarSeccion() {
        val id = editId.text.toString().toIntOrNull()
        if (id != null) {
            val seccion = seccionHandler.consultar(id)
            if (seccion != null) {
                editNivel.setText(seccion.nivel.toString())
                editCapacidad.setText(seccion.capacidadMax.toString())
                
                // Seleccionar la bodega correcta en el spinner
                val index = listaBodegas.indexOfFirst { it.idBodega == seccion.idBodega }
                if (index != -1) spinnerBodegas.setSelection(index)
                
                Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se encontró la sección", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarSeccion() {
        val id = editId.text.toString().toIntOrNull()
        val nivel = editNivel.text.toString().toIntOrNull()
        val capacidad = editCapacidad.text.toString().toIntOrNull()

        if (id != null && nivel != null && capacidad != null) {
            val idBodega = listaBodegas[spinnerBodegas.selectedItemPosition].idBodega
            val seccion = Seccion(id, idBodega, nivel, capacidad)
            val actualizados = seccionHandler.actualizar(seccion)
            
            if (actualizados > 0) {
                Toast.makeText(this, "Sección actualizada con éxito", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
