package sv.edu.ues.fia.proyecto_pdm.ubicacion

import android.content.ContentValues
import android.content.Context
import sv.edu.ues.fia.proyecto_pdm.DatabaseContract
import sv.edu.ues.fia.proyecto_pdm.DatabaseHelper
import java.time.LocalDate

class UbicacionHandler(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)

    fun insertar(ubicacion: Ubicacion_vehiculo): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UbicacionEntry.COLUMN_ID_SECCION, ubicacion.idSeccion)
            put(DatabaseContract.UbicacionEntry.COLUMN_FECHA, ubicacion.fechaAsignacion?.toString())
            put(DatabaseContract.UbicacionEntry.COLUMN_ACTIVA, if (ubicacion.activa == true) 1 else 0)
        }
        return db.insert(DatabaseContract.UbicacionEntry.TABLE_NAME, null, values)
    }

    fun consultar(id: Int): Ubicacion_vehiculo? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.UbicacionEntry.TABLE_NAME,
            null,
            "${DatabaseContract.UbicacionEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        var ubicacion: Ubicacion_vehiculo? = null
        if (cursor.moveToFirst()) {
            val fechaStr = cursor.getString(2)
            val fecha = if (fechaStr != null) LocalDate.parse(fechaStr) else null
            ubicacion = Ubicacion_vehiculo(
                cursor.getInt(0),
                cursor.getInt(1),
                fecha,
                cursor.getInt(3) == 1
            )
        }
        cursor.close()
        return ubicacion
    }

    fun actualizar(ubicacion: Ubicacion_vehiculo): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UbicacionEntry.COLUMN_ID_SECCION, ubicacion.idSeccion)
            put(DatabaseContract.UbicacionEntry.COLUMN_FECHA, ubicacion.fechaAsignacion?.toString())
            put(DatabaseContract.UbicacionEntry.COLUMN_ACTIVA, if (ubicacion.activa == true) 1 else 0)
        }
        return db.update(
            DatabaseContract.UbicacionEntry.TABLE_NAME,
            values,
            "${DatabaseContract.UbicacionEntry.COLUMN_ID} = ?",
            arrayOf(ubicacion.idUbicacion.toString())
        )
    }

    fun eliminar(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.UbicacionEntry.TABLE_NAME,
            "${DatabaseContract.UbicacionEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }
}
