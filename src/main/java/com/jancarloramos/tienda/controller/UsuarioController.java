package com.jancarloramos.tienda.controller;

import com.jancarloramos.tienda.entity.Usuario;
import com.jancarloramos.tienda.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/get")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crear(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/getId/{id}")
    public Usuario buscar(@PathVariable Integer id){
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/put/{id}")
    public Usuario actualizar(@PathVariable Integer id, @Valid @RequestBody Usuario usuario){
        return usuarioService.actualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id){
        usuarioService.eliminar(id);

    }
}

