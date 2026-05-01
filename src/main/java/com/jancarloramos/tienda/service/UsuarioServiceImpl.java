package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.Usuario;
import com.jancarloramos.tienda.exception.ResourceNotFoundException;
import com.jancarloramos.tienda.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario crear(Usuario usuario) {
        usuario.setIdUsuario(null);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario actualizar(Integer id, Usuario usuario) {
        Usuario usuarioExistente = buscarPorId(id);

        // Actualizamos los campos básicos
        usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
        usuarioExistente.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioExistente.setEdadUsuario(usuario.getEdadUsuario());

        // Actualizamos el Email (Importante para la persistencia)
        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            usuarioExistente.setEmail(usuario.getEmail());
        }

        // Actualizamos el Rol
        if (usuario.getRol() != null) {
            usuarioExistente.setRol(usuario.getRol());
        }

        // Mantenemos la contraseña existente si el formulario no envía una nueva
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            usuarioExistente.setPassword(usuario.getPassword());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID no encontrado: " + id));
    }

    @Override
    public void eliminar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con Id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}