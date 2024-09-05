/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
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
    @NotNull(message = "El pago contra entrega no puede estar nulo.")
    private Boolean pagoContraEntrega;
    @NotNull(message = "La direccion no debe estar nula.")
    @NotBlank(message = "La direccion no debe estar en blanco.")
    private String direccion;

    public VentaRequest(Long idCompradador, Boolean consumidorFinal, List<ProductoVentaRequest> productos, Boolean retiroEnTienda, Boolean pagoContraEntrega, String direccion) {
        this.idCompradador = idCompradador;
        this.consumidorFinal = consumidorFinal;
        this.productos = productos;
        this.retiroEnTienda = retiroEnTienda;
        this.pagoContraEntrega = pagoContraEntrega;
        this.direccion = direccion;
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

    public Boolean getPagoContraEntrega() {
        return pagoContraEntrega;
    }

    public void setPagoContraEntrega(Boolean pagoContraEntrega) {
        this.pagoContraEntrega = pagoContraEntrega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
