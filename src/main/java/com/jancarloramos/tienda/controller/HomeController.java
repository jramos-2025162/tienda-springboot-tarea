package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Usuario;
import com.jancarloramos.tienda.repository.UsuarioRepository;
import com.jancarloramos.tienda.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    public HomeController(AuthService authService, UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("No se encontró el usuario con correo: " + email));

        model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
        model.addAttribute("apellidoUsuario", usuario.getApellidoUsuario());
        return "home";
    }

    @GetMapping("/")
    public String redirectHome(){
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            @RequestParam("rol") String rol,
            Model model){

        if (bindingResult.hasErrors()){
            return "register";
        }

        boolean registroExitoso = authService.register(
                usuario.getNombreUsuario(),
                usuario.getApellidoUsuario(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getEdadUsuario(),
                rol
        );

        if (!registroExitoso) {
            model.addAttribute("errorRegistro", "El email ya ha sido registrado anteriormente");
            return "register";
        }

        return "redirect:/login?registered";
    }

    // Metodo creado por IA para el acceso denedago para los usuarios
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}