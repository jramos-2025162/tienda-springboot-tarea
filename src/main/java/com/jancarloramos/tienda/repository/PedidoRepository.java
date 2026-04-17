package com.jancarloramos.tienda.repository;

import com.jancarloramos.tienda.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
}
