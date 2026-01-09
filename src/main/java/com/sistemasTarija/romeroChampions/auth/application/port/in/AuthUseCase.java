package com.sistemasTarija.romeroChampions.auth.application.port.in;

import com.sistemasTarija.romeroChampions.auth.application.dto.UsuarioDTO;

public interface AuthUseCase {
    
    /**
     * Autentica un usuario con username y contraseña
     * 
     * @param username nombre de usuario
     * @param rawPassword contraseña en texto plano
     * @return DTO del usuario autenticado (sin password)
     * @throws com.sistemasTarija.romeroChampions.auth.domain.exception.AuthenticationFailedException si las credenciales son inválidas
     */
    UsuarioDTO login(String username, String rawPassword);
}
