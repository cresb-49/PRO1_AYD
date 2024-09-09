/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "estado_envio")
public class EstadoEnvio extends Auditor {

    @Column(name = "nombre", length = 250, unique = false)
    @NotBlank(message = "El nombre del envio no puede estar vacío.")
    @NotNull(message = "El nombre del envio no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del envio debe tener entre 1 y 250 caracteres.")
    private String nombre;

    @OneToMany(mappedBy = "estadoEnvio", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<Envio> envios;

    public EstadoEnvio(String nombre) {
        this.nombre = nombre;
    }

    public EstadoEnvio(Long id) {
        super(id);
    }

    public EstadoEnvio() {
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
}
