package sv.edu.ues.fia.proyecto_pdm

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sv.edu.ues.fia.proyecto_pdm.usuarios.UsuarioHandler
import sv.edu.ues.fia.proyecto_pdm.transporte.MedioTransporteHandler
import sv.edu.ues.fia.proyecto_pdm.transporte.MedioTransporte
import sv.edu.ues.fia.proyecto_pdm.usuarios.OpcionCrudHandler
import sv.edu.ues.fia.proyecto_pdm.usuarios.AccesoUsuarioHandler
import java.util.Locale

class LoginActivity : BaseActivity() {

    private lateinit var usuarioHandler: UsuarioHandler
    private lateinit var medioHandler: MedioTransporteHandler
    private lateinit var vehiculoHandler: VehiculoHandler
    private lateinit var importadorHandler: ImportadorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioHandler = UsuarioHandler(this)
        medioHandler = MedioTransporteHandler(this)
        vehiculoHandler = VehiculoHandler(this)
        importadorHandler = ImportadorHandler(this)

        val editUser = findViewById<EditText>(R.id.editUser)
        val editPass = findViewById<EditText>(R.id.editPass)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnLlenarBD = findViewById<Button>(R.id.btnLlenarBD)
        val btnChangeLang = findViewById<Button>(R.id.btnChangeLang)

        btnLogin.setOnClickListener {
            val user = editUser.text.toString()
            val pass = editPass.text.toString()

            if (usuarioHandler.validarLogin(user, pass)) {
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
            }
        }

        btnLlenarBD.setOnClickListener {
            llenarBaseDatos()
        }

        btnChangeLang.setOnClickListener {
            toggleLanguage()
        }
    }

    private fun toggleLanguage() {
        val currentLocale = resources.configuration.locales.get(0).language
        val newLang = if (currentLocale == "es") "en" else "es"

        LocaleHelper.setLocale(this, newLang)

        // Restart activity to apply changes
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun llenarBaseDatos() {
        // Insertar usuario de prueba
        usuarioHandler.insertarUsuario("admin", "123")

        // Insertar algunos medios de transporte
        medioHandler.insertar(MedioTransporte(1, "Carro", 1))
        medioHandler.insertar(MedioTransporte(2, "Grua", 1))
        medioHandler.insertar(MedioTransporte(3, "Tacuazina", 12))

        // Insertar algunos vehiculos de prueba
        vehiculoHandler.insertar(Vehiculo(1, "Toyota"))
        vehiculoHandler.insertar(Vehiculo(2, "Nissan"))
        vehiculoHandler.insertar(Vehiculo(3, "Honda"))

        // Insertar algunos importadores de prueba (NUI de 10 caracteres)
        importadorHandler.insertar(Importador(
            nui = "12345678-0",
            nombre = "Juan",
            apellido = "Pérez",
            apellidoCasada = null,
            genero = "M",
            fechaNacimiento = "1990-05-15",
            direccion = "Col. Miramonte, San Salvador",
            correoElectronico = "juan.perez@email.com",
            nuiResponsable = null
        ))
        importadorHandler.insertar(Importador(
            nui = "87654321-9",
            nombre = "María",
            apellido = "González",
            apellidoCasada = "de López",
            genero = "F",
            fechaNacimiento = "1985-03-22",
            direccion = "Res. Santa Elena, Santa Tecla",
            correoElectronico = "maria.gonzalez@email.com",
            nuiResponsable = null
        ))
        // OPCIONES CRUD
        val opcionHandler = OpcionCrudHandler(this)

// Importador
        opcionHandler.insertar("010", "Menu Importador", 0)
        opcionHandler.insertar("011", "Agregar Importador", 1)
        opcionHandler.insertar("012", "Editar Importador", 2)
        opcionHandler.insertar("013", "Consultar Importador", 4)
        opcionHandler.insertar("014", "Eliminar Importador", 3)

// Telefono Importador
        opcionHandler.insertar("020", "Menu Telefono", 0)
        opcionHandler.insertar("021", "Agregar Telefono", 1)
        opcionHandler.insertar("022", "Editar Telefono", 2)
        opcionHandler.insertar("023", "Consultar Telefono", 4)
        opcionHandler.insertar("024", "Eliminar Telefono", 3)

// Venta
        opcionHandler.insertar("030", "Menu Venta", 0)
        opcionHandler.insertar("031", "Agregar Venta", 1)
        opcionHandler.insertar("032", "Editar Venta", 2)
        opcionHandler.insertar("033", "Consultar Venta", 4)
        opcionHandler.insertar("034", "Eliminar Venta", 3)

// ACCESO USUARIO
        val accesoHandler = AccesoUsuarioHandler(this)

// Admin tiene acceso a todo
        accesoHandler.insertar("010", "admin")
        accesoHandler.insertar("011", "admin")
        accesoHandler.insertar("012", "admin")
        accesoHandler.insertar("013", "admin")
        accesoHandler.insertar("014", "admin")
        accesoHandler.insertar("020", "admin")
        accesoHandler.insertar("021", "admin")
        accesoHandler.insertar("022", "admin")
        accesoHandler.insertar("023", "admin")
        accesoHandler.insertar("024", "admin")
        accesoHandler.insertar("030", "admin")
        accesoHandler.insertar("031", "admin")
        accesoHandler.insertar("032", "admin")
        accesoHandler.insertar("033", "admin")
        accesoHandler.insertar("034", "admin")

// Consultor solo puede consultar, sin eliminar
        usuarioHandler.insertarUsuario("consultor", "con01")
        accesoHandler.insertar("010", "consultor")
        accesoHandler.insertar("013", "consultor")
        accesoHandler.insertar("020", "consultor")
        accesoHandler.insertar("023", "consultor")
        accesoHandler.insertar("030", "consultor")
        accesoHandler.insertar("033", "consultor")

        Toast.makeText(this, getString(R.string.db_filled), Toast.LENGTH_LONG).show()
    }
}
