package cl.tiendamascotas.inventario.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.tiendamascotas.inventario.databinding.ItemProductoBinding
import cl.tiendamascotas.inventario.modelo.Producto

class ProductoAdapter(
    private var productos: List<Producto>,
    private val alTocar: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(val vista: ItemProductoBinding) : RecyclerView.ViewHolder(vista.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val vista = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.vista.txtNombre.text = producto.nombre
        holder.vista.txtCategoria.text = "Categoría: " + producto.categoria
        holder.vista.txtPrecio.text = "Precio: $" + producto.precio
        holder.vista.txtStock.text = "Stock: " + producto.stock
        holder.vista.root.setOnClickListener { alTocar(producto) }
    }

    override fun getItemCount(): Int = productos.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        productos = nuevaLista
        notifyDataSetChanged()
    }
}
