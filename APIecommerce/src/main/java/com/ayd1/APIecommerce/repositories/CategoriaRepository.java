package com.ayd1.APIecommerce.repositories;

import com.ayd1.APIecommerce.models.Categoria;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    public Optional<Categoria> findOneByNombre(String nombre);
}
