package sv.edu.ues.fia.proyecto_pdm.ubicacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class UbicacionMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ubicacion_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnUbicacionInsertar).setOnClickListener {
            startActivity(Intent(this, UbicacionInsertarActivity::class.java))
        }

        findViewById<Button>(R.id.btnUbicacionConsultar).setOnClickListener {
            startActivity(Intent(this, UbicacionConsultarActivity::class.java))
        }

        findViewById<Button>(R.id.btnUbicacionActualizar).setOnClickListener {
            startActivity(Intent(this, UbicacionActualizarActivity::class.java))
        }

        findViewById<Button>(R.id.btnUbicacionEliminar).setOnClickListener {
            startActivity(Intent(this, UbicacionEliminarActivity::class.java))
        }
    }
}
