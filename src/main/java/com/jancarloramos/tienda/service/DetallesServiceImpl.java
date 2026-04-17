package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.DetallePedido;
import com.jancarloramos.tienda.exception.ResourceNotFoundException;
import com.jancarloramos.tienda.repository.DetallesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallesServiceImpl implements DetallesService{
    private final DetallesRepository detallesRepository;

    public DetallesServiceImpl(DetallesRepository detallesRepository) {
        this.detallesRepository = detallesRepository;
    }

    @Override
    public List<DetallePedido> listar() {
        return detallesRepository.findAll();
    }

    @Override
    public DetallePedido crear(DetallePedido detallePedido) {
        detallePedido.setIdDetalle(null);
        return detallesRepository.save(detallePedido);
    }

    @Override
    public DetallePedido actualizar(Integer id, DetallePedido detallePedido) {
        DetallePedido detallesExistentes = buscarPorId(id);
        detallesExistentes.setCantidadPedido(detallePedido.getCantidadPedido());
        detallesExistentes.setPrecioUnitario(detallePedido.getPrecioUnitario());
        detallesExistentes.setIdPedido(detallePedido.getIdPedido());
        detallesExistentes.setIdProducto(detallePedido.getIdProducto());
        return detallesRepository.save(detallesExistentes);
    }

    @Override
    public DetallePedido buscarPorId(Integer id) {
        return detallesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Detalles no encontrados con ID :" + id));
    }

    @Override
    public void eliminar(Integer id) {
        if(!detallesRepository.existsById(id)){
            throw new ResourceNotFoundException("Detalles no encontrados con ID: " + id);
        }
        detallesRepository.deleteById(id);

    }
}
