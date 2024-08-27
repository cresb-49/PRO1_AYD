package com.ayd1.APIecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayd1.APIecommerce.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    public Optional<Categoria> findOneByNombre(String nombre);
} 
