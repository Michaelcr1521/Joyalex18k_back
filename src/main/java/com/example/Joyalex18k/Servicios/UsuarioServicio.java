package com.example.Joyalex18k.Servicios;

import com.example.Joyalex18k.Modelos.Rol;
import com.example.Joyalex18k.Modelos.Usuario;
import com.example.Joyalex18k.Repositorios.RolRepositorio;
import com.example.Joyalex18k.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    // ✅ Buscar usuario por correo para login
    public Usuario buscarPorEmail(String correo) {
        return usuarioRepositorio.findByCorreo(correo).orElse(null);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario debe tener un rol asociado con un ID válido.");
        }

        Optional<Rol> rolExistente = rolRepositorio.findById(usuario.getRol().getId());
        if (!rolExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con ID " + usuario.getRol().getId() + " no encontrado.");
        }
        usuario.setRol(rolExistente.get());

        if (usuarioRepositorio.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario '" + usuario.getNombreUsuario() + "' ya está en uso.");
        }
        if (usuarioRepositorio.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico '" + usuario.getCorreo() + "' ya está en uso.");
        }

        return usuarioRepositorio.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioDetalles) {
        return usuarioRepositorio.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNombreUsuario(usuarioDetalles.getNombreUsuario());
                    usuarioExistente.setCorreo(usuarioDetalles.getCorreo());

                    if (usuarioDetalles.getRol() != null && usuarioDetalles.getRol().getId() != null) {
                        Optional<Rol> nuevoRol = rolRepositorio.findById(usuarioDetalles.getRol().getId());
                        if (nuevoRol.isPresent()) {
                            usuarioExistente.setRol(nuevoRol.get());
                        } else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol con ID " + usuarioDetalles.getRol().getId() + " no encontrado.");
                        }
                    }
                    return usuarioRepositorio.save(usuarioExistente);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con ID " + id + " no encontrado."));
    }

    public void eliminarUsuario(Long id) {
        usuarioRepositorio.findById(id)
                .ifPresentOrElse(usuarioRepositorio::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario con ID " + id + " no encontrado."); });
    }
}
