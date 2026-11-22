package cl.tiendamascotas.inventario.vista

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cl.tiendamascotas.inventario.databinding.ActivityFormularioBinding
import cl.tiendamascotas.inventario.datos.MascotasHelper
import cl.tiendamascotas.inventario.modelo.Producto

class FormularioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioBinding
    private lateinit var helper: MascotasHelper
    private var idProducto = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        helper = MascotasHelper(this)

        idProducto = intent.getIntExtra("id", -1)
        if (idProducto != -1) {
            binding.etNombre.setText(intent.getStringExtra("nombre"))
            binding.etCategoria.setText(intent.getStringExtra("categoria"))
            binding.etPrecio.setText(intent.getDoubleExtra("precio", 0.0).toString())
            binding.etStock.setText(intent.getIntExtra("stock", 0).toString())
            binding.btnEliminar.visibility = View.VISIBLE
        }

        binding.btnGuardar.setOnClickListener { guardar() }
        binding.btnEliminar.setOnClickListener { eliminar() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun guardar() {
        val nombre = binding.etNombre.text.toString().trim()
        val categoria = binding.etCategoria.text.toString().trim()
        val textoPrecio = binding.etPrecio.text.toString().trim()
        val textoStock = binding.etStock.text.toString().trim()

        var valido = true

        if (nombre.isEmpty()) {
            binding.etNombre.error = "Ingrese el nombre"
            valido = false
        }
        if (categoria.isEmpty()) {
            binding.etCategoria.error = "Ingrese la categoría"
            valido = false
        }

        val precio = textoPrecio.replace(',', '.').toDoubleOrNull()
        if (textoPrecio.isEmpty()) {
            binding.etPrecio.error = "Ingrese el precio"
            valido = false
        } else if (precio == null || precio < 0) {
            binding.etPrecio.error = "Precio no válido"
            valido = false
        }

        val stock = textoStock.toIntOrNull()
        if (textoStock.isEmpty()) {
            binding.etStock.error = "Ingrese el stock"
            valido = false
        } else if (stock == null || stock < 0) {
            binding.etStock.error = "Stock no válido"
            valido = false
        }

        if (!valido || precio == null || stock == null) {
            return
        }

        val id = if (idProducto == -1) 0 else idProducto
        val producto = Producto(id, nombre, categoria, precio, stock)

        val correcto = if (idProducto == -1) {
            helper.insertar(producto) != -1L
        } else {
            helper.actualizar(producto) > 0
        }

        if (correcto) {
            Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "No se pudo guardar el producto", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminar() {
        if (helper.eliminar(idProducto) > 0) {
            Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "No se pudo eliminar el producto", Toast.LENGTH_SHORT).show()
        }
    }
}
