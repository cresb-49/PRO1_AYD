/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "rol_usuario")
public class UsuarioRol extends Auditor {

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Usuario usuario;

    @ManyToOne//indicador de relacion muchos a uno
    @JoinColumn(name = "rol", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Rol rol;

    public UsuarioRol(Long id) {
        super(id);
    }

    public UsuarioRol(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public UsuarioRol() {
    }

    @Override
    public String toString() {
        return rol.getNombre();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
