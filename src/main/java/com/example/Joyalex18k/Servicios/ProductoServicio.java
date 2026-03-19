package com.example.Joyalex18k.Servicios;

import com.example.Joyalex18k.Modelos.Producto;
import com.example.Joyalex18k.Modelos.Usuario;
import com.example.Joyalex18k.Repositorios.ProductoRepositorio;
import com.example.Joyalex18k.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepositorio.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepositorio.findById(id);
    }

    /**
     * CREAR PRODUCTO
     * Corregido para asegurar que la URL de imagen y el usuario se procesen bien.
     */
    public Producto crearProducto(Producto producto) {
        // 1. Validar que venga un usuario asignado
        if (producto.getCreadoPor() == null || producto.getCreadoPor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe asignar un usuario creador válido.");
        }

        // 2. Buscar el usuario real en la base de datos para evitar errores de persistencia
        Usuario usuarioReal = usuarioRepositorio.findById(producto.getCreadoPor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El usuario con ID " + producto.getCreadoPor().getId() + " no existe en la base de datos."));

        // 3. Vincular el usuario completo al producto
        producto.setCreadoPor(usuarioReal);

        // 4. Guardar (JPA usará el setImagenUrl automáticamente gracias al @JsonProperty que pusimos en el Modelo)
        return productoRepositorio.save(producto);
    }

    /**
     * ACTUALIZAR PRODUCTO
     * Asegura que el campo de la imagen no se pierda al editar.
     */
    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        return productoRepositorio.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(productoDetalles.getNombre());
                    productoExistente.setDescripcion(productoDetalles.getDescripcion());
                    productoExistente.setPrecio(productoDetalles.getPrecio());
                    productoExistente.setStock(productoDetalles.getStock());

                    // CRÍTICO: Seteamos la URL de la imagen que viene de React
                    productoExistente.setImagenUrl(productoDetalles.getImagenUrl());

                    return productoRepositorio.save(productoExistente);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado."));
    }

    public void eliminarProducto(Long id) {
        if (!productoRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede eliminar: Producto no encontrado.");
        }
        productoRepositorio.deleteById(id);
    }
}