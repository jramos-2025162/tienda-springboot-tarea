package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Pedido;
import com.jancarloramos.tienda.service.PedidoService;
import com.jancarloramos.tienda.service.UsuarioService; // IMPORTANTE
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedido")
public class PedidoViewController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;

    public PedidoViewController(PedidoService pedidoService, UsuarioService usuarioService) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        model.addAttribute("pedidoForm", new Pedido());

        model.addAttribute("usuarios", usuarioService.listar());
        return "pedido-lista";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("pedidoForm") Pedido pedido,
                          BindingResult result,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pedidos", pedidoService.listar());
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("mostrarModal", true);
            return "pedido-lista";
        }

        try {
            pedidoService.crear(pedido);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pedido guardado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar: Verifique que el usuario sea válido.");
        }

        return "redirect:/pedido";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        model.addAttribute("pedidoForm", pedidoService.buscarPorId(id));
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("modoEdicion", true);
        model.addAttribute("mostrarModal", true);
        return "pedido-lista";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Integer id,
                             @Valid @ModelAttribute("pedidoForm") Pedido pedido,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pedidos", pedidoService.listar());
            model.addAttribute("usuarios", usuarioService.listar());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("mostrarModal", true);
            return "pedido-lista";
        }

        pedido.setIdPedido(id);
        pedidoService.actualizar(id, pedido);
        redirectAttributes.addFlashAttribute("mensajeExito", "Pedido actualizado correctamente");
        return "redirect:/pedido";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        pedidoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Pedido eliminado correctamente");
        return "redirect:/pedido";
    }
}