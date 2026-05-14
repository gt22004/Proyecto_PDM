package sv.edu.ues.fia.proyecto_pdm.importador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class TelefonoImportadorMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_telefono_importador_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnTelefonoInsertar).setOnClickListener {
            startActivity(Intent(this, TelefonoImportadorInsertarActivity::class.java))
        }
        findViewById<Button>(R.id.btnTelefonoConsultar).setOnClickListener {
            startActivity(Intent(this, TelefonoImportadorConsultarActivity::class.java))
        }
        findViewById<Button>(R.id.btnTelefonoActualizar).setOnClickListener {
            startActivity(Intent(this, TelefonoImportadorActualizarActivity::class.java))
        }
        findViewById<Button>(R.id.btnTelefonoEliminar).setOnClickListener {
            startActivity(Intent(this, TelefonoImportadorEliminarActivity::class.java))
        }
    }
}