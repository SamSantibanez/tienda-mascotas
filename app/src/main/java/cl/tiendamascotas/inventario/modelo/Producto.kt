package cl.tiendamascotas.inventario.modelo

data class Producto(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
)
