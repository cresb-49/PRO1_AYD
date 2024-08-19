package com.ayd1.APIecommerce.controllers;

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

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/{id}")
    public String getUsuario(@PathVariable Long id) {
        return usuarioService.getUsuario(id).toString();
    }

    @PostMapping("/usuario")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        return entity;
    }

    @PutMapping("usuario/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request

        return entity;
    }

    @DeleteMapping("usuario/{id}")
    public String deleteMethodName(@PathVariable String id) {
        return "Usuario con id: " + id + " eliminado";
    }

    @PostMapping("/usuario/recuperarPasswordMail")
    public String enviarMailDeRecuperacion(@RequestBody String correoElectronico) {
        return usuarioService.enviarMailDeRecuperacion(correoElectronico);
    }
    /*
    @PostMapping("/usuario/cambioPassword")
    public String cambiarPassword(@RequestBody PasswordChange cambioPassword) {
        return usuarioService.cambiarPassword(cambioPassword);
    }
     */
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
