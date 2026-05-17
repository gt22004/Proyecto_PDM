package sv.edu.ues.fia.proyecto_pdm.gastos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import sv.edu.ues.fia.proyecto_pdm.DatabaseContract
import sv.edu.ues.fia.proyecto_pdm.DatabaseHelper

class GastoAdicionalHandler(context: Context) {
    private val dbHelper = DatabaseHelper.getInstance(context)

    fun insertar(gasto: GastoAdicional): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_ID_VEHICULO, gasto.idVehiculo)
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_CONCEPTO, gasto.concepto)
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_MONTO, gasto.monto)
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_FECHA, gasto.fechaGasto)
        }
        return db.insert(DatabaseContract.GastoAdicionalEntry.TABLE_NAME, null, values)
    }

    fun consultar(id: Int): GastoAdicional? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.GastoAdicionalEntry.TABLE_NAME,
            null,
            "${DatabaseContract.GastoAdicionalEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        var gasto: GastoAdicional? = null
        if (cursor.moveToFirst()) {
            gasto = cursorToGasto(cursor)
        }
        cursor.close()
        return gasto
    }

    fun actualizar(gasto: GastoAdicional): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_ID_VEHICULO, gasto.idVehiculo)
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_CONCEPTO, gasto.concepto)
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_MONTO, gasto.monto)
            put(DatabaseContract.GastoAdicionalEntry.COLUMN_FECHA, gasto.fechaGasto)
        }
        return db.update(
            DatabaseContract.GastoAdicionalEntry.TABLE_NAME,
            values,
            "${DatabaseContract.GastoAdicionalEntry.COLUMN_ID} = ?",
            arrayOf(gasto.idGasto.toString())
        )
    }

    fun eliminar(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.GastoAdicionalEntry.TABLE_NAME,
            "${DatabaseContract.GastoAdicionalEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun obtenerGastosPorVehiculo(idVehiculo: Int): List<GastoAdicional> {
        val lista = mutableListOf<GastoAdicional>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseContract.GastoAdicionalEntry.TABLE_NAME,
            null,
            "${DatabaseContract.GastoAdicionalEntry.COLUMN_ID_VEHICULO} = ?",
            arrayOf(idVehiculo.toString()),
            null, null, "${DatabaseContract.GastoAdicionalEntry.COLUMN_FECHA} DESC"
        )
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursorToGasto(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    private fun cursorToGasto(cursor: Cursor): GastoAdicional {
        return GastoAdicional(
            idGasto = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.GastoAdicionalEntry.COLUMN_ID)),
            idVehiculo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.GastoAdicionalEntry.COLUMN_ID_VEHICULO)),
            concepto = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.GastoAdicionalEntry.COLUMN_CONCEPTO)),
            monto = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.GastoAdicionalEntry.COLUMN_MONTO)),
            fechaGasto = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.GastoAdicionalEntry.COLUMN_FECHA))
        )
    }
}
