package com.ayd1.APIecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    public Long deleteProductoById(Long producto);

    public List<Producto> findByCategoria(Categoria categoria);

    public List<Producto> findByNombreContaining(String nombre);

    public List<Producto> findByCategoriaIn(List<Categoria> categoria);

    public List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);

    public List<Producto> findTop10ByOrderByCreatedAtDesc();

    public List<Producto> findByStockLessThan(int stock);
}
