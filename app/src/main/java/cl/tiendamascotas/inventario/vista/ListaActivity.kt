package cl.tiendamascotas.inventario.vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cl.tiendamascotas.inventario.adaptador.ProductoAdapter
import cl.tiendamascotas.inventario.databinding.ActivityListaBinding
import cl.tiendamascotas.inventario.datos.MascotasHelper

class ListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding
    private lateinit var helper: MascotasHelper
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helper = MascotasHelper(this)

        adapter = ProductoAdapter(emptyList()) { producto ->
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra("id", producto.id)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("categoria", producto.categoria)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("stock", producto.stock)
            startActivity(intent)
        }
        binding.listaProductos.layoutManager = LinearLayoutManager(this)
        binding.listaProductos.adapter = adapter

        binding.btnAgregar.setOnClickListener {
            startActivity(Intent(this, FormularioActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val productos = helper.listar()
        adapter.actualizarLista(productos)
        if (productos.isEmpty()) {
            binding.txtVacio.visibility = View.VISIBLE
        } else {
            binding.txtVacio.visibility = View.GONE
        }
    }
}
