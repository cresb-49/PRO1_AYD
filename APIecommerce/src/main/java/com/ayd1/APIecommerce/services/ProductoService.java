package com.ayd1.APIecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.dto.LoginDto;
import com.ayd1.APIecommerce.repositories.ProductoRepository;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

@Service
public class ProductoService extends com.ayd1.APIecommerce.services.Service {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto createProducto(Producto producto) throws Exception{
        //validamos el producto
        this.validar(producto);
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
