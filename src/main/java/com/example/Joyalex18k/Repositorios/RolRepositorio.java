package com.example.Joyalex18k.Repositorios;

import com.example.Joyalex18k.Modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Rol.
 * Proporciona métodos CRUD y la posibilidad de definir consultas personalizadas.
 */
@Repository // Indica que esta interfaz es un componente de repositorio de Spring
public interface RolRepositorio extends JpaRepository<Rol, Long> {
    // Puedes añadir métodos de consulta personalizados, por ejemplo, para buscar un rol por su nombre
    // Optional<Rol> findByNombre(String nombre);
}