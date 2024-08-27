/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
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
@Table(name = "envio")
public class Envio extends Auditor {

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "venta", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Venta venta;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "estado_envio", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EstadoEnvio estadoEnvio;

    private LocalDateTime entregadoAt;

    public Envio(Venta venta, EstadoEnvio estadoEnvio, LocalDateTime entregadoAt) {
        this.venta = venta;
        this.estadoEnvio = estadoEnvio;
        this.entregadoAt = entregadoAt;
    }

    public Envio(Long id) {
        super(id);
    }

    public Envio() {
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public LocalDateTime getEntregadoAt() {
        return entregadoAt;
    }

    public void setEntregadoAt(LocalDateTime entregadoAt) {
        this.entregadoAt = entregadoAt;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

}
