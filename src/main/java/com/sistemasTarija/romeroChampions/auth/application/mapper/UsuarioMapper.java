package com.sistemasTarija.romeroChampions.auth.application.mapper;

import com.sistemasTarija.romeroChampions.auth.application.dto.UsuarioDTO;
import com.sistemasTarija.romeroChampions.auth.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return UsuarioDTO.builder()
                .idUsuario(usuario.getId())
                .username(usuario.getUsername())
                .rol(usuario.getRol())
                .idSucursal(usuario.getIdSucursal())
                .nombreCompleto(usuario.getNombreCompleto())
                .build();
        // Nota: NO incluimos el password en el DTO
    }
}
