package com.example.Joyalex18k.Modelos;

import jakarta.persistence.*;
import java.math.BigDecimal; // Para el tipo DECIMAL(10,2) en BD
import java.time.LocalDateTime; // Si quieres un campo de fecha de creación/actualización

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "productos") // Mapea esta entidad a la tabla "productos" en la base de datos
public class Producto {

    @Id // Marca el campo 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática de ID
    private Long id; // id : int(11) en BD

    @Column(nullable = false, length = 100) // nombre : varchar(100)
    private String nombre;

    @Column(columnDefinition = "TEXT") // descripcion : text
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2) // precio : decimal(10,2)
    private BigDecimal precio; // Usamos BigDecimal para manejar dinero con precisión

    @Column(nullable = false) // stock : int(11)
    private Integer stock;

    // --- NUEVO CAMPO: URL de la imagen ---
    @Column(length = 255) // imagen_url : varchar(255)
    private String imagenUrl; // Campo para almacenar la URL de la imagen del producto

    // Relación ManyToOne con Usuario (campo creado_por)
    // Un producto es creado por un Usuario
    @ManyToOne(fetch = FetchType.EAGER) // Carga el Usuario inmediatamente con el Producto
    @JoinColumn(name = "creado_por", nullable = false) // creado_por : int(11) en BD, clave foránea
    private Usuario creadoPor; // Nombre de la propiedad en Java

    // Puedes añadir campos de auditoría si lo deseas (ej. fecha de creación)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist // Se ejecuta antes de persistir la entidad (primer guardado)
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    // Constructor vacío requerido por JPA
    public Producto() {
    }

    // Constructor para inicializar un Producto (Actualizado para incluir imagenUrl)
    public Producto(String nombre, String descripcion, BigDecimal precio, Integer stock, String imagenUrl, Usuario creadoPor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl; // Añadir al constructor
        this.creadoPor = creadoPor;
    }

    // --- Getters y Setters (Añadidos para imagenUrl) ---
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    // Nuevo Getter para imagenUrl
    public String getImagenUrl() {
        return imagenUrl;
    }

    // Nuevo Setter para imagenUrl
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}