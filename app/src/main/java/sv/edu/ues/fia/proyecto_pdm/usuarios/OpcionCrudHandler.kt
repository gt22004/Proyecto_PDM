package sv.edu.ues.fia.proyecto_pdm.usuarios

import android.content.ContentValues
import android.content.Context
import sv.edu.ues.fia.proyecto_pdm.DatabaseContract
import sv.edu.ues.fia.proyecto_pdm.DatabaseHelper

class OpcionCrudHandler(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)

    fun insertar(idOpcion: String, descripcion: String, numCrud: Int): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.OpcionCrudEntry.COLUMN_ID, idOpcion)
            put(DatabaseContract.OpcionCrudEntry.COLUMN_DESCRIPCION, descripcion)
            put(DatabaseContract.OpcionCrudEntry.COLUMN_NUM_CRUD, numCrud)
        }
        return db.insert(DatabaseContract.OpcionCrudEntry.TABLE_NAME, null, values)
    }

    fun obtenerTodas(): List<Triple<String, String, Int>> {
        val lista = mutableListOf<Triple<String, String, Int>>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseContract.OpcionCrudEntry.TABLE_NAME}", null
        )
        if (cursor.moveToFirst()) {
            do {
                lista.add(Triple(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}