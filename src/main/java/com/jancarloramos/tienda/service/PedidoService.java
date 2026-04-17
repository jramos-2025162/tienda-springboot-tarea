package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.Pedido;
import java.util.List;

public interface PedidoService {
    List<Pedido> listar();
    Pedido crear(Pedido pedido);
    Pedido actualizar(Integer id, Pedido pedido);
    Pedido buscarPorId(Integer id);
    void eliminar(Integer id);
}
