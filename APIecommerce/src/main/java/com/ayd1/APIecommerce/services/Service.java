/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class Service {

    @Autowired
    private Validator validator;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Si se falla la validacion se lanza Exception con los motivos
     *
     * @param object
     * @return
     * @throws Exception
     */
    public boolean validar(Object object) throws Exception {
        //extraemos las validaciones
        Set<ConstraintViolation<Object>> validaciones = validator.validate(object);
        //validamos los valores
        if (!validaciones.isEmpty()) {
            throw new Exception(extraerErrores(validaciones));
        }
        return true;
    }

    public boolean validarAtributo(Object objeto, String attributeName) throws Exception {
        Set<ConstraintViolation<Object>> validaciones = validator.validateProperty(
                objeto, attributeName);
        if (!validaciones.isEmpty()) {
            throw new Exception(extraerErrores(validaciones));
        }
        return true;
    }

    private String extraerErrores(Set<ConstraintViolation<Object>> validaciones) {
        String fallas = "";
        for (ConstraintViolation<Object> item : validaciones) {
            fallas += item.getMessage().concat("\n");
        }
        return fallas;
    }

    public boolean isUserAdmin(String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return userDetails.getAuthorities().stream().anyMatch(auth
                -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
