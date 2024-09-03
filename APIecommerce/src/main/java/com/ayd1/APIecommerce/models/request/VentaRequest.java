/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class VentaRequest {

    @NotNull(message = "Id del comprador no puede estar nulo.")
    private Long idCompradador;
    @NotNull(message = "CF no puede estar vacio.")
    private Boolean consumidorFinal;
    @NotNull(message = "Los productos deben existir.")
    private List<ProductoVentaRequest> productos;
    @NotNull(message = "El tipo de retiro no puede estar vacio.")
    private Boolean retiroEnTienda;

    public VentaRequest(Long idCompradador, Boolean consumidorFinal, List<ProductoVentaRequest> productos, Boolean retiroEnTienda) {
        this.idCompradador = idCompradador;
        this.consumidorFinal = consumidorFinal;
        this.productos = productos;
        this.retiroEnTienda = retiroEnTienda;
    }

    public VentaRequest() {
    }

    public Long getIdCompradador() {
        return idCompradador;
    }

    public void setIdCompradador(Long idCompradador) {
        this.idCompradador = idCompradador;
    }

    public Boolean getConsumidorFinal() {
        return consumidorFinal;
    }

    public void setConsumidorFinal(Boolean consumidorFinal) {
        this.consumidorFinal = consumidorFinal;
    }

    public List<ProductoVentaRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVentaRequest> productos) {
        this.productos = productos;
    }

    public Boolean getRetiroEnTienda() {
        return retiroEnTienda;
    }

    public void setRetiroEnTienda(Boolean retiroEnTienda) {
        this.retiroEnTienda = retiroEnTienda;
    }

}
