package com.jancarloramos.tienda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @NotBlank(message = "La fecha no puede estar vacía")
    @Column(name = "fecha_pedido")
    private String fechaPedido;

    @NotNull(message = "El total del pedido no puede ir vacío")
    @Column(name = "total_pedido")
    private BigDecimal totalPedido;

    @NotNull(message = "El ID del usuario no puede ir vacío")
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public Integer getIdPedido() { return idPedido; }
    public void setIdPedido(Integer idPedido) { this.idPedido = idPedido; }

    public String getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(String fechaPedido) { this.fechaPedido = fechaPedido; }

    public BigDecimal getTotalPedido() { return totalPedido; }
    public void setTotalPedido(BigDecimal totalPedido) { this.totalPedido = totalPedido; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
}