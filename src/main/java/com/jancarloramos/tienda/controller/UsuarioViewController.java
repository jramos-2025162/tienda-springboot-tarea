package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Usuario;
import com.jancarloramos.tienda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/usuario")
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuarioForm", new Usuario());
        return "usuario-lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("usuarioForm", usuarioService.buscarPorId(id));
        model.addAttribute("modoEdicion", true);
        model.addAttribute("mostrarModal", true);
        return "usuario-lista";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable("id") Integer id,
                             @Valid @ModelAttribute("usuarioForm") Usuario usuario,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("mostrarModal", true);
            return "usuario-lista";
        }

        // Metodo creado por IA para forzar que el objeto tenga el ID de la URL para evitar errores
        usuario.setIdUsuario(id);
        usuarioService.actualizar(id, usuario);

        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario actualizado correctamente");

        return "redirect:/usuario";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente");
        return "redirect:/usuario";
    }
}
