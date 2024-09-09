/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ayd1.APIecommerce.tools;

import com.ayd1.APIecommerce.models.Permiso;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.UsuarioPermiso;
import com.ayd1.APIecommerce.services.UsuarioService;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ValidadorPermiso {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UsuarioService usuarioService;

    public Boolean verificarPermiso() throws Exception {
        //extraer la autenticacion (JWT)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Extraer el email del usuario
        String email = authentication.getName();
        //verificar si es ayudante
        Boolean isAyudante = this.isAyudante(authentication);

        //si no se trata de un ayudante entonces romper el metodo
        if (!isAyudante) {
            return true;
        }
        //si se trata de un ayudante entonces mandar a buscar al usuario por su correo
        Usuario usuarioEncontrado = this.usuarioService.getByEmail(email);
        //verificar si el usuario tiene el permiso a la ruta que esta intentando acceder
        if (!this.tienePermiso(usuarioEncontrado.getPermisos())) {
            throw new Exception("Sin permisos");
        }
        return true;
    }

    private boolean isAyudante(Authentication authentication) {
        // Extraer roles del usuario y verificar si se trata de un ayudante
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //buscar el rol AYUDANTE
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_AYUDANTE"));
    }

    /**
     * Verifica si el usuario tiene un permiso con el nombre dado.
     *
     * @param permisos
     * @return true si el permiso está asignado al usuario, false en caso
     * contrario.
     */
    private boolean tienePermiso(List<UsuarioPermiso> permisos) {
        //obtener el nombre de la ruta que se esta usando
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        Boolean tienePermiso = permisos.stream()
                .map(UsuarioPermiso::getPermiso)
                .map(Permiso::getRuta)
                .anyMatch(nombre -> requestURI.contains(nombre));
        return tienePermiso;
    }
}
