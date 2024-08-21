/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "estado_envio")
public class EstadoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", length = 250, unique = false)
    @NotBlank(message = "El nombre del envio no puede estar vacío.")
    @NotNull(message = "El nombre del envio no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del envio debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @OneToMany(mappedBy = "estadoEnvio", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<Envio> envios;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public EstadoEnvio(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public EstadoEnvio() {
    }

    public EstadoEnvio(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public void setEnvios(List<Envio> envios) {
        this.envios = envios;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
