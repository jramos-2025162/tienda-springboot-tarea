package com.jancarloramos.tienda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Size(max = 60, message = "El nombre no puede exceder 60 caracteres")
    @Column(name = "nombre_categoria")
    private String nombreCategoria;

    @Size(max = 150, message = "La descripción no puede exceder 150 caracteres")
    @Column(name = "descripcion_categoria")
    private String descripcionCategoria;

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }

    public String getDescripcionCategoria() { return descripcionCategoria; }
    public void setDescripcionCategoria(String descripcionCategoria) { this.descripcionCategoria = descripcionCategoria; }
}