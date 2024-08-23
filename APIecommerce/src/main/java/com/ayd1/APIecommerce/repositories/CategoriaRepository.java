package com.ayd1.APIecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayd1.APIecommerce.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    
} 
