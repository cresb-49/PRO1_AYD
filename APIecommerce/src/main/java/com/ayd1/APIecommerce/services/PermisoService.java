/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.Permiso;
import com.ayd1.APIecommerce.repositories.PermisoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class PermisoService extends Service {

    @Autowired
    private PermisoRepository permisoRepository;

    /**
     * Devuelve todas los permisos
     *
     * @return
     */
    public List<Permiso> getPermisos() {
        return this.permisoRepository.findAll();
    }
}
