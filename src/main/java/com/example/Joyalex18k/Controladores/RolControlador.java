package com.example.Joyalex18k.Controladores;

import com.example.Joyalex18k.Modelos.Rol;
import com.example.Joyalex18k.Servicios.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controlador REST para la gestión de roles.
 * Expone endpoints para realizar operaciones CRUD sobre la entidad Rol.
 */
@RestController // Indica que esta clase es un controlador REST de Spring
@RequestMapping("/api/roles") // Define la ruta base para todos los endpoints de este controlador
public class RolControlador {

    @Autowired // Inyecta una instancia de RolServicio
    private RolServicio rolServicio;

    /**
     * Endpoint para obtener todos los roles.
     * URL: GET /api/roles
     * @return ResponseEntity con la lista de roles y estado HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodosLosRoles() {
        List<Rol> roles = rolServicio.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles); // Retorna 200 OK con la lista de roles
    }

    /**
     * Endpoint para obtener un rol por su ID.
     * URL: GET /api/roles/{id}
     * @param id El ID del rol a buscar.
     * @return ResponseEntity con el rol encontrado y estado HTTP 200 (OK),
     * o 404 (Not Found) si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerRolPorId(@PathVariable Long id) {
        return rolServicio.obtenerRolPorId(id)
                .map(ResponseEntity::ok) // Si se encuentra, retorna 200 OK con el rol
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con ID " + id + " no encontrado.")); // Si no, lanza 404
    }

    /**
     * Endpoint para crear un nuevo rol.
     * URL: POST /api/roles
     * @param rol El objeto Rol enviado en el cuerpo de la solicitud.
     * @return ResponseEntity con el rol creado y estado HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol nuevoRol = rolServicio.crearRol(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED); // Retorna 201 Created
    }

    /**
     * Endpoint para actualizar un rol existente.
     * URL: PUT /api/roles/{id}
     * @param id El ID del rol a actualizar.
     * @param rolDetalles El objeto Rol con los detalles actualizados.
     * @return ResponseEntity con el rol actualizado y estado HTTP 200 (OK).
     * Retorna 404 (Not Found) si el rol no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @RequestBody Rol rolDetalles) {
        Rol rolActualizado = rolServicio.actualizarRol(id, rolDetalles);
        return ResponseEntity.ok(rolActualizado); // Retorna 200 OK
    }

    /**
     * Endpoint para eliminar un rol por su ID.
     * URL: DELETE /api/roles/{id}
     * @param id El ID del rol a eliminar.
     * @return ResponseEntity con estado HTTP 204 (No Content) si se elimina con éxito.
     * Retorna 404 (Not Found) si el rol no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        rolServicio.eliminarRol(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}