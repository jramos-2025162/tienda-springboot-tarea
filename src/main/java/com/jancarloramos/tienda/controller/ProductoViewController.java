package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Producto;
import com.jancarloramos.tienda.service.CategoriaService;
import com.jancarloramos.tienda.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/producto")
public class ProductoViewController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoViewController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("productoForm", new Producto());
        return "producto-lista";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("productoForm") Producto producto,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.listar());
            model.addAttribute("categorias", categoriaService.listar());
            model.addAttribute("mostrarModal", true);
            return "producto-lista";
        }
        productoService.crear(producto);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto guardado correctamente");
        return "redirect:/producto";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("productoForm", productoService.buscarPorId(id));
        model.addAttribute("modoEdicion", true);
        model.addAttribute("mostrarModal", true);
        return "producto-lista";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @Valid @ModelAttribute("productoForm") Producto producto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.listar());
            model.addAttribute("categorias", categoriaService.listar());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("mostrarModal", true);
            return "producto-lista";
        }
        productoService.actualizar(id, producto);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto actualizado correctamente");
        return "redirect:/producto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        productoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto eliminado correctamente");
        return "redirect:/producto";
    }
}