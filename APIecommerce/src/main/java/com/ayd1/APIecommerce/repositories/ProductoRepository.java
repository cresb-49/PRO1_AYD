package com.ayd1.APIecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayd1.APIecommerce.models.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
