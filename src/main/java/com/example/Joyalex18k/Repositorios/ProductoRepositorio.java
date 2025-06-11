package com.example.Joyalex18k.Repositorios;

import com.example.Joyalex18k.Modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Producto.
 * Proporciona métodos CRUD (Crear, Leer, Actualizar, Eliminar)
 * y la posibilidad de definir consultas personalizadas.
 */
@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    // Puedes agregar métodos personalizados si lo necesitas, por ejemplo:

    // List<Producto> findByNombreContainingIgnoreCase(String nombre);
    // List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    // List<Producto> findByStockGreaterThan(int cantidad);
}
