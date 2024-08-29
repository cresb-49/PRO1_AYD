package com.ayd1.APIecommerce.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.repositories.CategoriaRepository;

@org.springframework.stereotype.Service
public class CategoriaService extends Service {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getCategorias() throws Exception {
        
        List<Categoria> listaCategorias = (List<Categoria>) categoriaRepository.findAll();
        
        if (listaCategorias == null) {
            throw new Exception("No se encontró ninguna categoría");    
        }
        return listaCategorias;
    }

    public Categoria getCategoria(Long id) throws Exception {
        
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        
        if (categoria == null) {
            throw new Exception("Categoria no encontrada");    
        }

        if (categoria.getDeletedAt() != null) {
            throw new Exception("Categoria eliminada previamente");
        }

        return categoria;
    }

    @Transactional
    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria updateCategoria(Long id, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);

        if (categoriaExistente != null) {
            return categoriaRepository.save(categoriaExistente);
        }
        return null;
    }

    @Transactional
    public Categoria deleteCategoria(Long id) throws Exception {
        Categoria delCategoria = categoriaRepository.findById(id).orElse(null);
        
        // Si no encontro la categoria con el id
        if (delCategoria == null) {
            throw new Exception("No se encontró ninguna categoría para eliminar");
        }

        return delCategoria;
    }

}
