package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.DetallePedido;

import java.util.List;

public interface DetallesService {
    List<DetallePedido> listar();
    DetallePedido crear(DetallePedido detallePedido);
    DetallePedido actualizar(Integer id, DetallePedido detallePedido);
    DetallePedido buscarPorId(Integer id);
    void eliminar(Integer id);
}
