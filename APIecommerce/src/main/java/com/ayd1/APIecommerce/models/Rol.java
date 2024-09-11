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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "rol")
public class Rol extends Auditor {

    @Column(name = "rol", length = 250, unique = true)
    @NotBlank(message = "El nombre del rol no puede estar vacío.")
    @NotNull(message = "El nombre del rol no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del rol debe tener entre 1 y 250 caracteres.")
    @Pattern(regexp = "^(USUARIO|ADMIN|AYUDANTE)$",
            message = "El nombre de rol solo puede ser USUARIO, ADMIN, AYUDANTE")
    private String nombre;

    @OneToMany(mappedBy = "rol", orphanRemoval = true)//indicamos que la relacion debera ser por medio del atributo "Paciente" del objeto Tratamiento
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<UsuarioRol> asignaciones;

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public Rol(Long id) {
        super(id);
    }

    public Rol() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<UsuarioRol> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<UsuarioRol> asignaciones) {
        this.asignaciones = asignaciones;
    }

}
