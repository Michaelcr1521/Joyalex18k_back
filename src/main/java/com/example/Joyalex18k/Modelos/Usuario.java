package com.example.Joyalex18k.Modelos;



import com.fasterxml.jackson.annotation.JsonIgnore; // Importar para evitar recursión
import jakarta.persistence.*;
import java.util.List;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "usuarios") // Mapea esta entidad a la tabla "usuarios" en la base de datos
public class Usuario {

    @Id // Marca el campo 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática de ID
    private Long id; // id : int(11) en BD

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 100) // nombre_usuario : varchar(100)
    private String nombreUsuario;

    @Column(nullable = false, unique = true, length = 100) // correo : varchar(100)
    private String correo;

    @Column(nullable = false, length = 255) // contraseña : varchar(255)
    private String contrasena;

    // Relación ManyToOne con Rol
    // Un usuario pertenece a un Rol (rol_id)
    @ManyToOne(fetch = FetchType.EAGER) // Carga el Rol inmediatamente con el Usuario
    @JoinColumn(name = "rol_id", nullable = false) // rol_id : int(11) en BD, clave foránea
    private Rol rol;

    // Relación bidireccional con Producto (un usuario puede haber creado muchos productos)
    // Usamos @JsonIgnore para evitar recursión infinita cuando se serializa un Usuario.
    @OneToMany(mappedBy = "creadoPor", fetch = FetchType.LAZY) // 'creadoPor' es el campo en Producto que mapea esta relación
    @JsonIgnore // Importante: Evita la recursión infinita en la serialización JSON
    private List<Producto> productosCreados;

    // Constructor vacío requerido por JPA
    public Usuario() {
    }

    // Constructor para inicializar un Usuario (sin la lista de productos)
    public Usuario(String nombreUsuario, String correo, String contrasena, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Producto> getProductosCreados() {
        return productosCreados;
    }

    public void setProductosCreados(List<Producto> productosCreados) {
        this.productosCreados = productosCreados;
    }
}