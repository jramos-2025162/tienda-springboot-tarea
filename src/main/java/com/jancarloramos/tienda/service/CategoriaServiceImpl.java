package com.jancarloramos.tienda.service;

import com.jancarloramos.tienda.entity.Categoria;
import com.jancarloramos.tienda.exception.ResourceNotFoundException;
import com.jancarloramos.tienda.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService{
    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria crear(Categoria categoria) {
        categoria.setIdCategoria(null);
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizar(Integer id, Categoria categoria) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
        categoriaExistente.setDescripcionCategoria(categoria.getDescripcionCategoria());
        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Categoría no encontrado con ID:" + id));
    }

    @Override
    public void eliminar(Integer id) {
        if(!categoriaRepository.existsById(id)){
            throw new ResourceNotFoundException("categoría no encontrado con Id" + id);
        }
        categoriaRepository.deleteById(id);
    }
}
