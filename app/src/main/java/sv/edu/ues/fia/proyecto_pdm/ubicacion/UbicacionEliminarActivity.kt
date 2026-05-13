package sv.edu.ues.fia.proyecto_pdm.ubicacion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class UbicacionEliminarActivity : AppCompatActivity() {

    private lateinit var helper: UbicacionHandler
    private lateinit var editId: EditText
    private lateinit var btnEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ubicacion_eliminar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = UbicacionHandler(this)
        editId = findViewById(R.id.editEliminarIdUbicacion)
        btnEliminar = findViewById(R.id.btnEliminarUbicacion)

        btnEliminar.setOnClickListener {
            val id = editId.text.toString().toIntOrNull()
            if (id != null) {
                val eliminados = helper.eliminar(id)
                if (eliminados > 0) {
                    Toast.makeText(this, "Ubicación eliminada con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "No se encontró la ubicación", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
