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
                // Guardar usuario en SharedPreferences
                val sharedPref = getSharedPreferences("nombre_usuario", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("username", user)
                    apply()
                }

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
        val entities = listOf(
            "Importador", "Telefono", "Venta", "Vehiculo", "Medio Transporte",
            "Bodega", "Seccion", "Ubicacion", "Taller", "Reparacion",
            "Movimiento", "Importacion", "Estado Vehicular", "Gasto Adicional"
        )

        for ((index, entity) in entities.withIndex()) {
            val prefix = (index + 1).toString().padStart(2, '0')
            opcionHandler.insertar("${prefix}0", "Menu $entity", 0)
            opcionHandler.insertar("${prefix}1", "Agregar $entity", 1)
            opcionHandler.insertar("${prefix}2", "Editar $entity", 2)
            opcionHandler.insertar("${prefix}3", "Consultar $entity", 4)
            opcionHandler.insertar("${prefix}4", "Eliminar $entity", 3)
        }

        // ACCESO USUARIO
        val accesoHandler = AccesoUsuarioHandler(this)

        // Admin tiene acceso a todo
        for (i in 1..entities.size) {
            val prefix = i.toString().padStart(2, '0')
            for (j in 0..4) {
                accesoHandler.insertar("$prefix$j", "admin")
            }
        }

        // Consultor solo tiene acceso a Menu y Consultar
        usuarioHandler.insertarUsuario("con", "con01")
        for (i in 1..entities.size) {
            val prefix = i.toString().padStart(2, '0')
            accesoHandler.insertar("${prefix}0", "consultor") // Menu
            accesoHandler.insertar("${prefix}3", "consultor") // Consultar
        }

        Toast.makeText(this, getString(R.string.db_filled), Toast.LENGTH_LONG).show()
    }
}
