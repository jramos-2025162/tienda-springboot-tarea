package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Categoria;
import com.jancarloramos.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/categoria")
public class CategoriaViewController {

    private final CategoriaService categoriaService;

    public CategoriaViewController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("categoriaForm", new Categoria());
        return "categoria-lista";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("categoriaForm") Categoria categoria,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listar());
            model.addAttribute("mostrarModal", true);
            return "categoria-lista";
        }
        categoriaService.crear(categoria);
        redirectAttributes.addFlashAttribute("mensajeExito", "Categoría guardada correctamente");
        return "redirect:/categoria";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("categoriaForm", categoriaService.buscarPorId(id));
        model.addAttribute("modoEdicion", true);
        model.addAttribute("mostrarModal", true);
        return "categoria-lista";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @Valid @ModelAttribute("categoriaForm") Categoria categoria,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listar());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("mostrarModal", true);
            return "categoria-lista";
        }
        categoriaService.actualizar(id, categoria);
        redirectAttributes.addFlashAttribute("mensajeExito", "Categoría actualizada correctamente");
        return "redirect:/categoria";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        categoriaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Categoría eliminada correctamente");
        return "redirect:/categoria";
    }
}