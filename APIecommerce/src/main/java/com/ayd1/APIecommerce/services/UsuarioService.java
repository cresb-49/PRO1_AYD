package com.ayd1.APIecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayd1.APIecommerce.models.Rol;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.UsuarioRol;
import com.ayd1.APIecommerce.models.dto.LoginDto;
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

        Usuario usuarioEncontrado = busquedaUsuario.get();

        //vemos si el usuario no  ha sido eliminado
        if (usuarioEncontrado.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }

        return usuarioEncontrado;
    }

    @Transactional
    public String eliminarUsuario(Long id, String emailUsuarioAutenticado) throws Exception {

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
        Usuario usuarioEliminar = busquedaUsuario.get();

        //vemos si el usuario no  ha sido eliminado
        if (usuarioEliminar.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }

        //validar si el usuario tiene permiso de eliminar
        this.verificarUsuarioJwt(usuarioEliminar, emailUsuarioAutenticado);
        //seteamos la fecha de eliminacion
        usuarioEliminar.setDeletedAt(Instant.now());

        //editar el usuario
        Usuario usuarioUpdate = this.usuarioRepository.save(usuarioEliminar);

        //mandamos a editar la password y comparamos si se hizo el cambio
        if (usuarioUpdate.getId() > 0) {
            return "Se elimino el usuario con exito.";
        }
        throw new Exception("No pudimos eliminar el usuario, inténtalo más tarde.");
    }

    @Transactional
    public Usuario updateUsuario(Usuario usuario, String emailUsuarioAutenticado) throws Exception {
        if (usuario.getId() == null || usuario.getId() <= 0) {
            throw new Exception("Id inválido.");
        }

        Optional<Usuario> busquedaUsuario = usuarioRepository.findById(usuario.getId());
        if (busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado el usuario.");
        }
        Usuario usuarioEncontrado = busquedaUsuario.get();

        //validar si el usuario tiene permiso de eliminar
        this.verificarUsuarioJwt(usuarioEncontrado, emailUsuarioAutenticado);

        //vemos si el usuario no  ha sido eliminado
        if (usuarioEncontrado.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }
        //vemos que no exista otro con el mismo email
        if (this.usuarioRepository.existsUsuarioByEmailAndIdNot(usuario.getEmail(),
                usuario.getId())) {
            //si el metodo no se rompe hubo un error insesperado
            throw new Exception(String.format("No se editó el usuario %s, "
                    + "debido a que ya existe otro usuario con el mismo email.",
                    usuario.getEmail()));
        }
        // Evitar el cambio de contraseña
        usuario.setPassword(usuarioEncontrado.getPassword());
        usuario.setFacturas(usuarioEncontrado.getFacturas());
        usuario.setRoles(usuarioEncontrado.getRoles());
        this.validar(usuario);
        Usuario usuarioUpdate = this.usuarioRepository.save(usuario);
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
        //vemos si el usuario no  ha sido eliminado
        if (usuario.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }
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
                    + "instrucciones para recuperar tu cuenta. Por favor revisa tu bandeja de entrada.";
        }
        throw new Exception("No hemos podido enviar el correo electrónico. Intentalo más tarde.");
    }

    public LoginDto iniciarSesion(Usuario log) throws Exception {
        try {
            //validamos la password
            this.validarAtributo(log, "email");
            //validamos la password
            this.validarAtributo(log, "password");
            Optional<Usuario> busquedaUsuario = usuarioRepository.
                    findByEmail(log.getEmail());

            if (busquedaUsuario.isEmpty()) {
                throw new Exception("Correo electronico incorrecto.");
            }

            Usuario usuario = busquedaUsuario.get();

            //si la fecha de eliminacion no es nula entonces ya ha sido eliminado ese usuario
            if (usuario.getDeletedAt() != null) {
                throw new Exception("Usuario ya ha sido eliminado.");
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
    public String recuperarPassword(PasswordChange cambioPassword) throws Exception {
        //validamos 
        this.validar(cambioPassword);

        //para cambiar la password necesitamos obtener el usuario que solicito mediante el codigo
        Optional<Usuario> busqueda = this.usuarioRepository
                .findByCodigoRecuperacion(cambioPassword.getCodigo());

        if (busqueda.isEmpty()) {//si esta vacio entonces el codigo no existe y devolvemos false
            throw new Exception("Tu código de autorización invalido.");
        }

        Usuario usuarioEncontrado = busqueda.get();

        //vemos si el usuario no  ha sido eliminado
        if (usuarioEncontrado.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }

        //mandamos a borrar el codigo de recuperacion 
        usuarioEncontrado.setCodigoRecuperacion(null);
        //encriptamos la password y hacemos el cambio en el modelo
        usuarioEncontrado.setPassword(
                Encriptador.encriptarPassword(
                        cambioPassword.getNuevaPassword()
                )
        );

        Usuario update = this.usuarioRepository.save(usuarioEncontrado);

        //mandamos a editar la password y comparamos si se hizo el cambio
        if (update.getId().longValue() == usuarioEncontrado.getId().longValue()) {
            return "Se cambió tu contraseña con exito.";
        }
        throw new Exception("No pudimos actualizar tu contraseña, inténtalo más tarde.");
    }

    @Transactional
    public String cambiarPassword(Usuario usuPassChange, String emailUsuarioAutenticado) throws Exception {
        //que el id no este vacio
        if (usuPassChange.getId() == null || usuPassChange.getId() <= 0) {
            throw new Exception("Id inválido.");
        }
        //validamos la password
        this.validarAtributo(usuPassChange, "password");
        //buscamos el usuario
        Optional<Usuario> busquedaUsuario = usuarioRepository.findById(usuPassChange.getId());

        if (busquedaUsuario.isEmpty()) {
            throw new Exception("No hemos encontrado el usuario.");
        }

        Usuario usuarioEncontrado = busquedaUsuario.get();

        //vemos si el usuario no  ha sido eliminado
        if (usuarioEncontrado.getDeletedAt() != null) {
            throw new Exception("Usuario ya ha sido eliminado.");
        }

        //validar si el usuario tiene permiso de eliminar
        this.verificarUsuarioJwt(usuarioEncontrado, emailUsuarioAutenticado);

        //encriptamos la password y hacemos el cambio en el modelo
        usuarioEncontrado.setPassword(
                Encriptador.encriptarPassword(usuPassChange.getPassword()
                )
        );

        Usuario update = this.usuarioRepository.save(usuarioEncontrado);

        //mandamos a editar la password y comparamos si se hizo el cambio
        if (update.getId().longValue() == usuarioEncontrado.getId().longValue()) {
            return "Se cambió tu contraseña con exito.";
        }
        throw new Exception("No pudimos actualizar tu contraseña, inténtalo más tarde.");
    }

    public LoginDto crearUsuario(Usuario crear, String rolStr) throws Exception {
        //validamos 
        this.validar(crear);
        // traer el rol (USUARIO)
        Optional<Rol> rolBusqueda = this.rolRepository.findOneByNombre(rolStr);
        //si el rol no existe lanzamos error
        if (rolBusqueda.isEmpty()) {
            throw new Exception("Rol no encontrado.");
        }
        Rol rol = rolBusqueda.get();
        return this.crearUsuario(crear, rol);
    }

    /**
     *
     * @param crear
     * @param rol
     * @return
     * @throws Exception
     */
    @Transactional
    public LoginDto crearUsuario(Usuario crear, Rol rol) throws Exception {
        if (this.usuarioRepository.existsByEmail(crear.getEmail())) {
            throw new Exception("El Email ya existe.");
        }
        // Asignamos un rol al usuario
        UsuarioRol usuarioRol = new UsuarioRol(crear, rol);
        ArrayList<UsuarioRol> rols = new ArrayList<>();
        rols.add(usuarioRol);
        crear.setRoles(rols);

        // Encriptar la contraseña 
        crear.setPassword(this.encriptador.encriptarPassword(crear.getPassword()));

        // Guardar el usuario
        Usuario userCreado = this.usuarioRepository.save(crear);

        // Generar el JWT para el usuario creado
        UserDetails userDetails = authenticationService.loadUserByUsername(crear.getEmail());
        String jwt = jwtGenerator.generateToken(userDetails);

        // Retornar la confirmación con el JWT
        if (userCreado.getId() > 0) {
            return new LoginDto(userCreado, jwt);
        }
        throw new Exception("No pudimos crear tu usuario, inténtalo más tarde.");
    }

    private boolean verificarUsuarioJwt(Usuario usuarioTratar, String emailUsuarioAutenticado) throws Exception {
        //validar si el usuario tiene permiso de eliminar
        if (!emailUsuarioAutenticado.equals(usuarioTratar.getEmail())
                && !isUserAdmin(emailUsuarioAutenticado)) {
            throw new Exception("No tienes permiso para realizar acciones a este usuario.");
        }
        return true;
    }

}
