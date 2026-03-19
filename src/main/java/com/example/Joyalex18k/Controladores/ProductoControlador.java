package com.example.Joyalex18k.Controladores;

import com.example.Joyalex18k.Modelos.Producto;
import com.example.Joyalex18k.Servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
// CORRECCIÓN: Permite que cualquier dispositivo de tu red local acceda a los datos
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos = productoServicio.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoServicio.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado."));
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoServicio.crearProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        Producto productoActualizado = productoServicio.actualizarProducto(id, productoDetalles);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}