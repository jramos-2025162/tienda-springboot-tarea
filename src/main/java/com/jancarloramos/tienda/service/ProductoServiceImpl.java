package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.Producto;
import com.jancarloramos.tienda.exception.ResourceNotFoundException;
import com.jancarloramos.tienda.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto crear(Producto producto) {
        producto.setIdProducto(null);
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Integer id, Producto producto) {
        return productoRepository.findById(id)
                .map(existente -> {
                    existente.setNombreProducto(producto.getNombreProducto());
                    existente.setPrecioProducto(producto.getPrecioProducto());
                    existente.setStockProducto(producto.getStockProducto());
                    existente.setIdCategoria(producto.getIdCategoria());
                    return productoRepository.save(existente);
                }).orElseThrow(() -> new ResourceNotFoundException("No se encontró el ID: " + id));
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    public void eliminar(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
}