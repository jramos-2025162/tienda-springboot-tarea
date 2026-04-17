package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Categoria;
import com.jancarloramos.tienda.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/get")
    public List<Categoria> listar(){
        return categoriaService.listar();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria){
        Categoria creado = categoriaService.crear(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/getId/{id}")
    public Categoria obtenerPorId(@PathVariable Integer id){
       return categoriaService.buscarPorId(id);
    }

    @PutMapping("/put/{id}")
    public Categoria actualizar(@PathVariable Integer id, @Valid @RequestBody Categoria categoria){
        return categoriaService.actualizar(id, categoria);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id){
        categoriaService.eliminar(id);
    }


}
