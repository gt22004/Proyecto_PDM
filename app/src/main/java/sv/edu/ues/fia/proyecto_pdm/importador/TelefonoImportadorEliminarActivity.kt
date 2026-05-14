package sv.edu.ues.fia.proyecto_pdm.importador

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

class TelefonoImportadorEliminarActivity : AppCompatActivity() {

    private lateinit var handler: TelefonoImportadorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_telefono_importador_eliminar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handler = TelefonoImportadorHandler(this)

        val editId = findViewById<EditText>(R.id.editEliminarTelefonoId)
        val btnEliminar = findViewById<Button>(R.id.btnEliminarTelefono)

        btnEliminar.setOnClickListener {
            val id = editId.text.toString().trim().toIntOrNull()
            if (id == null) {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val telefono = handler.buscar(id)
            if (telefono == null) {
                Toast.makeText(this, "No existe teléfono con ID: $id", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Eliminar el número ${telefono.numero}?")
                .setPositiveButton("Eliminar") { _, _ ->
                    val resultado = handler.eliminar(id)
                    if (resultado > 0) {
                        Toast.makeText(this, "Teléfono eliminado", Toast.LENGTH_SHORT).show()
                        editId.setText("")
                    } else {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }
}