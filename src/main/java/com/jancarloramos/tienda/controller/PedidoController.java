package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Pedido;
import com.jancarloramos.tienda.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/get")
    public List<Pedido> listar(){
        return pedidoService.listar();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido){
        Pedido creado = pedidoService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/getId/{id}")
    public Pedido buscarPorId(@PathVariable Integer id){
        return pedidoService.buscarPorId(id);
    }

    @PutMapping("/put/{id}")
    public Pedido actualizar(@PathVariable Integer id, @Valid @RequestBody Pedido pedido){
        return pedidoService.actualizar(id, pedido);
    }

    @DeleteMapping("/delete/{id}")
    public void eliminar(@PathVariable Integer id){
        pedidoService.eliminar(id);
    }

}
