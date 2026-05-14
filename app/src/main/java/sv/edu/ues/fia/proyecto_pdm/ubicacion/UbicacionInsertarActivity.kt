package sv.edu.ues.fia.proyecto_pdm.ubicacion

import android.app.DatePickerDialog
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
import sv.edu.ues.fia.proyecto_pdm.Vehiculo
import sv.edu.ues.fia.proyecto_pdm.VehiculoHandler
import sv.edu.ues.fia.proyecto_pdm.seccion.Seccion
import sv.edu.ues.fia.proyecto_pdm.seccion.SeccionHandler
import java.time.LocalDate
import java.util.Calendar

class UbicacionInsertarActivity : AppCompatActivity() {

    private lateinit var ubicacionHandler: UbicacionHandler
    private lateinit var vehiculoHandler: VehiculoHandler
    private lateinit var seccionHandler: SeccionHandler
    
    private lateinit var spinnerVehiculos: Spinner
    private lateinit var spinnerSecciones: Spinner
    private lateinit var editFecha: EditText
    private lateinit var btnGuardar: Button
    
    private lateinit var listaVehiculos: List<Vehiculo>
    private lateinit var listaSecciones: List<Seccion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ubicacion_insertar)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ubicacionHandler = UbicacionHandler(this)
        vehiculoHandler = VehiculoHandler(this)
        seccionHandler = SeccionHandler(this)

        spinnerVehiculos = findViewById(R.id.spinnerVehiculos)
        spinnerSecciones = findViewById(R.id.spinnerSecciones)
        editFecha = findViewById(R.id.editFechaAsignacion)
        btnGuardar = findViewById(R.id.btnGuardarUbicacion)

        cargarDatos()

        editFecha.setOnClickListener { mostrarDatePicker() }
        btnGuardar.setOnClickListener { insertarUbicacion() }
    }

    private fun cargarDatos() {
        listaVehiculos = vehiculoHandler.obtenerTodos().filter { it.estado == "DISPONIBLE" }
        listaSecciones = seccionHandler.obtenerTodas()

        if (listaVehiculos.isEmpty() || listaSecciones.isEmpty()) {
            Toast.makeText(this, "Asegúrese de tener vehículos disponibles y secciones creadas", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val adapterVeh = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaVehiculos.map { "ID: ${it.idVehiculo} - ${it.marca}" })
        adapterVeh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVehiculos.adapter = adapterVeh

        val adapterSec = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSecciones.map { "Sec: ${it.idSeccion} - Nivel ${it.nivel}" })
        adapterSec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSecciones.adapter = adapterSec
    }

    private fun mostrarDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, y, m, d ->
            val fecha = LocalDate.of(y, m + 1, d)
            editFecha.setText(fecha.toString())
        }, year, month, day)
        dpd.show()
    }

    private fun insertarUbicacion() {
        val fechaStr = editFecha.text.toString()
        if (fechaStr.isEmpty()) {
            Toast.makeText(this, "Seleccione una fecha", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val vehiculoSel = listaVehiculos[spinnerVehiculos.selectedItemPosition]
            val seccionSel = listaSecciones[spinnerSecciones.selectedItemPosition]
            
            // 1. Crear registro de ubicación
            val nuevaUbicacion = Ubicacion_vehiculo(0, seccionSel.idSeccion, LocalDate.parse(fechaStr), true)
            val idUbicacionGen = ubicacionHandler.insertar(nuevaUbicacion)

            if (idUbicacionGen != -1L) {
                // 2. Actualizar el vehículo para que apunte a esta ubicación
                vehiculoHandler.asignarUbicacion(vehiculoSel.idVehiculo!!, idUbicacionGen.toInt())
                
                Toast.makeText(this, "Ubicación asignada con éxito al vehículo ${vehiculoSel.idVehiculo}", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error: Capacidad de sección alcanzada (Trigger)", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al asignar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
