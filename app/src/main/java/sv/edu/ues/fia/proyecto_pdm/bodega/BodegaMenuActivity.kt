package sv.edu.ues.fia.proyecto_pdm.bodega

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.R

class BodegaMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bodega_menu)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnMenuInsertar).setOnClickListener {
            startActivity(Intent(this, BodegaInsertarActivity::class.java))
        }

        findViewById<Button>(R.id.btnMenuConsultar).setOnClickListener {
            startActivity(Intent(this, BodegaConsultarActivity::class.java))
        }

        findViewById<Button>(R.id.btnMenuActualizar).setOnClickListener {
            startActivity(Intent(this, BodegaActualizarActivity::class.java))
        }

        findViewById<Button>(R.id.btnMenuEliminar).setOnClickListener {
            startActivity(Intent(this, BodegaEliminarActivity::class.java))
        }

        findViewById<Button>(R.id.btnGestionSecciones).setOnClickListener {
            startActivity(Intent(this, sv.edu.ues.fia.proyecto_pdm.seccion.SeccionMenuActivity::class.java))
        }

        findViewById<Button>(R.id.btnGestionUbicaciones).setOnClickListener {
            startActivity(Intent(this, sv.edu.ues.fia.proyecto_pdm.ubicacion.UbicacionMenuActivity::class.java))
        }
    }
}
