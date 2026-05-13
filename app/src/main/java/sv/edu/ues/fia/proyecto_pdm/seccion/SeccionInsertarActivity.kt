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

class SeccionInsertarActivity : AppCompatActivity() {

    private lateinit var seccionHandler: SeccionHandler
    private lateinit var bodegaHandler: BodegaHandler
    private lateinit var spinnerBodegas: Spinner
    private lateinit var editNivel: EditText
    private lateinit var editCapacidad: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnLimpiar: Button
    private lateinit var listaBodegas: List<Bodega>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seccion_insertar)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        seccionHandler = SeccionHandler(this)
        bodegaHandler = BodegaHandler(this)

        spinnerBodegas = findViewById(R.id.spinnerBodegas)
        editNivel = findViewById(R.id.editNivel)
        editCapacidad = findViewById(R.id.editCapacidadSeccion)
        btnGuardar = findViewById(R.id.btnGuardarSeccion)
        btnLimpiar = findViewById(R.id.btnLimpiarSeccion)

        cargarBodegas()

        btnGuardar.setOnClickListener { insertarSeccion() }
        btnLimpiar.setOnClickListener { limpiarCampos() }
    }

    private fun cargarBodegas() {
        listaBodegas = bodegaHandler.obtenerTodas()
        if (listaBodegas.isEmpty()) {
            Toast.makeText(this, "Debe registrar una bodega primero", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val nombresBodegas = listaBodegas.map { it.nombreBodega }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresBodegas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBodegas.adapter = adapter
    }

    private fun insertarSeccion() {
        val nivelStr = editNivel.text.toString()
        val capacidadStr = editCapacidad.text.toString()
        
        if (nivelStr.isEmpty() || capacidadStr.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val idBodega = listaBodegas[spinnerBodegas.selectedItemPosition].idBodega
            val nivel = nivelStr.toInt()
            val capacidad = capacidadStr.toInt()

            val seccion = Seccion(0, idBodega, nivel, capacidad)
            val res = seccionHandler.insertar(seccion)

            if (res != -1L) {
                Toast.makeText(this, "Sección guardada. ID: $res", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al insertar sección", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        editNivel.setText("")
        editCapacidad.setText("")
        editNivel.requestFocus()
    }
}
