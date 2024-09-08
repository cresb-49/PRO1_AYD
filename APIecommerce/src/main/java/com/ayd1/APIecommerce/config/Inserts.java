/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.config;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ayd1.APIecommerce.models.Categoria;
import com.ayd1.APIecommerce.models.EstadoEnvio;
import com.ayd1.APIecommerce.models.Permiso;
import com.ayd1.APIecommerce.models.Rol;
import com.ayd1.APIecommerce.models.TiendaConfig;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.repositories.CategoriaRepository;
import com.ayd1.APIecommerce.repositories.EstadoEnvioRepository;
import com.ayd1.APIecommerce.repositories.PermisoRepository;
import com.ayd1.APIecommerce.repositories.RolRepository;
import com.ayd1.APIecommerce.repositories.TiendaConfigReporitory;
import com.ayd1.APIecommerce.services.UsuarioService;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class Inserts implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private TiendaConfigReporitory tiendaConfigReporitory;
    @Autowired
    private EstadoEnvioRepository estadoEnvioRepository;
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

    public Permiso insertarPermiso(Permiso permiso) throws Exception {
        try {
            Optional<Permiso> opPermiso = this.permisoRepository.findOneByNombre(permiso.getNombre());
            if (opPermiso.isPresent()) {
                return opPermiso.get();
            }
            return this.permisoRepository.save(permiso);
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    public EstadoEnvio insertarEstadoEnvio(EstadoEnvio estadoEnvio) throws Exception {
        try {
            Optional<EstadoEnvio> opRol
                    = this.estadoEnvioRepository.findOneByNombre(estadoEnvio.getNombre());
            if (opRol.isPresent()) {
                return opRol.get();
            }
            return this.estadoEnvioRepository.save(estadoEnvio);
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

    public TiendaConfig insertarTiendaConfig(TiendaConfig config) throws Exception {
        try {
            TiendaConfig conf
                    = this.tiendaConfigReporitory.findFirstByOrderByIdAsc().orElse(null);
            if (conf == null) {
                return this.tiendaConfigReporitory.save(config);
            }
            return conf;

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

            /**
             * Estados de envio
             */
            EstadoEnvio estadoEnvio = new EstadoEnvio("PENDIENTE");
            this.insertarEstadoEnvio(estadoEnvio);
            EstadoEnvio estadoEnvio2 = new EstadoEnvio("ENTREGADO");
            this.insertarEstadoEnvio(estadoEnvio2);

            //IMAGEN DEFAULT DE LA TIENDA
            byte[] img = getClass().getResourceAsStream("/img/logo.png").readAllBytes();

            TiendaConfig tiendaConfig
                    = new TiendaConfig("TiendaAyD1", img, 12.00,
                            "2da calle XXX-XXX-XX Quetgo",
                            "image/png");
            this.insertarTiendaConfig(tiendaConfig);
            //sider usuario Admin
            Usuario admin = new Usuario("admin", "admin",
                    "admin@admin", null,
                    "123",
                    null, null, false);
            this.usuarioService.crearUsuario(admin, rolAdmin);

            //Creacion de todos los permisos que tiene el sistema
            // CREAR, BORRAR, MODIFICAR, REPORTES
            Permiso permiso_crear = new Permiso("CREAR");
            Permiso permiso_borrar = new Permiso("BORRAR");
            Permiso permiso_modificar = new Permiso("MODIFICAR");
            Permiso permiso_reportes = new Permiso("REPORTES");
            this.insertarPermiso(permiso_crear);
            this.insertarPermiso(permiso_borrar);
            this.insertarPermiso(permiso_modificar);
            this.insertarPermiso(permiso_reportes);
            // Asignacion de permisos a los usuarios
            this.usuarioService.agregarPermisoUsuario(admin, permiso_crear);
            this.usuarioService.agregarPermisoUsuario(admin, permiso_borrar);
            this.usuarioService.agregarPermisoUsuario(admin, permiso_modificar);
            this.usuarioService.agregarPermisoUsuario(admin, permiso_reportes);
        } catch (Exception ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
