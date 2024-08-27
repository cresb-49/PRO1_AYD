/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.config;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.Rol;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.repositories.CategoriaRepository;
import com.ayd1.APIecommerce.repositories.RolRepository;
import com.ayd1.APIecommerce.repositories.UsuarioRepository;
import com.ayd1.APIecommerce.services.UsuarioService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class Inserts implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioService usuarioService;

    public Rol insertarRol(Rol rol) throws Exception {
        try {
            Optional<Rol> opRol = this.rolRepository.findOneByNombre(rol.getNombre());
            if (opRol.isPresent()) {
                return opRol.get();
            }
            return this.rolRepository.save(rol);
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    public Categoria insertarCategoria(Categoria cat) throws Exception {
        try {
            Optional<Categoria> opCat = this.categoriaRepository.findOneByNombre(cat.getNombre());
            if (opCat.isPresent()) {
                return opCat.get();
            }
            return this.categoriaRepository.save(cat);
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //siders roles
            this.insertarRol(new Rol("USUARIO"));
            Rol rolAdmin = this.insertarRol(new Rol("ADMIN"));
            this.insertarRol(new Rol("AYUDANTE"));

            Categoria categoria = new Categoria("Hogar");
            this.insertarCategoria(categoria);

            //sider usuario Admin
            Usuario admin = new Usuario("admin", "admin",
                    "admin@admin", null,
                    "123",
                    null, null, false);
            this.usuarioService.crearUsuario(admin, rolAdmin);

        } catch (Exception ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
