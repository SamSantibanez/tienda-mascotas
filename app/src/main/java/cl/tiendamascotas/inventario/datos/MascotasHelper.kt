package cl.tiendamascotas.inventario.datos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cl.tiendamascotas.inventario.modelo.Producto

class MascotasHelper(context: Context) :
    SQLiteOpenHelper(context, "tienda_mascotas.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE productos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "categoria TEXT NOT NULL, " +
                    "precio REAL NOT NULL, " +
                    "stock INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, versionAnterior: Int, versionNueva: Int) {
        db.execSQL("DROP TABLE IF EXISTS productos")
        onCreate(db)
    }

    fun insertar(producto: Producto): Long {
        val valores = ContentValues()
        valores.put("nombre", producto.nombre)
        valores.put("categoria", producto.categoria)
        valores.put("precio", producto.precio)
        valores.put("stock", producto.stock)
        return writableDatabase.insert("productos", null, valores)
    }

    fun listar(): List<Producto> {
        val lista = mutableListOf<Producto>()
        val cursor = readableDatabase.rawQuery(
            "SELECT id, nombre, categoria, precio, stock FROM productos ORDER BY nombre", null
        )
        while (cursor.moveToNext()) {
            lista.add(
                Producto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getInt(4)
                )
            )
        }
        cursor.close()
        return lista
    }

    fun actualizar(producto: Producto): Int {
        val valores = ContentValues()
        valores.put("nombre", producto.nombre)
        valores.put("categoria", producto.categoria)
        valores.put("precio", producto.precio)
        valores.put("stock", producto.stock)
        return writableDatabase.update(
            "productos", valores, "id = ?", arrayOf(producto.id.toString())
        )
    }

    fun eliminar(id: Int): Int {
        return writableDatabase.delete("productos", "id = ?", arrayOf(id.toString()))
    }
}
