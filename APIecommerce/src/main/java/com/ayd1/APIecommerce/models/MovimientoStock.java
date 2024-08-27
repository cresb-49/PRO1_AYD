/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "movimiento_stock")
public class MovimientoStock extends Auditor {

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "producto", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Producto producto;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "tipo", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TipoMovimientoStock tipo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    public MovimientoStock(Long id) {
        super(id);
    }

    public MovimientoStock(Producto producto, TipoMovimientoStock tipo, Integer cantidad) {
        this.producto = producto;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public MovimientoStock() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public TipoMovimientoStock getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimientoStock tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
