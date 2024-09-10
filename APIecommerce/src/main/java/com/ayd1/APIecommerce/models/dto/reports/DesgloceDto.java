/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.dto.reports;

import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class DesgloceDto {

    private Integer cantidad;
    private String precio;
    private String producto;
    private String descripcion;
    private String impuesto;
    private String total;

    public DesgloceDto(Integer cantidad, String precio, String producto, String descripcion,
            String total, String impuesto) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.producto = producto;
        this.descripcion = descripcion == null ? "" : descripcion;
        this.total = total;
        this.impuesto = impuesto;
    }

    public DesgloceDto() {
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;

    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

}
