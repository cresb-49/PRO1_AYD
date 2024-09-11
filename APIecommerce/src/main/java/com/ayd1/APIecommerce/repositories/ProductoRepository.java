package com.ayd1.APIecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    public Long deleteProductoById(Long producto);

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByCategoriaIn(List<Categoria> categoria);

    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);

    List<Producto> findTop10ByOrderByCreatedAtDesc();
}
