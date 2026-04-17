package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.Usuario;
import com.jancarloramos.tienda.enumtypes.UserRole;
import com.jancarloramos.tienda.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public AuthService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }
    // Metodo creado con IA
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== INTENTO DE LOGIN CON: " + username + " ===");

        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> {
                    System.out.println("=== ERROR: El correo " + username + " no existe en la DB ===");
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        System.out.println("=== USUARIO ENCONTRADO EN DB. VALIDANDO CLAVE... ===");

        // Mantenemos el ROLE_ para que Spring Security reconozca los permisos
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities("ROLE_" + usuario.getRol().name())
                .build();
    }

    public boolean register(String nombreUsuario, String apellidoUsuario, String email, String password, Integer edad, String rol) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return false;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setApellidoUsuario(apellidoUsuario);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(passwordEncoder.encode(password));
        nuevoUsuario.setEdadUsuario(edad);

        try {
            nuevoUsuario.setRol(UserRole.valueOf(rol.toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {

            nuevoUsuario.setRol(UserRole.USUARIO);
        }

        usuarioRepository.save(nuevoUsuario);
        return true;
    }
}