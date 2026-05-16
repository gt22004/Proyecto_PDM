package sv.edu.ues.fia.proyecto_pdm.estadovehicular

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import sv.edu.ues.fia.proyecto_pdm.DatabaseContract
import sv.edu.ues.fia.proyecto_pdm.DatabaseHelper

class EstadoVehicularHandler(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)

    fun insertar(estado: EstadoVehicular): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_ID_VEHICULO, estado.idVehiculo)
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_DESCRIPCION, estado.descripcion)
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_IMAGEN, estado.imagen)
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_FECHA, estado.fecha)
        }
        return db.insert(DatabaseContract.EstadoVehicularEntry.TABLE_NAME, null, values)
    }

    fun consultar(id: Int): EstadoVehicular? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.EstadoVehicularEntry.TABLE_NAME,
            null,
            "${DatabaseContract.EstadoVehicularEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        var estado: EstadoVehicular? = null
        if (cursor.moveToFirst()) {
            estado = cursorToEstado(cursor)
        }
        cursor.close()
        return estado
    }

    fun actualizar(estado: EstadoVehicular): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_ID_VEHICULO, estado.idVehiculo)
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_DESCRIPCION, estado.descripcion)
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_IMAGEN, estado.imagen)
            put(DatabaseContract.EstadoVehicularEntry.COLUMN_FECHA, estado.fecha)
        }
        return db.update(
            DatabaseContract.EstadoVehicularEntry.TABLE_NAME,
            values,
            "${DatabaseContract.EstadoVehicularEntry.COLUMN_ID} = ?",
            arrayOf(estado.idEstado.toString())
        )
    }

    fun eliminar(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.EstadoVehicularEntry.TABLE_NAME,
            "${DatabaseContract.EstadoVehicularEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun obtenerEstadosPorVehiculo(idVehiculo: Int): List<EstadoVehicular> {
        val lista = mutableListOf<EstadoVehicular>()
        val db = dbHelper.readableDatabase
        // Seleccionamos solo las columnas necesarias para el historial, excluyendo la imagen
        val columnas = arrayOf(
            DatabaseContract.EstadoVehicularEntry.COLUMN_ID,
            DatabaseContract.EstadoVehicularEntry.COLUMN_ID_VEHICULO,
            DatabaseContract.EstadoVehicularEntry.COLUMN_DESCRIPCION,
            DatabaseContract.EstadoVehicularEntry.COLUMN_FECHA
        )
        val cursor = db.query(
            DatabaseContract.EstadoVehicularEntry.TABLE_NAME,
            columnas,
            "${DatabaseContract.EstadoVehicularEntry.COLUMN_ID_VEHICULO} = ?",
            arrayOf(idVehiculo.toString()),
            null, null, "${DatabaseContract.EstadoVehicularEntry.COLUMN_FECHA} DESC"
        )
        if (cursor.moveToFirst()) {
            do {
                val estado = EstadoVehicular(
                    idEstado = cursor.getInt(0),
                    idVehiculo = cursor.getInt(1),
                    descripcion = cursor.getString(2),
                    fecha = cursor.getString(3),
                    imagen = null // No cargamos la imagen aquí para evitar el error de CursorWindow
                )
                lista.add(estado)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    private fun cursorToEstado(cursor: Cursor): EstadoVehicular {
        return EstadoVehicular(
            idEstado = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstadoVehicularEntry.COLUMN_ID)),
            idVehiculo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EstadoVehicularEntry.COLUMN_ID_VEHICULO)),
            descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstadoVehicularEntry.COLUMN_DESCRIPCION)),
            imagen = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseContract.EstadoVehicularEntry.COLUMN_IMAGEN)),
            fecha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EstadoVehicularEntry.COLUMN_FECHA))
        )
    }
}
