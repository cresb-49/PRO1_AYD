package com.ayd1.APIecommerce.repositories;

import java.util.Optional;
import com.ayd1.APIecommerce.models.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    public Optional<Categoria> findOneByNombre(String nombre);
}