package sv.edu.ues.fia.proyecto_pdm.usuarios

import android.content.ContentValues
import android.content.Context
import sv.edu.ues.fia.proyecto_pdm.DatabaseContract
import sv.edu.ues.fia.proyecto_pdm.DatabaseHelper

class AccesoUsuarioHandler(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)

    fun insertar(idOpcion: String, idUsuario: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.AccesoUsuarioEntry.COLUMN_ID_OPCION, idOpcion)
            put(DatabaseContract.AccesoUsuarioEntry.COLUMN_ID_USUARIO, idUsuario)
        }
        return db.insert(DatabaseContract.AccesoUsuarioEntry.TABLE_NAME, null, values)
    }

    fun tieneAcceso(idUsuario: String, idOpcion: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.AccesoUsuarioEntry.TABLE_NAME,
            null,
            "${DatabaseContract.AccesoUsuarioEntry.COLUMN_ID_USUARIO} = ? AND " +
                    "${DatabaseContract.AccesoUsuarioEntry.COLUMN_ID_OPCION} = ?",
            arrayOf(idUsuario, idOpcion),
            null, null, null
        )
        val tiene = cursor.count > 0
        cursor.close()
        return tiene
    }

    fun obtenerOpcionesPorUsuario(idUsuario: String): List<String> {
        val lista = mutableListOf<String>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.AccesoUsuarioEntry.TABLE_NAME,
            arrayOf(DatabaseContract.AccesoUsuarioEntry.COLUMN_ID_OPCION),
            "${DatabaseContract.AccesoUsuarioEntry.COLUMN_ID_USUARIO} = ?",
            arrayOf(idUsuario),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}