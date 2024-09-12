/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.config;

import com.ayd1.APIecommerce.enums.PermisoEnum;
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
import com.ayd1.APIecommerce.models.request.UsuarioAyudanteRequest;
import com.ayd1.APIecommerce.repositories.CategoriaRepository;
import com.ayd1.APIecommerce.repositories.EstadoEnvioRepository;
import com.ayd1.APIecommerce.repositories.PermisoRepository;
import com.ayd1.APIecommerce.repositories.RolRepository;
import com.ayd1.APIecommerce.repositories.TiendaConfigReporitory;
import com.ayd1.APIecommerce.services.UsuarioService;
import java.util.ArrayList;

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

    public EstadoEnvio insertarEstadoEnvio(EstadoEnvio estadoEnvio) throws Exception {
        try {
            Optional<EstadoEnvio> opRol = this.estadoEnvioRepository.findOneByNombre(estadoEnvio.getNombre());
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
            TiendaConfig conf = this.tiendaConfigReporitory.findFirstByOrderByIdAsc().orElse(null);
            if (conf == null) {
                return this.tiendaConfigReporitory.save(config);
            }
            return conf;

        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    public Permiso insertarPermisoSiNoExiste(Permiso pa) {
        Permiso permiso = this.permisoRepository.findOneByNombre(pa.getNombre()).orElse(null);
        if (permiso == null) {
            return this.permisoRepository.save(
                    pa);
        }
        return permiso;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            // siders roles
            this.insertarRol(new Rol("USUARIO"));
            this.insertarRol(new Rol("ADMIN"));
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

            // IMAGEN DEFAULT DE LA TIENDA
            byte[] img = getClass().getResourceAsStream("/img/logo.png").readAllBytes();

            TiendaConfig tiendaConfig = new TiendaConfig("TiendaAyD1", img, 12.00,
                    "2da calle XXX-XXX-XX Quetgo",
                    "image/png", 25.0);
            this.insertarTiendaConfig(tiendaConfig);
            // Seeder de usuarios del sistema
            Usuario admin = new Usuario("admin", "admin",
                    "elrincondelgamer77@gmail.com", null,
                    "12345",
                    null, null, false);
            Usuario user1 = new Usuario("Carlos",
                    "Pac", "carlosbpac@gmail.com",
                    null, "12345",
                    null, null, false);

            Usuario ayudante = new Usuario("Luis",
                    "Monterroso", "luismonteg1@hotmail.com",
                    null, "12345",
                    null, null, false);

            /*
             * Crear los usuarios, un catch por usuario para que ignore las
             * excepciones que puedan haber
             */
            try {
                this.usuarioService.crearAdministrador(admin);
            } catch (Exception e) {
            }
            try {
                this.usuarioService.crearUsuarioNormal(user1);

            } catch (Exception e) {
            }
            try {
                this.usuarioService.crearAyudante(
                        new UsuarioAyudanteRequest(ayudante, new ArrayList<>()));
            } catch (Exception e) {
            }

            // Creacion de todos los permisos que tiene el sistema
            for (PermisoEnum permiso : PermisoEnum.values()) {

                Permiso insercion = this.insertarPermisoSiNoExiste(
                        new Permiso(
                                permiso.getNombrePermiso(),
                                permiso.getRuta()));
                try {
                    // Asignacion de permisos a los usuarios
                    Usuario ayudante2 = this.usuarioService.getByEmail(
                            "luismonteg1@hotmail.com");
                    this.usuarioService.agregarPermisoUsuario(ayudante2,
                            insercion);
                } catch (Exception e) {
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Inserts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
