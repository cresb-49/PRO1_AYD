/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "venta")
public class Venta extends Auditor {

    @Column(name = "valor_total", nullable = false)
    @Min(value = 0, message = "El valor total debe tener como valor mínimo 0.")
    private Double valorTotal;

    @Column(name = "cantidad_productos", nullable = false)
    @Min(value = 1, message = "El cantidad debe tener como valor mínimo 1.")
    private Integer cantidadProductos;

    @OneToMany(mappedBy = "venta", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonIgnore // Evita la serialización del usuario al serializar UsuarioRol
    private List<LineaVenta> lineaVentas;

    @OneToMany(mappedBy = "venta", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonIgnore // Evita la serialización del usuario al serializar UsuarioRol
    private List<DatosFacturacion> datosFacturacion;

    @OneToMany(mappedBy = "venta", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonIgnore // Evita la serialización del usuario al serializar UsuarioRol
    private List<Envio> envios;

    public Venta() {
    }

    public Venta(Long id) {
        super(id);
    }

    public Venta(Double valorTotal, Integer cantidadProductos) {
        this.valorTotal = valorTotal;
        this.cantidadProductos = cantidadProductos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(Integer cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public List<LineaVenta> getLineaVentas() {
        return lineaVentas;
    }

    public void setLineaVentas(List<LineaVenta> lineaVentas) {
        this.lineaVentas = lineaVentas;
    }

    public List<DatosFacturacion> getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(List<DatosFacturacion> datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

}
