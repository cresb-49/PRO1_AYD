package com.ayd1.APIecommerce.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.dto.LoginDto;
import com.ayd1.APIecommerce.models.request.PasswordChange;
import com.ayd1.APIecommerce.services.UsuarioService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene la información del usuario basado en el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/protected/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        try {
            Usuario data = usuarioService.getUsuario(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", data, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene la información del usuario basado en el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(
                                        implementation = Usuario.class
                                )
                        )
                )),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/usuario/private/getUsuarios")
    public ResponseEntity<?> getUsuario() {
        try {
            List<Usuario> data = usuarioService.getUsuarios();
            return new ApiBaseTransformer(HttpStatus.OK, "OK", data, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Enviar correo de recuperación de contraseña", description = "Envía un correo de recuperación de contraseña al usuario basado en la dirección de correo electrónico proporcionada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Correo enviado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/usuario/private/all/recuperarPasswordMail")
    public ResponseEntity<?> enviarMailDeRecuperacion(
            @Parameter(
                    description = "ID del producto a buscar",
                    required = true,
                    example = "{correoElectronico:\"xd\"}"
            ) @RequestBody Map<String, Object> requestBody) {
        try {
            String correoElectronico = (String) requestBody.get("correoElectronico");
            String mensaje = usuarioService.enviarMailDeRecuperacion(correoElectronico);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", mensaje, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Recuperar contraseña", description = "Recupera la contraseña del usuario utilizando el código de recuperación y nueva contraseña.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña recuperada exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PatchMapping("/usuario/private/all/recuperarPassword")
    public ResponseEntity<?> recuperarPassword(@RequestBody PasswordChange requestBody) {
        try {
            String respuesta = usuarioService.recuperarPassword(requestBody);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Cambiar contraseña", description = "Permite al usuario cambiar su contraseña actual.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contraseña cambiada exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PatchMapping("/usuario/private/all/cambioPassword")
    public ResponseEntity<?> cambiarPassword(
            @Parameter(
                    description = "ID del producto a buscar",
                    required = true,
                    example = "{id:1,password:\"xd\"}"
            )
            @RequestBody Usuario requestBody) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUsuarioAutenticado = authentication.getName();
            String respuesta = usuarioService.cambiarPassword(requestBody, emailUsuarioAutenticado);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class))}),
        @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
    })
    @PostMapping("/usuario/public/login")
    public ResponseEntity<?> login(
            @Parameter(
                    description = "ID del producto a buscar",
                    required = true,
                    example = "{email:\"nose@nose\",password:\"xd\"}"
            )
            @RequestBody Usuario login) {
        try {
            LoginDto respuesta = usuarioService.iniciarSesion(login);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @PostMapping("/usuario/public/validateTwoFactorToken")
    public ResponseEntity<?> validateTwoFactorToken(
        @Parameter(
                description = "Valida el token de autenticación de dos factores",
                required = true,
                example = "{email:\"user@email.com\",twoFactorCode:\"67858\"}"
        )
        @RequestBody Usuario login) {
        try {
            LoginDto respuesta = usuarioService.login2FT(login);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/usuario/public/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario crear) {
        try {
            LoginDto respuesta = usuarioService.crearUsuario(crear, "USUARIO");
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Crear administrador", description = "Crea un nuevo administrador en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Administrador creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/usuario/private/admin/crearAdministrador")
    public ResponseEntity<?> crearAdministrador(@RequestBody Usuario crear) {
        try {
            String rol = "ADMIN";
            LoginDto respuesta = usuarioService.crearUsuario(crear, rol);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Crear ayudante", description = "Crea un nuevo ayudante en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ayudante creado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping("/usuario/private/admin/crearAyudante")
    public ResponseEntity<?> crearAyudante(@RequestBody Usuario crear) {
        try {
            String rol = "AYUDANTE";
            LoginDto respuesta = usuarioService.crearUsuario(crear, rol);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta, null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Obtener perfil de usuario", description = "Obtiene el perfil del usuario basado en el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil encontrado",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "400", description = "ID no válido")
    })
    @GetMapping("/usuario/private/perfil/{id}")
    public ResponseEntity<?> getPerfil(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.getUsuario(id);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", usuario, null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario basado en el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
        @ApiResponse(responseCode = "400", description = "ID con formato inválido")
    })
    @DeleteMapping("/usuario/protected/eliminarUsuario/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUsuarioAutenticado = authentication.getName();
            String confirmacion = usuarioService.eliminarUsuario(id, emailUsuarioAutenticado);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", confirmacion, null, null).sendResponse();
        } catch (NumberFormatException ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Id con formato invalido", null, null, ex.getMessage()).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }

    @Operation(summary = "Actualizar usuario parcialmente", description = "Actualiza parcialmente la información del usuario basado en los datos proporcionados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "400", description = "ID con formato inválido")
    })
    @PatchMapping("/usuario/private/all/updateUsuario")
    public ResponseEntity<?> actualizarUsuarioParcial(@RequestBody Usuario updates) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUsuarioAutenticado = authentication.getName();
            Usuario confirmacion = usuarioService.updateUsuario(updates, emailUsuarioAutenticado);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", confirmacion, null, null).sendResponse();
        } catch (NumberFormatException ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    "Id con formato invalido",
                    null, null, ex.getMessage()).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST, "Error", null, null, ex.getMessage()).sendResponse();
        }
    }
}
