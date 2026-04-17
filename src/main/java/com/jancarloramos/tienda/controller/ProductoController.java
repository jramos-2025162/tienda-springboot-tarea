package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Producto;
import com.jancarloramos.tienda.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/get")
    public List<Producto> listar(){
        return productoService.listar();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto){
        Producto creado = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/getId/{id}")
    public Producto buscarPorId(@PathVariable Integer id){
        return productoService.buscarPorId(id);
    }

    @PutMapping("/put/{id}")
    public Producto actualizar(@PathVariable Integer id, @Valid @RequestBody Producto producto){
       return productoService.actualizar(id, producto);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id){
        productoService.eliminar(id);
    }
}
