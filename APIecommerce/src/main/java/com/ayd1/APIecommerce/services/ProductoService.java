package com.ayd1.APIecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.repositories.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id).orElse(null);
        if (productoExistente != null) {
            return productoRepository.save(productoExistente);
        }
        return null;
    }
}
