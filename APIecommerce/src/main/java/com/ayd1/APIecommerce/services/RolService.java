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
        return (List<Rol>) rolRepository.findAll();
    }

    public Rol getRol(String rolStr) throws Exception {
        Rol rol = this.rolRepository.findOneByNombre(rolStr).orElse(null);
        // si el rol no existe lanzamos error
        if (rol == null) {
            throw new Exception("Rol no encontrado.");
        }
        return rol;
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
