    package com.jancarloramos.tienda.service;

    import com.jancarloramos.tienda.entity.Usuario;
    import com.jancarloramos.tienda.exception.ResourceNotFoundException;
    import com.jancarloramos.tienda.repository.UsuarioRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class UsuarioServiceImpl implements UsuarioService{
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
        public Usuario actualizar(Integer id, Usuario usuario) {
            Usuario usuarioExistente = buscarPorId(id);
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
            usuarioExistente.setApellidoUsuario(usuario.getApellidoUsuario());
            usuarioExistente.setEdadUsuario(usuario.getEdadUsuario());
            return usuarioRepository.save(usuarioExistente);
        }

        @Override
        public Usuario buscarPorId(Integer id) {
                return usuarioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuario con ID, no encoontrado:" + id));
        }

        @Override
        public void eliminar(Integer id) {
            if(!usuarioRepository.existsById(id)){
                throw new ResourceNotFoundException("Usuario no encontrado con Id" + id);
            }
            usuarioRepository.deleteById(id);
        }
    }
