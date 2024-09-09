/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.request;

import com.ayd1.APIecommerce.models.Permiso;
import com.ayd1.APIecommerce.models.Usuario;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class UsuarioAyudanteRequest {

    private Usuario usuario;
    private List<Permiso> permisos;

    public UsuarioAyudanteRequest(Usuario usuario, List<Permiso> permisos) {
        this.usuario = usuario;
        this.permisos = permisos;
    }

    public UsuarioAyudanteRequest() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

}
