package sv.edu.ues.fia.proyecto_pdm.importador

import android.content.ContentValues
import android.content.Context
import sv.edu.ues.fia.proyecto_pdm.DatabaseContract
import sv.edu.ues.fia.proyecto_pdm.DatabaseHelper

class TelefonoImportadorHandler(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)

    fun insertar(telefono: TelefonoImportador): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TelefonoImportadorEntry.COLUMN_NUI, telefono.nui)
            put(DatabaseContract.TelefonoImportadorEntry.COLUMN_NUMERO, telefono.numero)
            put(DatabaseContract.TelefonoImportadorEntry.COLUMN_TIPO, telefono.tipoTelefono)
        }
        return db.insert(DatabaseContract.TelefonoImportadorEntry.TABLE_NAME, null, values)
    }

    fun buscar(id: Int): TelefonoImportador? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.TelefonoImportadorEntry.TABLE_NAME,
            null,
            "${DatabaseContract.TelefonoImportadorEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        var telefono: TelefonoImportador? = null
        if (cursor.moveToFirst()) {
            telefono = TelefonoImportador(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
        }
        cursor.close()
        return telefono
    }

    fun obtenerPorImportador(nui: String): List<TelefonoImportador> {
        val lista = mutableListOf<TelefonoImportador>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.TelefonoImportadorEntry.TABLE_NAME,
            null,
            "${DatabaseContract.TelefonoImportadorEntry.COLUMN_NUI} = ?",
            arrayOf(nui),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                lista.add(TelefonoImportador(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    fun actualizar(telefono: TelefonoImportador): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.TelefonoImportadorEntry.COLUMN_NUMERO, telefono.numero)
            put(DatabaseContract.TelefonoImportadorEntry.COLUMN_TIPO, telefono.tipoTelefono)
        }
        return db.update(
            DatabaseContract.TelefonoImportadorEntry.TABLE_NAME,
            values,
            "${DatabaseContract.TelefonoImportadorEntry.COLUMN_ID} = ?",
            arrayOf(telefono.idTelefono.toString())
        )
    }

    fun eliminar(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.TelefonoImportadorEntry.TABLE_NAME,
            "${DatabaseContract.TelefonoImportadorEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun obtenerTodos(): List<TelefonoImportador> {
        val lista = mutableListOf<TelefonoImportador>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseContract.TelefonoImportadorEntry.TABLE_NAME}", null
        )
        if (cursor.moveToFirst()) {
            do {
                lista.add(TelefonoImportador(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}