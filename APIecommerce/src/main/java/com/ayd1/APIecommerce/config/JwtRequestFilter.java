/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayd1.APIecommerce.config;

import com.ayd1.APIecommerce.services.authentication.JwtGeneratorService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author luism
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtGeneratorService jwtGeneratorService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");//extraemos el header
        
        String user = null;
        String jwt = null;
        
        if (header != null && header.startsWith("Bearer ")) {//si el header cumple con la condicion extraemos el jwt y el user
            jwt = header.substring(7);
            user = jwtGeneratorService.extractUserName(jwt);
        }
        //si el usuario no es nulo entonces el jwt tampoco...inicializamos el SecurityContextHolder
        if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user);//traemos la info del usuario
            
            if (jwtGeneratorService.validateTOken(jwt, userDetails)) {//vemos si el jwt es valido
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
