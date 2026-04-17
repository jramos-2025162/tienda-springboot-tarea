package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.DetallePedido;
import com.jancarloramos.tienda.service.DetallesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/detalles")
public class DetallesController {
    private final DetallesService detallesService;

    public DetallesController(DetallesService detallesService) {
        this.detallesService = detallesService;
    }

    @GetMapping("/get")
    public List<DetallePedido> listar(){
        return detallesService.listar();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DetallePedido> crear(@Valid @RequestBody DetallePedido detallePedido){
        DetallePedido creado = detallesService.crear(detallePedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/getId/{id}")
    public DetallePedido buscarPorId(@PathVariable Integer id){
        return detallesService.buscarPorId(id);
    }

    @PutMapping("/put/{id}")
    public DetallePedido actualizar(@PathVariable Integer id, @Valid @RequestBody DetallePedido detallePedido){
        return detallesService.actualizar(id, detallePedido);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id){
        detallesService.eliminar(id);
    }
}
