package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Imagen;
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
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.repositories.ProductoRepository;
import com.ayd1.APIecommerce.tools.mappers.ProductoMapper;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService extends com.ayd1.APIecommerce.services.Service {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDto> getProductos() {
        List<Producto> findAll = productoRepository.findAll();
        return findAll.stream()
                .map(producto -> {
                    ProductoDto productoDto = ProductoMapper.INSTANCE.productoToProductoDto(producto);
                    // Aquí conviertes las imágenes a URLs o nombres de archivos
                    productoDto.convertImagenesToUrls(producto.getImagenes());
                    return productoDto;
                })
                .collect(Collectors.toList());
    }

    public ProductoDto getProductoDto(Long id) throws Exception {
        if (id == null || id < 0) {
            throw new Exception("Id inválido.");
        }

        Optional<Producto> productoOp = productoRepository.findById(id);

        if (productoOp.isEmpty()) {
            throw new Exception("Producto no encontrado.");
        }

        Producto producto = productoOp.get();

        if (producto.getDeletedAt() != null) {
            throw new Exception("Producto ya ha sido eliminado.");
        }

        // Convertir Producto a ProductoDTO
        ProductoDto productoDto = ProductoMapper.INSTANCE.productoToProductoDto(producto);
        productoDto.convertImagenesToUrls(producto.getImagenes());
        return productoDto;
    }

    public Producto getProducto(Long id) throws Exception {
        if (id == null || id < 0) {
            throw new Exception("Id inválido.");
        }

        Optional<Producto> productoOp = productoRepository.findById(id);

        if (productoOp.isEmpty()) {
            throw new Exception("Producto no encontrado.");
        }

        Producto producto = productoOp.get();

        if (producto.getDeletedAt() != null) {
            throw new Exception("Producto ya ha sido eliminado.");
        }

        return producto;
    }

    @Transactional
    public ProductoDto createProducto(Producto producto, List<MultipartFile> imagenes) throws Exception {
        //validamos el producto   
        this.validar(producto);
        List<Imagen> imagenesCargadas = new ArrayList<>();
        //por cada archivo enviado, creamos un objeto Imagen y lo agregamos a la lista
        imagenes.forEach(file -> {
            try {
                Imagen imagenCargada = new Imagen(producto, file.getBytes(),
                        file.getContentType());
                imagenesCargadas.add(imagenCargada);
            } catch (IOException ex) {
                Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //asignamos las imagenes al producto
        producto.setImagenes(imagenesCargadas);
        //guardmaos el producto
        Producto save = productoRepository.save(producto);
        // Convertir Producto a ProductoDTO
        ProductoDto productoDto = ProductoMapper.INSTANCE.productoToProductoDto(save);
        productoDto.convertImagenesToUrls(save.getImagenes());
        return productoDto;
    }

    @Transactional
    public ProductoDto actualizarImagenes(Long actualizar, List<MultipartFile> imagenes) throws Exception {
        if (actualizar == null || actualizar <= 0) {
            throw new Exception("Id invalido.");
        }

        // Buscar producto
        Optional<Producto> busquedaProd = productoRepository.findById(actualizar);

        // Verificar si existe
        if (busquedaProd.isEmpty()) {
            throw new Exception("No hemos encontrado el producto.");
        }

        Producto prodActualizar = busquedaProd.get();

        if (prodActualizar.getDeletedAt() != null) {
            throw new Exception("Producto ya ha sido eliminado.");
        }

        // Limpiar la lista de imágenes existentes
        prodActualizar.getImagenes().clear();

        List<Imagen> imagenesCargadas = new ArrayList<>();
        imagenes.forEach(file -> {
            try {
                Imagen imagenCargada = new Imagen(prodActualizar, file.getBytes(), file.getContentType());
                imagenesCargadas.add(imagenCargada);
            } catch (IOException ex) {
                Logger.getLogger(ProductoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Asignar las imágenes al producto
        prodActualizar.getImagenes().addAll(imagenesCargadas);

        // Guardar el producto
        Producto save = productoRepository.save(prodActualizar);

        // Convertir Producto a ProductoDTO
        ProductoDto productoDto = ProductoMapper.INSTANCE.productoToProductoDto(save);
        productoDto.convertImagenesToUrls(save.getImagenes());
        return productoDto;
    }

    @Transactional
    public String eliminarProducto(Long id) throws Exception {

        if (id == null || id <= 0) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Id invalido.");
        }

        //buscar producti
        Optional<Producto> busquedaProd
                = productoRepository.findById(id);

        //verificar si existe
        if (busquedaProd.isEmpty()) {
            throw new Exception("No hemos encontrado el producto.");
        }

        //extraer el usuario
        Producto prodEliminar = busquedaProd.get();

        //vemos si el usuario no  ha sido eliminado
        if (prodEliminar.getDeletedAt() != null) {
            throw new Exception("Producto ya ha sido eliminado.");
        }

        //seteamos la fecha de eliminacion
        prodEliminar.setDeletedAt(Instant.now());
        //prodEliminar.setImagenes(new ArrayList<>());

        //editar el usuario
        Producto prodEliminado = this.productoRepository.save(prodEliminar);

        //mandamos a editar la password y comparamos si se hizo el cambio
        if (prodEliminado.getId() > 0) {
            return "Se elimino el producto con exito.";
        }
        throw new Exception("No pudimos eliminar el producto, inténtalo más tarde.");
    }

    @Transactional
    public ProductoDto updateProducto(Producto productoActualizar) throws Exception {
        //verificar el id
        if (productoActualizar.getId() == null || productoActualizar.getId() <= 0) {
            throw new Exception("Id inválido.");
        }
        //buscar por id
        Optional<Producto> busquedaProducto = this.productoRepository.findById(productoActualizar.getId());
        if (busquedaProducto.isEmpty()) { //validar si existe
            throw new Exception("No hemos encontrado el producto.");
        }
        //extraer el producto
        Producto productoEncontrado = busquedaProducto.get();
        //verificar eliminacion
        if (productoEncontrado.getDeletedAt() != null) {
            throw new Exception("Producto ya ha sido eliminado.");
        }
        //seteamos las listas
        productoActualizar.setImagenes(productoEncontrado.getImagenes());
        productoActualizar.setLineaVentas(productoEncontrado.getLineaVentas());
        productoActualizar.setMovimientos(productoEncontrado.getMovimientos());
        //validar la entrada
        this.validar(productoActualizar);

        Producto save = this.productoRepository.save(productoActualizar);

        if (save.getId() > 0) {
            // Convertir Producto a ProductoDTO
            ProductoDto productoDto = ProductoMapper.INSTANCE.productoToProductoDto(save);
            productoDto.convertImagenesToUrls(save.getImagenes());
            return productoDto;
        }
        throw new Exception("No pudimos actualizar el prodcuto, inténtalo más tarde.");
    }

    public List<Producto> buscarPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    // Método para buscar productos por nombre
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }
}
