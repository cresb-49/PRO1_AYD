/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.Imagen;
import com.ayd1.APIecommerce.repositories.ImagenRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class ImagenService extends Service {

    @Autowired
    private ImagenRepository imagenRepository;

    public Imagen getImagen(Long id) throws Exception {
        if (id == null || id < 0) {
            throw new Exception("Id invalido.");
        }
        Optional<Imagen> imagenOpt = imagenRepository.findById(id);
        if (!imagenOpt.isPresent()) {
            throw new Exception("Imagen no encontrada.");
        }
        Imagen imagen = imagenOpt.get();
        return imagen;
    }
}
