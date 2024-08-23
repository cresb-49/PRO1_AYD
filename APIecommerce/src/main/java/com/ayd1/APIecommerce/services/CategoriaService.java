package com.ayd1.APIecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.repositories.CategoriaRepository;

@org.springframework.stereotype.Service
public class CategoriaService extends Service {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria getCategoria(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);

        if (categoriaExistente != null) {
            return categoriaRepository.save(categoriaExistente);
        }
        return null;
    }

}
