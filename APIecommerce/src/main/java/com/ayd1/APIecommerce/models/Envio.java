/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "envio")
@DynamicUpdate
public class Envio extends Auditor {

    @OneToOne
    @JoinColumn(name = "venta", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Venta venta;

    @Column(name = "direccion", nullable = true)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "estado_envio", nullable = false, unique = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EstadoEnvio estadoEnvio;

    private LocalDateTime entregadoAt;

    public Envio(Venta venta, String direccion, EstadoEnvio estadoEnvio) {
        this.venta = venta;
        this.direccion = direccion;
        this.estadoEnvio = estadoEnvio;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
