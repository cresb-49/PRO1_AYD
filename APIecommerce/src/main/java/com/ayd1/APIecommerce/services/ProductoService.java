package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Imagen;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayd1.APIecommerce.models.Producto;
import com.ayd1.APIecommerce.models.dto.ProductoDto;
import com.ayd1.APIecommerce.repositories.CategoriaRepository;
import com.ayd1.APIecommerce.repositories.ProductoRepository;
import com.ayd1.APIecommerce.tools.mappers.ProductoMapper;
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
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProductoDto> getProductosDto() {
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

    public List<ProductoDto> getDiezProductosMasReciente() {
        List<Producto> findAll = productoRepository.findTop10ByOrderByCreatedAtDesc();
        return findAll.stream()
                .map(producto -> {
                    ProductoDto productoDto = ProductoMapper.INSTANCE.productoToProductoDto(producto);
                    // Aquí conviertes las imágenes a URLs o nombres de archivos
                    productoDto.convertImagenesToUrls(producto.getImagenes());
                    return productoDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de productos
     *
     * @return
     */
    public List<Producto> getProductos() {
        List<Producto> findAll = productoRepository.findAll();
        return findAll;
    }

    /**
     * Retorna todos los productos que tengan 5 o menos exsitencias.
     *
     * @return
     * @throws Exception
     */
    public List<ProductoDto> getProductosConBajaExistencia() throws Exception {
        List<Producto> productos = productoRepository.findAll();

        if (productos.isEmpty()) {
            return new ArrayList<>();
        }

        List<Producto> productosConBajaExistencia = productos.stream()
                .filter(producto -> producto.getStock() <= 5)
                .filter(producto -> producto.getDeletedAt() == null)
                .collect(Collectors.toList());

        List<ProductoDto> productosDto
                = this.listProductoToListProdutoDto(productosConBajaExistencia);
        return productosDto;
    }

    private List<ProductoDto> listProductoToListProdutoDto(List<Producto> productos) {
        return productos.stream()
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
        Producto prodEliminar
                = productoRepository.findById(id).orElse(null);

        //verificar si existe
        if (prodEliminar == null) {
            throw new Exception("No hemos encontrado el producto.");
        }
        //eliminamos el producto
        Long delete = this.productoRepository.deleteProductoById(prodEliminar.getId());

        if (delete > 0) {
            return "Se elimino el producto con exito.";
        }
        throw new Exception("No pudimos eliminar el producto, inténtalo más tarde.");
    }

    @Transactional
    public ProductoDto updateProducto(Producto productoActualizar) throws Exception {
        //verificar el id
        this.validarAtributo(productoActualizar, "id");
        //buscar por id
        Producto productoEncontrado
                = this.productoRepository.findById(productoActualizar.getId())
                        .orElse(null);
        if (productoEncontrado == null) { //validar si existe
            throw new Exception("No hemos encontrado el producto.");
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

    /**
     * Obtiene todos los productos que pertenecen a una categoria o a alguna de
     * las categorias hijas de la misma.
     *
     * @param categoria
     * @return
     */
    public List<ProductoDto> buscarPorCategoria(Categoria categoria) {
        // Obtener todas las categorías relacionadas
        List<Categoria> categoriasDescendientes = obtenerCategoriasDescendientes(categoria);
        // Buscar productos por las categorías obtenidas
        return this.listProductoToListProdutoDto(
                productoRepository.findByCategoriaIn(
                        categoriasDescendientes));
    }

    /**
     * Busca todos los decendientes de la categoria padre
     *
     * @param categoria
     * @return
     */
    private List<Categoria> obtenerCategoriasDescendientes(Categoria categoria) {
        List<Categoria> categoriasDescendientes = new ArrayList<>();
        categoriasDescendientes.add(categoria); // Agrega la categoría actual

        // Obtener las categorías hijas
        List<Categoria> categoriasHijas = this.categoriaRepository.findByPadre(categoria);
        for (Categoria hija : categoriasHijas) {
            categoriasDescendientes.addAll(obtenerCategoriasDescendientes(hija)); // Recursivamente agrega las hijas
        }
        return categoriasDescendientes;
    }

    public List<ProductoDto> buscarPorNombre(String nombre) {
        return this.listProductoToListProdutoDto(
                productoRepository.findByNombreContaining(nombre));
    }

    public List<ProductoDto> buscarPorRangoDePrecio(Double precioMin, Double precioMax) {
        return this.listProductoToListProdutoDto(
                productoRepository.findByPrecioBetween(precioMin, precioMax));
    }
}
