
package cl.pedidos360.usuarios_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.pedidos360.usuarios_service.model.Usuario;
import cl.pedidos360.usuarios_service.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException(
                    "Ya existe un usuario registrado con ese correo");
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario datosActualizados) {

        return usuarioRepository.findById(id).map(usuario -> {

            usuario.setNombre(datosActualizados.getNombre());
            usuario.setCorreo(datosActualizados.getCorreo());
            usuario.setRol(datosActualizados.getRol());
            usuario.setActivo(datosActualizados.getActivo());

            return usuarioRepository.save(usuario);
        });
    }

    
}
