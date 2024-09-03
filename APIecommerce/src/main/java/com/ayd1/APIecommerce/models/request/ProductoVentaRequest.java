/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ProductoVentaRequest {

    @NotNull(message = "Id de producto no puede estar vacio.")
    private Long id;
    @Min(value = 1, message = "El cantidad debe tener como valor mínimo 1.")
    private Long cantidad;

    public ProductoVentaRequest(Long id, Long cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public ProductoVentaRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

}
