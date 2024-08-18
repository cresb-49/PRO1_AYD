/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "envio")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "venta", nullable = false) //indicamos que el id del paciente se guardara con un solo field de tabla
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Venta venta;

    private LocalDateTime entregadoAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Envio(Long id, Venta venta, LocalDateTime entregadoAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.venta = venta;
        this.entregadoAt = entregadoAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Envio(Long id, Venta venta, LocalDateTime entregadoAt) {
        this.id = id;
        this.venta = venta;
        this.entregadoAt = entregadoAt;
    }

    public Envio(Long id) {
        this.id = id;
    }

    public Envio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
