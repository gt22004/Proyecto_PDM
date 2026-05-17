package sv.edu.ues.fia.proyecto_pdm.seccion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sv.edu.ues.fia.proyecto_pdm.BaseActivity
import sv.edu.ues.fia.proyecto_pdm.R

class SeccionMenuActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seccion_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btnSeccionInsertar).setOnClickListener {
            startActivity(Intent(this, SeccionInsertarActivity::class.java))
        }

        findViewById<Button>(R.id.btnSeccionConsultar).setOnClickListener {
            startActivity(Intent(this, SeccionConsultarActivity::class.java))
        }

        findViewById<Button>(R.id.btnSeccionActualizar).setOnClickListener {
            startActivity(Intent(this, SeccionActualizarActivity::class.java))
        }

        findViewById<Button>(R.id.btnSeccionEliminar).setOnClickListener {
            startActivity(Intent(this, SeccionEliminarActivity::class.java))
        }
    }
}
