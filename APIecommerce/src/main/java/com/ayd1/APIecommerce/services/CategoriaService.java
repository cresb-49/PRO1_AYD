package com.ayd1.APIecommerce.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Usuario;
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
    public Categoria updateCategoria(Categoria categoria) throws Exception {
        
        if (categoria.getId() == null || categoria.getId() <=0) {
            throw new Exception("Id invalido");
        }

        Optional<Categoria> categoriaExistente = categoriaRepository.findById(categoria.getId());

        if (categoriaExistente.isEmpty()) {
            throw new Exception("No se encontró la categoría para actualizar.");
        }

        Categoria categoriaValida =  categoriaExistente.get();

        if (categoriaValida.getDeletedAt() != null) {
            throw new Exception("Categoria ya eliminada");
        }

        categoria.setProductos(categoriaValida.getProductos());
   
        Categoria categoriaActualizada = this.categoriaRepository.save(categoria);

        if (categoriaActualizada.getId() > 0) {
            return categoriaActualizada;
        }
        throw new Exception("No se pudo actualizar la categoría");
    }
    
    @Transactional
    public String deleteCategoria(Long id) throws Exception {
        
        if (id==null || id<=0) {
            throw new Exception("Id inválido");
        }

        Optional<Categoria> posibleCategoria = categoriaRepository.findById(id);
        // Si no encontro la categoria con el id
        if (posibleCategoria.isEmpty()) {
            throw new Exception("No se encontró ninguna categoría para eliminar");
        }

        Categoria delCategoria = posibleCategoria.get();

        if (delCategoria.getDeletedAt() != null) {
            throw new Exception("Categoría eliminada previamente");
        }

        delCategoria.setDeletedAt(Instant.now());

        Categoria updateDelCategoria = this.categoriaRepository.save(delCategoria);

        if (updateDelCategoria.getId() > 0) {
            return "La categoría se eliminó con éxito";
        }
        throw new Exception("No se pudo eliminar la categoría");
    }

}
