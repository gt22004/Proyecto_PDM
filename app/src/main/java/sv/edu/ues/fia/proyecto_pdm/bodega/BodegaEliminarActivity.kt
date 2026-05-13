package sv.edu.ues.fia.proyecto_pdm.bodega

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class BodegaEliminarActivity : AppCompatActivity() {

    private lateinit var helper: BodegaHandler
    private lateinit var editIdBodega: EditText
    private lateinit var btnEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bodega_eliminar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = BodegaHandler(this)
        editIdBodega = findViewById(R.id.editIdBodega)
        btnEliminar = findViewById(R.id.btnEliminarBodega)

        btnEliminar.setOnClickListener {
            confirmarEliminacion()
        }
    }

    private fun confirmarEliminacion() {
        val idStr = editIdBodega.text.toString()
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese ID para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val id = idStr.toInt()
        
        // Primero verificar si existe
        val bodega = helper.consultar(id)
        if (bodega == null) {
            Toast.makeText(this, "La bodega con ID $id no existe", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar diálogo de confirmación
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de eliminar la bodega '${bodega.nombreBodega}'? Esto podría afectar a las secciones asociadas.")
            .setPositiveButton("Eliminar") { _, _ ->
                val resultado = helper.eliminar(id)
                if (resultado > 0) {
                    Toast.makeText(this, "Bodega eliminada con éxito", Toast.LENGTH_SHORT).show()
                    editIdBodega.setText("")
                } else {
                    Toast.makeText(this, "Error al eliminar la bodega", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}