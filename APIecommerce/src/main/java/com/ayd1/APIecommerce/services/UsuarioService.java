package com.ayd1.APIecommerce.services;

import com.ayd1.APIecommerce.models.dto.LoginDto;
import com.ayd1.APIecommerce.models.Rol;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.UsuarioRol;
import com.ayd1.APIecommerce.models.noBD.AppProperties;
import com.ayd1.APIecommerce.models.request.PasswordChange;
import com.ayd1.APIecommerce.repositories.RolRepository;
import com.ayd1.APIecommerce.repositories.UsuarioRepository;
import com.ayd1.APIecommerce.services.authentication.AuthenticationService;
import com.ayd1.APIecommerce.services.authentication.JwtGeneratorService;
import com.ayd1.APIecommerce.services.tools.MailService;
import com.ayd1.APIecommerce.tools.Encriptador;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class UsuarioService extends com.ayd1.APIecommerce.services.Service {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private Encriptador encriptador;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtGeneratorService jwtGenerator;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuario(Long id) throws Exception {
        if (id == null || id <= 0) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Id invalido.");
        }

        //mandamos a traer el estado de la cuenta
        Optional<Usuario> busquedaUsuario
                = usuarioRepository.findById(id);

        if (busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado el usuario.");
        }

        return busquedaUsuario.get();
    }

    public String eliminarUsuario(Long id) throws Exception {
        if (id == null || id <= 0) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Id invalido.");
        }
        //mandamos a traer el estado de la cuenta
        Optional<Usuario> busquedaUsuario
                = usuarioRepository.findById(id);
        //si esta vacio entonces el usuairo no existe
        if (busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado el usuario.");
        }
        //extraer el usuario
        Usuario usuario = busquedaUsuario.get();
        //seteamos la fecha de eliminacion
        usuario.setDeletedAt(Instant.now());
        //editar el usuario
        Usuario usuarioUpdate = this.usuarioRepository.save(usuario);
        //mandamos a editar la password y comparamos si se hizo el cambio
        if (usuarioUpdate.getId() > 0) {
            return "Se elimino el usuario con exito.";
        }
        throw new Exception("No pudimos eliminar el usuario, inténtalo más tarde.");
    }

    @Transactional
    public Usuario updateUsuario(Long id, Usuario usuario) throws Exception {
        if (id == null || id <= 0) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Id invalido.");
        }
        Optional<Usuario> busquedaUsuario
                = usuarioRepository.findById(id);
        //si esta vacio entonces el usuairo no existe
        if (busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado el usuario.");
        }
        //extraer el usuario
        Usuario usuarioEncontrado = busquedaUsuario.get();

        //si la fecha de eliminacion no es nula entonces ya ha sido eliminado ese usuario
        if (usuarioEncontrado.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }
        //evitamos que se cambie la contrasenia en este metodo
        usuario.setPassword(usuarioEncontrado.getPassword());
        this.validar(usuario);
        //editar el usuario
        Usuario usuarioUpdate = this.usuarioRepository.save(usuario);
        //mandamos a editar la password y comparamos si se hizo el cambio
        if (usuarioUpdate.getId() > 0) {
            return usuarioUpdate;
        }
        throw new Exception("No pudimos actualizar el usuario, inténtalo más tarde.");
    }

    public String enviarMailDeRecuperacion(String correo) throws Exception {
        if (correo.isBlank()) {//si el correo esta en blanco entonces lanzmaos error
            throw new Exception("Correo vacio.");
        }

        //mandamos a traer el estado de la cuenta
        Optional<Usuario> busquedaUsuario
                = usuarioRepository.findByEmail(correo);
        if (busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado tu correo electrónico.");
        }
        //obtenemos el modelo
        Usuario usuario = busquedaUsuario.get();
        //creamos el codigo de recuperacion
        String codigoRecuperacion = UUID.randomUUID().toString();
        //actualizamos el codigo de recuperacion
        usuario.setCodigoRecuperacion(codigoRecuperacion);
        //actualizamos en la bd
        Usuario actualizacion = usuarioRepository.save(usuario);

        if (actualizacion.getCodigoRecuperacion().equals(codigoRecuperacion)) {
            //usamos el servicio de mail para mander el correo electronico de recuperacion
            mailService.enviarCorreoEnSegundoPlano(actualizacion.getEmail(),
                    actualizacion.getCodigoRecuperacion(), 2);
            return "Te hemos enviado un correo electrónico con las "
                    + "instrucciones para recuperar tu cuenta MeXpose. Por favor revisa tu bandeja de entrada.";
        }
        throw new Exception("No hemos podido enviar el correo electrónico. Intentalo más tarde.");
    }

    public LoginDto iniciarSesion(Usuario log) throws Exception {
        try {
            Optional<Usuario> busquedaUsuario = usuarioRepository.
                    findByEmail(log.getEmail());

            if (busquedaUsuario.isEmpty()) {
                throw new Exception("Correo electronico incorrecto.");
            }

            Usuario usuario = busquedaUsuario.get();

            //si la fecha de eliminacion no es nula entonces ya ha sido eliminado ese usuario
            if (usuario.getDeletedAt() != null) {
                throw new Exception("Uusario ha sido eliminado.");
            }

            authenticationManager.authenticate(
                    //autenticar el usuario con la contrasenia encriptada
                    new UsernamePasswordAuthenticationToken(log.getEmail(),
                            log.getPassword()));

            //cargamos el usuario por el nombre
            UserDetails userDetails
                    = authenticationService.loadUserByUsername(
                            log.getEmail());
            //generar el token
            String jwt = jwtGenerator.generateToken(userDetails);
            return new LoginDto(usuario, jwt);//devolver la respuesta 

        } catch (AuthenticationException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public String cambiarPassword(PasswordChange cambioPassword) throws Exception {
        //validamos 
        this.validar(cambioPassword);

        //para cambiar la password necesitamos obtener el usuario que solicito mediante el codigo
        Optional<Usuario> busqueda = this.usuarioRepository
                .findByCodigoRecuperacion(cambioPassword.getCodigo());
        if (busqueda.isEmpty()) {//si esta vacio entonces el codigo no existe y devolvemos false
            throw new Exception("Tu código de autorización ha sido usado o ha expirado.");
        }

        Usuario usuario = busqueda.get();

        //mandamos a borrar el codigo de recuperacion 
        usuario.setCodigoRecuperacion(null);
        //encriptamos la password y hacemos el cambio en el modelo
        usuario.setPassword(
                Encriptador.encriptarPassword(
                        cambioPassword.getNuevaPassword()
                )
        );

        Usuario update = this.usuarioRepository.save(usuario);

        //mandamos a editar la password y comparamos si se hizo el cambio
        if (update.getId().longValue() == usuario.getId().longValue()) {
            return "Se cambió tu contraseña con exito.";
        }
        throw new Exception("No pudimos actualizar tu contraseña, inténtalo más tarde.");
    }

    public String crearUsuarioNormal(Usuario crear) throws Exception {
        //validamos 
        this.validar(crear);
        // traer el rol (USUARIO)
        Optional<Rol> rolBusqueda = this.rolRepository.findOneByNombre("USUARIO");
        //si el rol no existe lanzamos error
        if (rolBusqueda.isEmpty()) {
            throw new Exception("Rol no encontrado.");
        }
        Rol rol = rolBusqueda.get();
        return this.crearUsuario(crear, rol);
    }

    @Transactional
    private String crearUsuario(Usuario crear, Rol rol) throws Exception {
        //asignamos un rol al usuario
        UsuarioRol usuarioRol = new UsuarioRol(crear, rol);
        //creamos la lista de roles del usuairo
        ArrayList<UsuarioRol> rols = new ArrayList<>();
        //cargar la asinacion
        rols.add(usuarioRol);
        //asignamos los nuevos roles al usuarios
        crear.setRoles(rols);
        //encriptar la password 
        crear.setPassword(this.encriptador.encriptarPassword(
                crear.getPassword()
        ));
        //guardar el usuario
        Usuario userCreado = this.usuarioRepository.save(crear);
        //mandamos a editar la password y comparamos si se hizo el cambio
        if (userCreado.getId() > 0) {
            return "Tu usuario se creo con exito.";
        }
        throw new Exception("No pudimos crear tu uusuario, inténtalo más tarde.");
    }
}
