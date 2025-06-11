package com.example.Joyalex18k.Servicios;

import com.example.Joyalex18k.Modelos.Rol;
import com.example.Joyalex18k.Repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de roles.
 * Contiene la lógica de negocio para operaciones CRUD de roles.
 */
@Service // Indica que esta clase es un componente de servicio de Spring
public class RolServicio {

    @Autowired // Inyecta una instancia de RolRepositorio
    private RolRepositorio rolRepositorio;

    /**
     * Obtiene todos los roles.
     * @return Una lista de todos los roles.
     */
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepositorio.findAll();
    }

    /**
     * Obtiene un rol por su ID.
     * @param id El ID del rol.
     * @return Un Optional que contiene el rol si se encuentra, o vacío si no.
     */
    public Optional<Rol> obtenerRolPorId(Long id) {
        return rolRepositorio.findById(id);
    }

    /**
     * Crea un nuevo rol.
     * @param rol El objeto Rol a guardar.
     * @return El rol guardado.
     */
    public Rol crearRol(Rol rol) {
        return rolRepositorio.save(rol);
    }

    /**
     * Actualiza un rol existente.
     * @param id El ID del rol a actualizar.
     * @param rolDetalles Los detalles del rol actualizado.
     * @return El rol actualizado.
     * @throws ResponseStatusException Si el rol no se encuentra.
     */
    public Rol actualizarRol(Long id, Rol rolDetalles) {
        return rolRepositorio.findById(id)
                .map(rolExistente -> {
                    rolExistente.setNombre(rolDetalles.getNombre());
                    return rolRepositorio.save(rolExistente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con ID " + id + " no encontrado."));
    }

    /**
     * Elimina un rol por su ID.
     * @param id El ID del rol a eliminar.
     * @throws ResponseStatusException Si el rol no se encuentra.
     */
    public void eliminarRol(Long id) {
        rolRepositorio.findById(id)
                .ifPresentOrElse(rolRepositorio::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con ID " + id + " no encontrado."); });
    }
}