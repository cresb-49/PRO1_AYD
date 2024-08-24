package com.ayd1.APIecommerce.controllers;

import com.ayd1.APIecommerce.models.dto.LoginDto;
import com.ayd1.APIecommerce.models.Usuario;
import com.ayd1.APIecommerce.models.noBD.AppProperties;
import com.ayd1.APIecommerce.models.request.PasswordChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayd1.APIecommerce.services.UsuarioService;
import com.ayd1.APIecommerce.transformers.ApiBaseTransformer;
import java.util.Map;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id) {
        Object data = usuarioService.getUsuario(id);
        return new ApiBaseTransformer(HttpStatus.OK, "OK", data, null, null).sendResponse();
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> postMethodName(@RequestBody String entity) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }

    @PutMapping("usuario/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody String entity) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }

    @DeleteMapping("usuario/{id}")
    public ResponseEntity<?> deleteMethodName(@PathVariable String id) {
        return new ApiBaseTransformer(HttpStatus.OK, "OK", null, null, null).sendResponse();
    }

    @PostMapping("/usuario/public/recuperarPasswordMail")
    public ResponseEntity<?>
            enviarMailDeRecuperacion(@RequestBody Map<String, Object> requestBody) {

        try {
            String correoElectronico = (String) requestBody.get("correoElectronico");
            String mensaje = usuarioService.enviarMailDeRecuperacion(correoElectronico);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", mensaje,
                    null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }

    }

    @PostMapping("/usuario/public/cambioPassword")
    public ResponseEntity<?> cambiarPassword(@RequestBody PasswordChange requestBody) {
        try {
            String respuesta = usuarioService.cambiarPassword(requestBody);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @PostMapping("/usuario/public/login")
    public ResponseEntity<?> login(@RequestBody Usuario login) {
        try {
            LoginDto respuesta = usuarioService.iniciarSesion(login);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @PostMapping("/usuario/public/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario crear) {
        try {
            String respuesta = usuarioService.crearUsuarioNormal(crear);
            return new ApiBaseTransformer(HttpStatus.OK, "OK", respuesta,
                    null, null).sendResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST,
                    ex.getMessage(),
                    null, null, null).sendResponse();
        }
    }

    @GetMapping("/usuario/public/perfil/{id}")
    public ApiBaseTransformer getPerfil(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.getUsuario(id);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK",
                    usuario,
                    null, null);
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }
    }

    @DeleteMapping("/usuario/public/eliminarUsuario/{id}")
    public ApiBaseTransformer eliminarUsuario(@PathVariable Long id) {
        try {
            String confirmacion = usuarioService.eliminarUsuario(id);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK",
                    confirmacion,
                    null, null);
        } catch (NumberFormatException ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    "Id con formato invalido",
                    null, null, null);
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }
    }

    @PatchMapping("/usuario/public/updateUsuario/{id}")
    public ApiBaseTransformer actualizarUsuarioParcial(@PathVariable Long id,
            @RequestBody Usuario updates) {
        try {
            Usuario confirmacion = usuarioService.updateUsuario(id, updates);
            return new ApiBaseTransformer(HttpStatus.OK.value(), "OK",
                    confirmacion,
                    null, null);
        } catch (NumberFormatException ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    "Id con formato invalido",
                    null, null, null);
        } catch (Exception ex) {
            return new ApiBaseTransformer(HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    null, null, null);
        }
    }

    /*
    @PostMapping("/usuario/activarCuentaMail")
    public String enviarMailDeConfirmacion(@RequestBody String correoElectronico) {
        return estadoCuentaService.enviarCorreoDeActivacion(correoElectronico);
    }
    @PostMapping("/usuario/activarCuenta")
    public String activarCuenta(@RequestBody String codigo) {
        return estadoCuentaService.activarCuenta(codigo);
    } */
}
