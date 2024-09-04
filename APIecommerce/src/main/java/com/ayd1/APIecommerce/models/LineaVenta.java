/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "linea_venta")
public class LineaVenta extends Auditor {

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "producto", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Producto producto;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "venta", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Venta venta;

    @Column(name = "precio", nullable = false)
    @Min(value = 0, message = "El precio debe tener como valor mínimo 0.")
    private Double precio;

    @Column(name = "cantidad", nullable = false)
    @Min(value = 1, message = "La cantidad debe tener como valor mínimo 1.")
    private Integer cantidad;

    public LineaVenta(Long id) {
        super(id);
    }

    public LineaVenta(Producto producto, Venta venta, Double precio, Integer cantidad) {
        this.producto = producto;
        this.venta = venta;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public LineaVenta() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
