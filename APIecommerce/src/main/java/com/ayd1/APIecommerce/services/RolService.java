package com.ayd1.APIecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ayd1.APIecommerce.repositories.RolRepository;

import com.ayd1.APIecommerce.models.Rol;

import java.util.List;

@Service
public class RolService {
    
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    public Rol getRol(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol createRol(Rol producto) {
        return rolRepository.save(producto);
    }

    public Rol updateRol(Long id, Rol producto) {
        Rol rolExistente = rolRepository.findById(id).orElse(null);
        if (rolExistente != null) {
            return rolRepository.save(rolExistente);
        }
        return null;
    }

}
