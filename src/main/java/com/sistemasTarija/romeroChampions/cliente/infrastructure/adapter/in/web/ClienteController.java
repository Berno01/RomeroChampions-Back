package com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.in.web;

import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;
import com.sistemasTarija.romeroChampions.cliente.application.port.in.ManageClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {
    
    private final ManageClienteUseCase manageClienteUseCase;

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(manageClienteUseCase.createCliente(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Integer id, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(manageClienteUseCase.updateCliente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        manageClienteUseCase.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(manageClienteUseCase.findClienteById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.ok(manageClienteUseCase.findAllClientes());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClienteDTO>> searchClientes(@RequestParam String query) {
        return ResponseEntity.ok(manageClienteUseCase.searchClientes(query));
    }
}
