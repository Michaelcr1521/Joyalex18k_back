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

    public Producto crearProducto(Producto producto) {
        if (producto.getCreadoPor() != null && producto.getCreadoPor().getId() != null) {
            Optional<Usuario> usuarioExistente = usuarioRepositorio.findById(producto.getCreadoPor().getId());
            if (usuarioExistente.isPresent()) {
                producto.setCreadoPor(usuarioExistente.get());
                return productoRepositorio.save(producto);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario creador con ID " + producto.getCreadoPor().getId() + " no encontrado.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto debe tener un usuario creador válido.");
        }
    }

    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        return productoRepositorio.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(productoDetalles.getNombre());
                    productoExistente.setDescripcion(productoDetalles.getDescripcion());
                    productoExistente.setPrecio(productoDetalles.getPrecio());
                    productoExistente.setStock(productoDetalles.getStock());
                    productoExistente.setImagenUrl(productoDetalles.getImagenUrl());
                    return productoRepositorio.save(productoExistente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto con ID " + id + " no encontrado."));
    }

    public void eliminarProducto(Long id) {
        productoRepositorio.findById(id)
                .ifPresentOrElse(productoRepositorio::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto con ID " + id + " no encontrado."); });
    }
}
