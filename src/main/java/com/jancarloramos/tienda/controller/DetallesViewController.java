package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.DetallePedido;
import com.jancarloramos.tienda.service.DetallesService;
import com.jancarloramos.tienda.service.PedidoService; // NUEVO
import com.jancarloramos.tienda.service.ProductoService; // NUEVO
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
    private final PedidoService pedidoService; // NUEVO
    private final ProductoService productoService; // NUEVO

    public DetallesViewController(DetallesService detallesService,
                                  PedidoService pedidoService,
                                  ProductoService productoService) {
        this.detallesService = detallesService;
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("detalles", detallesService.listar());
        model.addAttribute("detalleForm", new DetallePedido());
        cargarCombos(model); // Carga pedidos y productos
        return "detalles-lista";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("detalleForm") DetallePedido detalle,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("detalles", detallesService.listar());
            cargarCombos(model);
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
        cargarCombos(model);
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
            cargarCombos(model);
            model.addAttribute("modoEdicion", true);
            model.addAttribute("mostrarModal", true);
            return "detalles-lista";
        }
        detalle.setIdDetalle(id);
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

    // Método para no repetir código al llenar los selects
    private void cargarCombos(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        model.addAttribute("productos", productoService.listar());
    }
}