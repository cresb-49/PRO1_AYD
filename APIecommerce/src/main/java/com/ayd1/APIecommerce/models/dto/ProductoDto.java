/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Imagen;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ProductoDto {

    private Long id;

    private String nombre;

    private Categoria categoria;

    private Integer stock;

    private Double precio;

    private String descripcion;

    private ArrayList<String> imagenesUrls;

    private Double porcentajeImpuesto;

    private Boolean habilitado;

    public ProductoDto(Long id, String nombre, Categoria categoria, Integer stock, Double precio, String descripcion, ArrayList<String> imagenesUrls, Double porcentajeImpuesto, Boolean habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenesUrls = imagenesUrls;
        this.porcentajeImpuesto = porcentajeImpuesto;
        this.habilitado = habilitado;
    }

    public ProductoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ArrayList<String> getImagenesUrls() {
        return imagenesUrls;
    }

    public void setImagenesUrls(ArrayList<String> imagenesUrls) {
        this.imagenesUrls = imagenesUrls;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public void setPorcentajeImpuesto(Double porcentajeImpuesto) {
        this.porcentajeImpuesto = porcentajeImpuesto;
    }
    
    public Double getPorcentajeImpuesto() {
        return porcentajeImpuesto;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void convertImagenesToUrls(List<Imagen> imagenes) {
        ArrayList<String> urls = new ArrayList<>();
        //por cada imagen construir la url
        for (Imagen item : imagenes) {
            String url = String.format(
                    "http://localhost:8080/api/imagenes/public/getImage/%s",
                    item.getId());
            urls.add(url);//anadir la url a las urls
        }
        this.imagenesUrls = urls;//igual la variabe global a la que construimos
    }
}
