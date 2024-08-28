package com.ayd1.APIecommerce.repositories;

<<<<<<< HEAD
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

=======
>>>>>>> bffb9f9b0d6b9ec8c0e63be9e05d08a1ee924b9c
import com.ayd1.APIecommerce.models.Categoria;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

<<<<<<< HEAD
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    public Optional<Categoria> findOneByNombre(String nombre);
} 
=======
    public Optional<Categoria> findOneByNombre(String nombre);
}
>>>>>>> bffb9f9b0d6b9ec8c0e63be9e05d08a1ee924b9c
