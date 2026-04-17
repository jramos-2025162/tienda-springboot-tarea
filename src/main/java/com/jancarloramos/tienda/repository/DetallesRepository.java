package com.jancarloramos.tienda.repository;

import com.jancarloramos.tienda.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesRepository extends JpaRepository<DetallePedido, Integer> {
}
