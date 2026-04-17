package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.DetallePedido;
import com.jancarloramos.tienda.service.DetallesService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/detalles")
public class DetallesViewController {

    private final DetallesService detallesService;

    public DetallesViewController(DetallesService detallesService) {
        this.detallesService = detallesService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalles", detallesService.listar());
        model.addAttribute("detalleForm", new DetallePedido());
        return "detalles-lista";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("detalleForm") DetallePedido detalle,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("detalles", detallesService.listar());
            model.addAttribute("mostrarModal", true);
            return "detalles-lista";
        }
        detallesService.crear(detalle);
        redirectAttributes.addFlashAttribute("mensajeExito", "Detalle guardado correctamente");
        return "redirect:/detalles";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("detalles", detallesService.listar());
        model.addAttribute("detalleForm", detallesService.buscarPorId(id));
        model.addAttribute("modoEdicion", true);
        model.addAttribute("mostrarModal", true);
        return "detalles-lista";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @Valid @ModelAttribute("detalleForm") DetallePedido detalle,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("detalles", detallesService.listar());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("mostrarModal", true);
            return "detalles-lista";
        }
        detallesService.actualizar(id, detalle);
        redirectAttributes.addFlashAttribute("mensajeExito", "Detalle actualizado correctamente");
        return "redirect:/detalles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        detallesService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Detalle eliminado correctamente");
        return "redirect:/detalles";
    }
}
