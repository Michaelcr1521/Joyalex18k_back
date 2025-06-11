package com.example.Joyalex18k.Modelos;


import com.fasterxml.jackson.annotation.JsonIgnore; // Importar para evitar recursión
import jakarta.persistence.*;
import java.util.List;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "roles") // Mapea esta entidad a la tabla "roles" en la base de datos
public class Rol {

    @Id // Marca el campo 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática de ID (autoincrement)
    private Long id; // id : int(11) en BD, usamos Long en Java por convención

    @Column(nullable = false, unique = true, length = 50) // nombre : varchar(50) en BD, no nulo y único
    private String nombre;

    // Relación bidireccional con Usuario
    // Un rol puede tener muchos usuarios (OneToMany)
    // Usamos @JsonIgnore aquí para evitar la serialización de la lista de usuarios
    // cuando se devuelve un objeto Rol. Esto previene referencias circulares.
    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY) // 'rol' es el campo en la entidad Usuario que mapea esta relación
    @JsonIgnore // Importante: Evita la recursión infinita en la serialización JSON
    private List<Usuario> usuarios;

    // Constructor vacío requerido por JPA
    public Rol() {
    }

    // Constructor para inicializar un Rol
    public Rol(String nombre) {
        this.nombre = nombre;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}