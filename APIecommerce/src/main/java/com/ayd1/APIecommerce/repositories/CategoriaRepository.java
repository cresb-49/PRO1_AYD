package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.Categoria;
import java.util.List;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    public Optional<Categoria> findOneByNombre(String nombre);

    public boolean existsByNombre(String nombre);

    public boolean existsByNombreAndIdNot(String nombre, Long id);

    public List<Categoria> findByPadre(Categoria padre);

    public Long deleteCategoriaById(Long id);
}
