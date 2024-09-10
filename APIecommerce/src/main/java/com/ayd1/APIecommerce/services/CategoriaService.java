package com.ayd1.APIecommerce.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
    public Categoria createCategoria(Categoria categoria) throws Exception {
        this.validar(categoria);

        if (this.categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new Exception("Ya hay una categoria con el mismo nombre.");
        }

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria updateCategoria(Categoria categoria) throws Exception {

        if (categoria.getId() == null || categoria.getId() <= 0) {
            throw new Exception("Id invalido");
        }

        Categoria categoriaSearch
                = categoriaRepository.findById(categoria.getId()).orElse(null);

        if (categoriaSearch == null) {
            throw new Exception("No se encontró la categoría para actualizar.");
        }

        if (categoria.getPadre() != null
                && categoria.getPadre().getId() != null
                && categoriaSearch.getId().longValue() == categoria.getPadre().getId().longValue()) {
            throw new Exception("La categoria padre no puede ser la misma que la hija.");
        }

        if (this.categoriaRepository.existsByNombreAndIdNot(categoria.getNombre(),
                categoriaSearch.getId())) {
            throw new Exception("Ya existe una categoria con el mismo nombre.");
        }

        categoria.setProductos(categoriaSearch.getProductos());

        Categoria categoriaActualizada = this.categoriaRepository.save(categoria);

        if (categoriaActualizada.getId() > 0) {
            return categoriaActualizada;
        }
        throw new Exception("No se pudo actualizar la categoría");
    }

    @Transactional
    public String deleteCategoria(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Id inválido");
        }
        Categoria categoria = categoriaRepository.findById(id)
                .orElse(null);
        // Si no encontro la categoria con el id
        if (categoria == null) {
            throw new Exception("No se encontró ninguna categoría para eliminar");
        }
        Long delete = this.categoriaRepository.deleteCategoriaById(categoria.getId());
        if (delete > 0) {
            return "La categoría se eliminó con éxito";
        }
        throw new Exception("No se pudo eliminar la categoría");
    }

}
