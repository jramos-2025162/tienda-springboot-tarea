package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();
    Usuario crear(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuario);
    Usuario buscarPorId(Integer id);
    void eliminar(Integer id);
}
