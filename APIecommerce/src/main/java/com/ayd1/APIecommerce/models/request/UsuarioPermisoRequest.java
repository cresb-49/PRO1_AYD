/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.models.request;

import com.ayd1.APIecommerce.models.Permiso;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class UsuarioPermisoRequest {

    private Long idUsuario;
    private List<Permiso> permisos;

    public UsuarioPermisoRequest(Long idUsuario, List<Permiso> permisos) {
        this.idUsuario = idUsuario;
        this.permisos = permisos;
    }

    public UsuarioPermisoRequest() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

}
